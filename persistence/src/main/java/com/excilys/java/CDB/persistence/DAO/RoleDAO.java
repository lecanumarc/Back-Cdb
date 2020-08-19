package com.excilys.java.CDB.persistence.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.java.CDB.model.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
	Role findByName(String name);
	Page<Role> findByNameContaining(String filter, PageRequest pageReq);
	int countByNameContaining(String search);
}
