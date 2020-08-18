package com.excilys.java.CDB.persistence.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.java.CDB.model.Company;

/**
 * Class doing the relation with the table company
 * 
 * @author ninonV
 **/
@Repository
public interface CompanyDAO extends JpaRepository<Company, Long> {

	Page<Company> findByNameContaining(String filter, Pageable of);

	int countByNameContaining(String search);

}
