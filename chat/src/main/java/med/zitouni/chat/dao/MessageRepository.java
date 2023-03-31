package med.zitouni.chat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import med.zitouni.chat.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>{
	List<Message> findTopByOrderByTimestampDesc();
}
