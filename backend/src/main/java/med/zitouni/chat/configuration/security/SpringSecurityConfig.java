package med.zitouni.chat.configuration.security;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.entities.User;

@Configuration
public class SpringSecurityConfig {
	private final JwtTokenFilter jwtTokenFilter;
	private final CorsFilter corsFilter;
	private final UserRepository userRepository;
	
	public SpringSecurityConfig(CorsFilter corsFilter, JwtTokenFilter jwtTokenFilter, UserRepository userRepository) {
		this.jwtTokenFilter = jwtTokenFilter;
		this.corsFilter = corsFilter;
		this.userRepository = userRepository;}

	@Bean
	public UserDetailsService userDetailsService() {
		return (String username) -> {
			Optional<User> optional = userRepository.findByUsername(username);
			
			if(optional.isEmpty()) {
				throw new UsernameNotFoundException(String.format("User %s is not existing", username));
			}
			
			return AppSecurityUtils.convertUserToUserDetails.apply(optional.get());
		};
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService())
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}
	
	// Expose authentication manager bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http = http.authorizeHttpRequests()
				.antMatchers("/api/auth/**", "/api/messages/subscribe-user/**")
				.permitAll().anyRequest().authenticated().and();
		http = http.addFilterBefore(corsFilter, SessionManagementFilter.class).csrf().disable();
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
