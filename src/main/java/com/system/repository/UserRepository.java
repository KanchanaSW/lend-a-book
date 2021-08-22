package com.system.repository;

import com.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	//Boolean existsByUsername(String email);

	Boolean existsByEmail(String email);
}
