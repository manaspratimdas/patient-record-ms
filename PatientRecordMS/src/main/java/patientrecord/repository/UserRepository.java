package patientrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import patientrecord.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);
}