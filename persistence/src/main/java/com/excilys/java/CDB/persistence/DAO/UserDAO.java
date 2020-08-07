package com.excilys.java.CDB.persistence.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.java.CDB.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
