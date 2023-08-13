package com.johnsmith.SpringBootInstagramApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.johnsmith.SpringBootInstagramApi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query(value = "SELECT u FROM User u WHERE u.fullname LIKE %:query% OR u.username LIKE %:query%")
	List<User> findByQuery(@Param("query") String query);
}
