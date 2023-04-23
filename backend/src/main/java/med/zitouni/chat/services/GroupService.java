package med.zitouni.chat.services;

import java.util.Set;

import med.zitouni.chat.entities.Group;

public interface GroupService {

	Group add(String userUuid, String name);
	
	Set<Group> getUserGroups(String userUuid);
	
	void addUserToGroup(String userUuid, String groupUuid);
}
