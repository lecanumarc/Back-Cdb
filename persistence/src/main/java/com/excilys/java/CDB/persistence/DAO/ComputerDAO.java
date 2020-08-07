package com.excilys.java.CDB.persistence.DAO;

import java.util.List;

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
	
	 List<Computer> findByNameContaining(Pageable pageable, String search);
	
	/*@Query("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name "
			+ "FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ?2 OR company.name LIKE ?3") 
	List<Computer> findAllWithDescriptionQuery(Pageable pageable, String searchName, String searchCompanyName);*/

	int countByNameContaining(String search);

	//@Query("SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ?1 OR company.name LIKE ?2")
}