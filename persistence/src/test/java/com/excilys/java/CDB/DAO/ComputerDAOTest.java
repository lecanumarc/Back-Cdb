package com.excilys.java.CDB.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.java.CDB.configuration.SpringTestConfig;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.persistence.DAO.ComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql({"/script.sql"})
public class ComputerDAOTest {

	@Autowired
	ComputerDAO computerDAO;

	@Test
	public void addValidComputer() {
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple Inc.")
				.build();
		Computer computer = new Computer.Builder()
				.setName("NewComputer")
				.setIntroduced(LocalDate.now())
				.setDiscontinued(LocalDate.now().plusYears(1))
				.setCompany(company)
				.build();
		computerDAO.save(computer);
		Computer computerFound = computerDAO.findById((long)21).get();
		assertEquals("Error during computer add", computerFound, computer);

	}

	@Test
	public void editValidComputer() {
		Computer computer = new Computer.Builder()
				.setName("NewComputer")
				.setId(new Long(1))
				.build();

		computerDAO.save(computer);

		Computer computerFound = computerDAO.findById((long)1).get();

		assertEquals("computer's name is incorrect", computerFound.getName(), "NewComputer");
		assertEquals("computer's id is incorrect", computerFound.getId(), new Long(1));
		assertEquals("Error during computer edit", computerFound, computer);

	}

	@Test
	public void validDeleteComputer() {
		computerDAO.deleteById(new Long(1));
		assertFalse("Computer still exists after deletion", computerDAO.existsById(new Long(1)));
	}

	@Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
	public void invalidDeleteComputer() { 
		computerDAO.deleteById(new Long(55));
	}

	@Test
	public void validFindComputerById() {
		Computer computerFound = computerDAO.findById(new Long(17)).get();
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple Inc.")
				.build();
		Computer computerExpected = new Computer.Builder()
				.setId(new Long(17))
				.setName("Apple III Plus")
				.setIntroduced(LocalDate.parse("1983-12-01"))
				.setDiscontinued(LocalDate.parse("1984-04-01"))
				.setCompany(company)
				.build();

		assertEquals("computer's name is incorrect", computerExpected.getName(), computerFound.getName());
		assertEquals("computer's id is incorrect", computerExpected.getId(), computerFound.getId());
		assertEquals("computer's disc date is incorrect", computerExpected.getDiscontinued(), computerFound.getDiscontinued());
		assertEquals("computer's intro date is incorrect", computerExpected.getIntroduced(), computerFound.getIntroduced());
		assertEquals("computer's company  is incorrect", computerExpected.getCompany(), computerFound.getCompany());
		assertEquals("Error during computer edit", computerExpected, computerFound);
	}

	@Test
	public void validListByPage() {
		int index = 0;
		int rows = 5;
		Page<Computer> page = computerDAO.findAll(PageRequest.of(index, rows));
		List<Computer> list = page.getContent();
		
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple Inc.")
				.build();
		Company company2 = new Company.Builder()
				.setId( new Long(2))
				.setName("Thinking Machines")
				.build();
		
		Computer firstComputer = new Computer.Builder()
				.setId(new Long(1))
				.setName("MacBook Pro 15.4 inch")
				.setCompany(company)
				.build();
		
		Computer lastComputer = new Computer.Builder()
				.setName("CM-5")
				.setId(new Long(5))
				.setIntroduced(LocalDate.parse("1991-01-01"))
				.setCompany(company2)
				.build();
		assertTrue("page has empty content", page.hasContent());
		assertEquals("Wrong amount of lines", rows, page.getSize());
		assertEquals("Wrong first computer value", firstComputer, list.get(0));
		assertEquals("Wrong last computer value", lastComputer, list.get(4));
	}

	@Test
	public void validFindAll() {
		List<Computer> list = computerDAO.findAll();
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple Inc.")
				.build();
		Company company2 = new Company.Builder()
				.setId( new Long(4))
				.setName("Netronics")
				.build();

		Computer firstComputer = new Computer.Builder()
				.setId(new Long(1))
				.setName("MacBook Pro 15.4 inch")
				.setCompany(company)
				.build();
		
		Computer lastComputer = new Computer.Builder()
				.setName("ELF II")
				.setId(new Long(20))
				.setIntroduced(LocalDate.parse("1977-01-01"))
				.setCompany(company2)
				.build();

		assertEquals("Wrong amount of lines", 20, list.size());
		assertEquals("Wrong first computer value", firstComputer, list.get(0));
		assertEquals("Wrong last computer value", lastComputer, list.get(19));
	}

}