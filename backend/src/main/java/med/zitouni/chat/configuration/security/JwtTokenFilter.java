package med.zitouni.chat.configuration.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.entities.User;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepository userRepository;
	
	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
		
		// Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }
        
     // Get user identity and set it on the spring security context
        User user = userRepository
            .findByUsername(jwtTokenUtil.getUsernameFromJwtToken(token))
            .orElse(null);
        
        UserDetails userDetails = AppSecurityUtils.convertUserToUserDetails.apply(user);
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        		userDetails, null,
        		userDetails == null ?
                List.of() : userDetails.getAuthorities());
                
        authentication.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
	}

}
