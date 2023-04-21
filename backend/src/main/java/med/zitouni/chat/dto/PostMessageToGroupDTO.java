package med.zitouni.chat.dto;

public class PostMessageToGroupDTO {
	private String groupUuid;
	private String userUuid;
	private String content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
