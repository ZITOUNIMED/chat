package med.zitouni.chat.services;
import med.zitouni.chat.dto.SignUpDTO;
import med.zitouni.chat.entities.User;

public interface AuthService {
	public User signUp(SignUpDTO request);
}
