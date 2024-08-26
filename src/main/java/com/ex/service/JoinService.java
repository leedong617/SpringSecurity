package com.ex.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ex.dto.JoinDTO;
import com.ex.entity.UserEntity;
import com.ex.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void joinProcess(JoinDTO dto) {
		
		//db에 이미 동일한 username을 가진 회원이 존재하는지
		Boolean isExist = userRepository.existsByUsername(dto.getUsername());
        if (isExist) {
            return;
        }
		
        UserEntity data = new UserEntity();

        data.setUsername(dto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        // role admin 임의 설정
        data.setRole("ROLE_ADMIN");


        userRepository.save(data);
		
	}
	
}
