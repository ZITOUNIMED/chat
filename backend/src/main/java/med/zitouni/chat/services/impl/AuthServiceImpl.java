package med.zitouni.chat.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.dto.SignUpDTO;
import med.zitouni.chat.entities.User;
import med.zitouni.chat.exceptions.FonctionalException;
import med.zitouni.chat.services.AuthService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User signUp(SignUpDTO request) {
		Optional<User> optional = userRepository.findByUsername(request.getUsername());
		
		if(optional.isPresent()) {
			throw new FonctionalException("Username already existing!");
		}
		
		if(!request.getPassword().equals(request.getConfirmPassword())) {
			throw new FonctionalException("Passwords are different!");
		}
		
		User user = new User();
		
		user.setName(request.getName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUsername(request.getUsername());
		user.setUuid(UUID.randomUUID().toString());
		
		return userRepository.save(user);
	}
}
