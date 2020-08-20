package com.excilys.java.CDB.persistence.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.java.CDB.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

	Page<User> findByUsernameContaining(String filter, Pageable pageReq);

	int countByUsernameContaining(String search);

	List<User> findAllByRoleId(long id);

	Page<User> findByUsernameContainingOrRoleNameContaining(String userFilter, String roleFilter, Pageable pageReq);

	int countByUsernameContainingOrRoleNameContaining(String userFilter, String roleFilter);

}
