package med.zitouni.chat.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import med.zitouni.chat.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByUsername(String username);
}
