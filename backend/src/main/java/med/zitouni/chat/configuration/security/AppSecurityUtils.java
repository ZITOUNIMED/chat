package med.zitouni.chat.configuration.security;

import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import med.zitouni.chat.entities.User;

public class AppSecurityUtils {
	public static Function<User, UserDetails> convertUserToUserDetails = (User user) -> {
		return org.springframework.security.core.userdetails.User
				.builder()
				.password(user.getPassword())
				.username(user.getUsername())
				.authorities(new ArrayList<>())
				.build();
	};
}
