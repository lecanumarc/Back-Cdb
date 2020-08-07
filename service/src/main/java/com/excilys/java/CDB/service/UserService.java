package com.excilys.java.CDB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.persistence.DAO.UserDAO;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}
 
		User user = userDAO.findByUsername(username);
 
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
	}
	
	public List<User> listUsers() {
		return userDAO.findAll();
	}
	
	public void createUser(User user) {
		userDAO.save(user);
	}
	
	public void deleteUser(User user) {
		userDAO.delete(user);
	}

}
