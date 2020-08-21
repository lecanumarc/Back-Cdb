package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.exception.UserException;
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

		Optional<User> user = userDAO.findByUsername(username);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.get().getUsername())
				.password(user.get().getPassword())
				.roles(user.get().getRole().getName())
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

	public Optional<User> findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	public void add(User user) throws UserException{
		if(findByUsername(user.getUsername()) != null) {
			throw new UserException("username already taken !");
		}
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
		} else {
			return userDAO.countByUsernameContainingOrRoleNameContaining(search, search);
		}
	}

	public List<User> listByRoleId(long id) {
		return userDAO.findAllByRoleId(id);
	}

	public Page<User> listByPage(String filter, PageRequest pageReq) {
		return userDAO.findByUsernameContainingOrRoleNameContaining(filter, filter, pageReq);
	}

	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}

}
