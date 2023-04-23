package med.zitouni.chat.dto;

public class PostMessageDTO {
	private String message;
	private String userUuid;
	private String groupUuid;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUserUuid() {
		return userUuid;
	}
	
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
	public String getGroupUuid() {
		return groupUuid;
	}
	
	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
}
