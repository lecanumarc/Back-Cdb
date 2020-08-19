package com.excilys.java.CDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.persistence.DAO.ComputerDAO;

/**
 *  Class doing the relation with the ComputerDAO  
 *  @author ninonV
 *  **/

@Service
public class ComputerService {

	private ComputerDAO computerDao;

	@Autowired
	public ComputerService(ComputerDAO computerDao){
		this.computerDao = computerDao;
	}

	public Computer add(Computer computer) {
		return computerDao.save(computer);
	}

	public boolean delete(long id) {
		if(computerDao.existsById(id)){
			computerDao.deleteById(id);
			return true;
		}
		return false;
	}

	public Computer edit(Computer computer) {
		return computerDao.save(computer);
	}

	public Optional<Computer> findById(long id) {
		return computerDao.findById(id);
	}

	public List<Computer> listByCompanyId(long id) {
		return computerDao.findAllByCompanyId(id);
	}

	public int getNumberRows() {
		return (int) computerDao.count();
	}

	public Page<Computer> listByPage(int index, int rows) {
		return computerDao.findAll(PageRequest.of(index, rows));
	}

	public Page<Computer> listByPage(String filter, PageRequest pageReq) {
		return computerDao.filteredPage(filter, pageReq);
	}

	public Sort sortBy(String column, boolean ascOrder) {
		if(ascOrder) {
			return Sort.by(column).ascending();
		}
		return Sort.by(column).descending();
	}

	public List<Computer> listComputers() {
		return computerDao.findAll();
	}

	public int countComputer(String search) {
		if(search==null) {
			return (int) computerDao.count();
		}else {
			return computerDao.countByNameContaining(search);
		}
	}
}
