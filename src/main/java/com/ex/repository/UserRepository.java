package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
}
