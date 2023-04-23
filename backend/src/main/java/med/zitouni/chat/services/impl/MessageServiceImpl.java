package med.zitouni.chat.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import med.zitouni.chat.dao.GroupRepository;
import med.zitouni.chat.dao.MessageRepository;
import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.dto.PostMessageDTO;
import med.zitouni.chat.entities.Group;
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
	private final GroupRepository groupRepository;
	
	public MessageServiceImpl(MessageRepository messageRepository,
			UserRepository userRepository,
			NotificationsService notificationsService, GroupRepository groupRepository) {
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
		this.notificationsService = notificationsService;
		this.groupRepository = groupRepository;
	}
	
	@Override
	public void postMessage(PostMessageDTO request) {
		Optional<User> optional = userRepository.findById(request.getUserUuid());
		
		if(optional.isEmpty()) {
			throw new FonctionalException("User does not exist!");
		}
		
		Message message  = new Message();
		
		if(!StringUtils.isEmpty(request.getGroupUuid())) {
			Optional<Group> optionalGroup = groupRepository.findById(request.getGroupUuid());
			
			if(optionalGroup.isEmpty()) {
				throw new FonctionalException("Group does not exist!");
			}
			
			message.setGroup(optionalGroup.get());
		}
		
		message.setContent(request.getMessage());
		message.setUser(optional.get());
		message.setTimestamp(Instant.now());
		message.setUuid(UUID.randomUUID().toString());
		
		messageRepository.save(message);
		
		notificationsService.dispatchMessage(message);
	}

	@Override
	public List<Message> getLast10Messages(String groupUuid) {
		List<Message> messages = null;
		if(!StringUtils.isEmpty(groupUuid)) {
			messages = messageRepository.findByGroupUuid(groupUuid);
		} else {
			messages = messageRepository.findByGroupUuid(null);
		}
		
		return messages.stream().sorted((m1, m2) -> m1.getTimestamp()
				.isAfter(m2.getTimestamp()) ? -1 : 1)
				.limit(10)
				.collect(Collectors.toList());
	}

}
