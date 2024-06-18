package patientrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import patientrecord.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
