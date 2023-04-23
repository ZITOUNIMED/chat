package med.zitouni.chat.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.zitouni.chat.dto.AddGroupDTO;
import med.zitouni.chat.dto.AddUserToGroupDTO;
import med.zitouni.chat.entities.Group;
import med.zitouni.chat.services.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
	private final GroupService groupService;
	
	public GroupController(GroupService groupService){
		this.groupService = groupService;
	}
	
	@PostMapping("/add")
	public Group addGroup(@RequestBody AddGroupDTO dto) {
		return groupService.add(dto.getUserUuid(), dto.getName());
	}
	
	@PostMapping("/add-user-to-group")
	public void addUserToGroup(@RequestBody AddUserToGroupDTO dto) {
		groupService.addUserToGroup(dto.getUserUuid(), dto.getGroupUuid());
	}
	
	@GetMapping("/user-groups/{userUuid}")
	public Set<Group> getUserGroups(@PathVariable() String  userUuid) {
		return groupService.getUserGroups(userUuid);
	}
}
