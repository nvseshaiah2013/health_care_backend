package com.cg.healthcare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthcare.service.JwtUtil;

@RestController
@RequestMapping(path="/api/admin")
public class AdminController {
	
	@Autowired
	private JwtUtil jwtUtil;

	public String getAdminByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;	
}
}
