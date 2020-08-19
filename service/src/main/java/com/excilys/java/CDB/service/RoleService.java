package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.CDB.model.Role;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.persistence.DAO.RoleDAO;
import com.excilys.java.CDB.persistence.DAO.UserDAO;

@Service
public class RoleService {

	private RoleDAO roleDAO; 
	private UserDAO userDAO; 

	@Autowired
	public RoleService(RoleDAO roleDAO, UserDAO userDAO){
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}

	public Role add(Role role) {
		return roleDAO.save(role);
	}

	@Transactional
	public boolean delete(long id) {
		if (roleDAO.existsById(id)) {
			List<User> users = userDAO.findAllByRoleId(id);
			users.stream().forEach((User user) -> {userDAO.delete(user);});
			roleDAO.deleteById(id);
			return true;
		}
		return false;
	}

	public Role edit(Role role) {
		return roleDAO.save(role);
	}

	public Optional<Role> findById(long id) {
		return roleDAO.findById(id);
	}

	public int getNumberRows() {
		return (int) roleDAO.count();
	}

	public Page<Role> listByPage(int index, int rows) {
		return roleDAO.findAll(PageRequest.of(index, rows));
	}

	public Page<Role> listByPage(String filter, PageRequest pageReq) {
		return roleDAO.findByNameContaining(filter, pageReq);
	}

	public List<Role> listRoles() {
		return roleDAO.findAll();
	}

	public int count(String search) {
		if(search==null) {
			return (int) roleDAO.count();
		}else {
			return roleDAO.countByNameContaining(search);
		}
	}

	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}
}