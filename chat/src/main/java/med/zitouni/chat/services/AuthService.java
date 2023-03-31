package med.zitouni.chat.services;

import med.zitouni.chat.dto.SignInDTO;
import med.zitouni.chat.dto.SignUpDTO;
import med.zitouni.chat.entities.User;

public interface AuthService {
	public User signUp(SignUpDTO request);
	
	public User signIn(SignInDTO request);
}
