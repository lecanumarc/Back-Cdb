package com.excilys.java.CDB.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Pagination;
import com.excilys.java.CDB.persistence.DAO.CompanyDAO;

@Service
public class CompanyService {
	
	/**
	 *  Class doing the relation with the CompanyDAO  
	 *  @author ninonV
	 *  **/
	
	@Autowired
	private CompanyDAO companyDAO;
	
	public List<Company> listCompanies(){
		return companyDAO.findAll();
	}
	
	public Company findbyID(Long id) {
		return companyDAO.findById(id).get();
	}
	
	public void deleteCompany(Long id) {
		companyDAO.deleteById(id);
	}

	public boolean existCompany(Long id) {
		return companyDAO.existsById(id);
	}
	
	public int countCompany() {
		return (int) companyDAO.count();
	}
	
	public List<Company> getListPage(Pagination page){
		Pageable pageRequest = PageRequest.of(page.getCurrentPage()-1, page.getLinesPage());
		Page<Company> companiesPage = companyDAO.findAll(pageRequest);
		return companiesPage.getContent();
	}
	
}
