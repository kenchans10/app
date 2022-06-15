package com.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java_backend.model.user;

@Repository
public interface userRepository extends JpaRepository<user, String> {
	user findByUserId(String Id);
}
