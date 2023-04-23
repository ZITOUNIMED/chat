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
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "chat_groups")
public class Group {
	@Id
	private String uuid;
	
	@ManyToMany(fetch = FetchType.LAZY,
			 cascade = { 
					 CascadeType.PERSIST,
			         CascadeType.MERGE
			        })
	@JoinTable(name = "chat_groups_users",
    joinColumns = { @JoinColumn(name = "chat_group_uuid") },
    inverseJoinColumns = { @JoinColumn(name = "chat_user_uuid") })
	private List<User> users;
	
	private String name;
	
	private String adminUuid;
	
	private String creatorUuid;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdminUuid() {
		return adminUuid;
	}

	public void setAdminUuid(String adminUuid) {
		this.adminUuid = adminUuid;
	}

	public String getCreatorUuid() {
		return creatorUuid;
	}

	public void setCreatorUuid(String creatorUuid) {
		this.creatorUuid = creatorUuid;
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
