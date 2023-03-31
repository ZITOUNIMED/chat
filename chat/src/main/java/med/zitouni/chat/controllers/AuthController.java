package med.zitouni.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.zitouni.chat.dto.SignInDTO;
import med.zitouni.chat.dto.SignUpDTO;
import med.zitouni.chat.entities.User;
import med.zitouni.chat.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/sign-up")
	public User signUp(@RequestBody SignUpDTO request) {
		User user = authService.signUp(request);
		user.setPassword(null);
		return user;
	}
	
	@PostMapping("/sign-in")
	public User signIn(@RequestBody SignInDTO request) {
		User user = authService.signIn(request);
		user.setPassword(null);
		return user;
	}


}
