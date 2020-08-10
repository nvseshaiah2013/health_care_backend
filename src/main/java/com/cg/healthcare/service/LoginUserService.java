package com.cg.healthcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.healthcare.dao.UserRepository;

@Service
public class LoginUserService implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.cg.healthcare.entities.User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(user.getRole()));
			return new User(user.getUsername(), user.getPassword(), authorities);
		}
	}

}
