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

import com.excilys.java.CDB.model.Role;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.persistence.DAO.RoleDAO;

@Service
public class RoleService {

	@Autowired
	private RoleDAO roleDAO; 

	@Autowired
	public RoleService(RoleDAO roleDAO){
		this.roleDAO = roleDAO;
	}

	public Role add(Role role) {
		return roleDAO.save(role);
	}

	public void delete(long id) {
		//TO DO: on cascade ?
		roleDAO.deleteById(id);
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
	
	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}
}