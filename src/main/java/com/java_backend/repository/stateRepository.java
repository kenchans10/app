package com.java_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java_backend.model.ref_state;

@Repository
public interface stateRepository extends JpaRepository<ref_state, Long> {

	//Optional<ref_state> findByStateId(String id);
	ref_state findByStateId(String id);
}
