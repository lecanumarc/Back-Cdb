package com.excilys.java.CDB.persistence.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.java.CDB.model.Computer;

/**
 *  Class doing the relation with the table computer  
 *  @author ninonV
 *  **/

@Repository
public interface ComputerDAO extends JpaRepository<Computer, Long> {
	
	Page<Computer> findByNameContaining(String string, Pageable page);
	List<Computer> findAllByCompanyId(long id);
}