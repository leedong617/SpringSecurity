package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	// username 중복 여부
	Boolean existsByUsername(String username);
	// username으로 찾기
	UserEntity findByUsername(String username);
}
