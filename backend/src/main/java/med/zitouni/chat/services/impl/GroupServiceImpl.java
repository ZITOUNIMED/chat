package med.zitouni.chat.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import med.zitouni.chat.dao.GroupRepository;
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
	
	public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository){
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
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
		
		
		return groupRepository.save(group);
	}
	
	@Override
	public List<Group> getUserGroups(String userUuid) {
		List<Group> groups = new ArrayList<>();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Message> getLast10Messages(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postMessage(String userUuid, String groupUuid, Message message) {
		// TODO Auto-generated method stub
		
	}

}
