package med.zitouni.chat.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import med.zitouni.chat.entities.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String>{
	Set<Group> findByAdminUuid(String uuid);
	Set<Group> findByCreatorUuid(String uuid);
}
