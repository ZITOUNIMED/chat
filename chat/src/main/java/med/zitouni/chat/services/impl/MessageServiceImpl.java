package med.zitouni.chat.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import med.zitouni.chat.dao.MessageRepository;
import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.dto.PostMessageDTO;
import med.zitouni.chat.entities.Message;
import med.zitouni.chat.entities.User;
import med.zitouni.chat.exceptions.FonctionalException;
import med.zitouni.chat.services.MessageService;
import med.zitouni.chat.services.NotificationsService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{
	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	private final NotificationsService notificationsService;
	
	public MessageServiceImpl(MessageRepository messageRepository,
			UserRepository userRepository,
			NotificationsService notificationsService) {
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
		this.notificationsService = notificationsService;
	}
	
	@Override
	public void postMessage(PostMessageDTO request) {
		Optional<User> optional = userRepository.findById(request.getUserUuid());
		
		if(optional.isEmpty()) {
			throw new FonctionalException("User does not exist!");
		}
		
		Message message  = new Message();
		
		message.setContent(request.getMessage());
		message.setUser(optional.get());
		message.setTimestamp(Instant.now());
		message.setUuid(UUID.randomUUID().toString());
		
		messageRepository.save(message);
		
		notificationsService.dispatchMessage(message);
	}

	@Override
	public List<Message> getLast10Messages() {
		//List<Message> messages = messageRepository.findTopByOrderByTimestampDesc();
		List<Message> messages = messageRepository.findAll();
		return messages.stream().sorted((m1, m2) -> m1.getTimestamp()
				.isAfter(m2.getTimestamp()) ? -1 : 1)
				.limit(10)
				.collect(Collectors.toList());
	}

}
