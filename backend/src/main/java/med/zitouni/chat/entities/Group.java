package med.zitouni.chat.entities;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
public class Group {
	@Id
	private String uuid;
	
	@ManyToMany(fetch = FetchType.LAZY,
			 cascade = { 
					 CascadeType.PERSIST,
			         CascadeType.MERGE
			        })
	@JoinTable(name = "groups_users",
    joinColumns = { @JoinColumn(name = "group_uuid") },
    inverseJoinColumns = { @JoinColumn(name = "user_uuid") })
	private List<User> users;
	
	@OneToMany(fetch = FetchType.LAZY,
			cascade = { 
					 CascadeType.PERSIST,
			         CascadeType.MERGE
			        })
	@JoinColumn(name = "group_uuid", referencedColumnName = "uuid")
	private List<Message> messages;
	
	private String name;
	
	private User admin;
	
	private User creator;
	
	private Instant createdAt;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return Objects.equals(uuid, other.uuid);
	}
	
	
}
