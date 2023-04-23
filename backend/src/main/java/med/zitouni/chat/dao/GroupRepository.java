package med.zitouni.chat.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import med.zitouni.chat.entities.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String>{
	Optional<Group> findByAdminUuid(String uuid);
	Optional<Group> findByCreatorUuid(String uuid);
}
