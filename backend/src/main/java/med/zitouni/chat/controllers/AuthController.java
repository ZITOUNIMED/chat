package med.zitouni.chat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.zitouni.chat.configuration.security.JwtTokenUtil;
import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.dto.SignInDTO;
import med.zitouni.chat.dto.SignUpDTO;
import med.zitouni.chat.dto.UserDTO;
import med.zitouni.chat.entities.User;
import med.zitouni.chat.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	
	
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenUtil jwtTokenUtil, AuthService authService) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO request) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			
			User user = authService.signUp(request);
			
			Authentication authenticate = authenticationManager.authenticate(authenticationToken);
			String token = jwtTokenUtil.generateJwtToken(authenticate);
			user.setPassword(null);
			UserDTO userDTO = new UserDTO(user);
			userDTO.setToken(token);
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<UserDTO> signIn(@RequestBody SignInDTO request) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			Authentication authenticate = authenticationManager.authenticate(authenticationToken);
			UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
			User user = userRepository.findByUsername(userDetails.getUsername()).get();
			user.setPassword(null);
			String token = jwtTokenUtil.generateJwtToken(authenticate);
			
			UserDTO userDTO = new UserDTO(user);
			userDTO.setToken(token);
			
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}


}
