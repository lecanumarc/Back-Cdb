package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.persistence.DAO.CompanyDAO;
import com.excilys.java.CDB.persistence.DAO.ComputerDAO;

@Service
public class CompanyService {

	/**
	 * Class doing the relation with the CompanyDAO
	 * 
	 * @author ninonV
	 **/

	private CompanyDAO companyDao;
	private ComputerDAO computerDao;

	@Autowired
	public CompanyService(CompanyDAO companyDao, ComputerDAO computerDao) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;
	}

	public void add(Company obj) {
		companyDao.save(obj);
	}

	public void edit(Company obj) {
		companyDao.save(obj);
	}

	@Transactional
	public boolean delete(long id) {
		if (companyDao.existsById(id)) {
			List<Computer> computers = computerDao.findAllByCompanyId(id);
			computers.stream().forEach((Computer computer) -> {
				computerDao.delete(computer);
			});
			companyDao.deleteById(id);
			return true;
		}
		return false;

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
		if (ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}

	public List<Company> listCompanies() {
		return companyDao.findAll();
	}

	public int countCompanies(String search) {
		if (search == null) {
			return (int) companyDao.count();
		} else {
			return companyDao.countByNameContaining(search);
		}
	}

}
