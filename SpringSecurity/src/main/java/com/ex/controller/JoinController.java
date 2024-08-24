package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ex.dto.JoinDTO;
import com.ex.service.JoinService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {
	
	private final JoinService joinService;
	
	@GetMapping("/join")
	public String joinP() {
		
		return "join";
	}
	
	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO dto) {
		
		joinService.joinProcess(dto);
		
		return "redirect:/login";
	}
}
