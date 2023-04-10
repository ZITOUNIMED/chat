package med.zitouni.chat.dto;

import med.zitouni.chat.entities.User;

public class UserDTO {
	private String uuid;
	private String username;
	private String name;
	private String password;
	private String token;
	
	public UserDTO(){}
	
	public UserDTO(User user) {
		this.username = user.getUsername();
		this.uuid = user.getUuid();
		this.password = user.getPassword();
		this.name = user.getName();
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
