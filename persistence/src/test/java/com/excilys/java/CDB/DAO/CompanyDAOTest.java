package com.excilys.java.CDB.DAO;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.java.CDB.configuration.SpringTestConfig;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.persistence.DAO.CompanyDAO;
import com.excilys.java.CDB.persistence.DAO.ComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql({"/script.sql"})
public class CompanyDAOTest {

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	ComputerDAO computerDAO;

	@Test
	public void addValidCompany() {
		Company company = new Company.Builder()
				.setName("NewCompany")
				.build();
		companyDAO.save(company);

		Company companyFound = companyDAO.findById((long)21).get();

		assertEquals("Error during company add", companyFound, company);

	}

	@Test
	public void editValidCompany() {
		Company company = new Company.Builder()
				.setName("NewCompany")
				.setId(new Long(1))
				.build();

		companyDAO.save(company);

		Company companyFound = companyDAO.findById((long)1).get();

		assertEquals("company's name is incorrect", companyFound.getName(), "NewCompany");
		assertEquals("company's id is incorrect", companyFound.getId(), new Long(1));
		assertEquals("Error during company edit", companyFound, company);

	}

	@Test
	public void validDeleteCompanyWithLinkedComputers() {
		Long companyId = new Long(1);
		List<Computer> computers = computerDAO.findAllByCompanyId(companyId);
		computers.stream().forEach((Computer computer) -> {computerDAO.delete(computer);});
		companyDAO.deleteById(companyId);

		assertEquals("Wrong number of computers linked to a specific company", 6, computers.size());
		assertFalse("Company still exists after deletion", companyDAO.existsById(companyId));
		assertEquals("linked computers still exist after company deletion", 0, computerDAO.findAllByCompanyId(companyId).size());
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void invalidDeleteCompanyWithLinkedComputers() {
		companyDAO.deleteById(new Long(1));
	}
	
	@Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
	public void invalidDeleteCompany() {
		companyDAO.deleteById(new Long(50));
	}

	@Test
	public void validFindCompanyById() {
		Company companyFound = companyDAO.findById(new Long(1)).get();

		Company companyExpected = new Company.Builder()
				.setName("Apple Inc.")
				.setId(new Long(1))
				.build();

		assertEquals("company's name is incorrect", companyExpected.getName(), companyFound.getName());
		assertEquals("company's id is incorrect", companyExpected.getId(), companyFound.getId());
		assertEquals("Error during company edit", companyExpected, companyFound);
	}

	@Test
	public void validListByPage() {
		int index = 0;
		int rows = 5;
		Page<Company> page = companyDAO.findAll(PageRequest.of(index, rows));
		List<Company> list = page.getContent();
		Company firstCompany = new Company.Builder()
				.setName("Apple Inc.")
				.setId(new Long(1))
				.build();
		Company lastCompany = new Company.Builder()
				.setName("Tandy Corporation")
				.setId(new Long(5))
				.build();
		assertTrue("page has empty content", page.hasContent());
		assertEquals("Wrong amount of lines", rows, page.getSize());
		assertEquals("Wrong first company value", firstCompany, list.get(0));
		assertEquals("Wrong last company value", lastCompany, list.get(4));
	}

	@Test
	public void validFindAll() {
		List<Company> list = companyDAO.findAll();

		Company firstCompany = new Company.Builder()
				.setName("Apple Inc.")
				.setId(new Long(1))
				.build();
		Company lastCompany = new Company.Builder()
				.setName("Atari")
				.setId(new Long(20))
				.build();

		assertEquals("Wrong amount of lines", 20, list.size());
		assertEquals("Wrong first company value", firstCompany, list.get(0));
		assertEquals("Wrong last company value", lastCompany, list.get(19));
	}
	
	@Test
	public void validFilteredByName() {
		int index = 0;
		int rows = 5;
		String filter = "Apple";
		String column = "id";
		
		PageRequest pageReq = PageRequest.of(index, rows , Sort.by(column).ascending());
		
		Page<Company> page = companyDAO.findByNameContaining(filter, pageReq);

		
		List<Company> list = page.getContent();
		Company firstCompany = new Company.Builder()
				.setName("Apple Inc.")
				.setId(new Long(1))
				.build();
		
		assertTrue("page has empty content", page.hasContent());
		assertEquals("Wrong list size", 1, page.getSize());
		assertEquals("Wrong first company value", firstCompany, list.get(0));
	}

}
