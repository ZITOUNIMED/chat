package med.zitouni.chat.services;

import java.util.List;
import java.util.Set;

import med.zitouni.chat.entities.Group;
import med.zitouni.chat.entities.Message;

public interface GroupService {

	Group add(String userUuid, String name);
	
	Set<Group> getUserGroups(String userUuid);
	
	void addUserToGroup(String userUuid, String groupUuid);
	
	List<Message> getLast10Messages(String uuid);
	
	void postMessage(String userUuid, String groupUuid, String content);
}
