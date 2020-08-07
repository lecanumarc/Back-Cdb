package com.excilys.java.CDB.persistence.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.excilys.java.CDB.model.Company;
/**
 *  Class doing the relation with the table company  
 *  @author ninonV
 *  **/
@Repository
public interface CompanyDAO extends JpaRepository<Company, Long> {
	
}
