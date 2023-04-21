package med.zitouni.chat.dto;

public class AddUserToGroupDTO {
	private String groupUuid;
	private String userUuid;
	public String getGroupUuid() {
		return groupUuid;
	}
	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
}
