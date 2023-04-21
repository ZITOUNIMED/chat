package med.zitouni.chat.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import med.zitouni.chat.dao.GroupRepository;
import med.zitouni.chat.dao.MessageRepository;
import med.zitouni.chat.dao.UserRepository;
import med.zitouni.chat.entities.Group;
import med.zitouni.chat.entities.Message;
import med.zitouni.chat.entities.User;
import med.zitouni.chat.exceptions.FonctionalException;
import med.zitouni.chat.services.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final MessageRepository messageRepository;
	
	public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, MessageRepository messageRepository){
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
	}
	
	@Override
	public Group add(String userUuid, String name) {
		Optional<User> optionalUser = userRepository.findById(userUuid);
		if(optionalUser.isEmpty()) {
			throw new FonctionalException("No user with this uuid");
		}
		
		Group group = new Group();
		group.setAdmin(optionalUser.get());
		group.setCreator(optionalUser.get());
		group.setCreatedAt(Instant.now());
		group.setName(name);
		group.setUuid(UUID.randomUUID().toString());
		
		return groupRepository.save(group);
	}
	
	@Override
	public Set<Group> getUserGroups(String userUuid) {
		Set<Group> groups = new HashSet<>();
		Optional<Group> optionalAdmin = groupRepository.findByAdminUuid(userUuid);
		Optional<Group> optionalCreator = groupRepository.findByCreatorUuid(userUuid);
		if(optionalAdmin.isPresent()) {
			groups.add(optionalAdmin.get());
		}
		if(optionalCreator.isPresent()) {
			groups.add(optionalCreator.get());
		}
		return groups;
	}

	@Override
	public void addUserToGroup(String userUuid, String groupUuid) {
		Optional<User> optionalUser = userRepository.findById(userUuid);
		if(optionalUser.isEmpty()) {
			throw new FonctionalException("No user with this uuid");
		}
		
		Optional<Group> optionalGroup = groupRepository.findById(groupUuid);
		if(optionalGroup.isEmpty()) {
			throw new FonctionalException("No group with this uuid");
		}
		
		List<User> users = optionalGroup.get().getUsers();
		if(users == null) {
			users = new ArrayList<>();
		}
		
		users.add(optionalUser.get());
		
		groupRepository.save(optionalGroup.get());
	}

	@Override
	public List<Message> getLast10Messages(String uuid) {
		List<Message> messages = groupRepository.getMessages();
		return messages.stream().sorted((m1, m2) -> m1.getTimestamp()
				.isAfter(m2.getTimestamp()) ? -1 : 1)
				.limit(10)
				.collect(Collectors.toList());
	}

	@Override
	public void postMessage(String userUuid, String groupUuid, String content) {
		Optional<User> optionalUser = userRepository.findById(userUuid);
		
		if(optionalUser.isEmpty()) {
			throw new FonctionalException("User does not exist!");
		}
		
		Optional<Group> optionalGroup = groupRepository.findById(groupUuid);
		if(optionalGroup.isEmpty()) {
			throw new FonctionalException("No group with this uuid");
		}
		
		Message message = new Message();
		message.setUser(optionalUser.get());
		message.setTimestamp(Instant.now());
		message.setContent(content);
		message.setUuid(UUID.randomUUID().toString());
		
		messageRepository.save(message);
		
		List<Message> messages = optionalGroup.get().getMessages();
		if(messages == null) {
			messages = new ArrayList<>();
		}
		
		messages.add(message);
		
		groupRepository.save(optionalGroup.get());
	}

}
