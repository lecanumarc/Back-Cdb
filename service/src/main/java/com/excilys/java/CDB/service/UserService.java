package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.model.Computer;
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
				.roles(user.getRole().getName())
				.build();
	}

	public List<User> listUsers() {
		return userDAO.findAll();
	}

	public User edit(User user) {
		return userDAO.save(user);
	}

	public Optional<User> findById(long id) {
		return userDAO.findById(id);
	}

	public void add(User user) {
		userDAO.save(user);
	}

	public boolean delete(long id) {
		if (userDAO.existsById(id)) {
			userDAO.deleteById(id);
			return true;
		}
		return false;
	}

	public int count() {
		return (int) userDAO.count();
	}

	public int count(String search) {
		if(search==null) {
			return (int) userDAO.count();
		}else {
			return userDAO.countByUsernameContaining(search);
		}
	}

	public List<User> listByRoleId(long id) {
		return userDAO.findAllByRoleId(id);
	}

	public Page<User> listByPage(String filter, PageRequest pageReq) {
		return userDAO.findByUsernameContaining(filter, pageReq);
	}

	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}

}
