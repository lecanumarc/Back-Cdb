package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.persistence.DAO.CompanyDAO;

@Service
public class CompanyService {
	
	/**
	 *  Class doing the relation with the CompanyDAO  
	 *  @author ninonV
	 *  **/
	
	private CompanyDAO companyDao;
	
	@Autowired
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public void add(Company obj) {
		companyDao.save(obj);
	}

	public void edit(Company obj) {
		companyDao.save(obj);
	}

	public void delete(long id) {
		//TO DO: on cascade ?
		companyDao.deleteById(id);
	}

	public Optional<Company> findById(Long id) {
		return companyDao.findById(id);
	}

	public Page<Company> listByPage(int index, int rows) {
		return companyDao.findAll(PageRequest.of(index, rows));
	}
	
	public Page<Company> listByPage(String filter, PageRequest pageReq) {
		return companyDao.findByNameContaining(filter, pageReq);
	}

	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}
	
	public List<Company> listCompanies() {
		return companyDao.findAll();
	}

}
