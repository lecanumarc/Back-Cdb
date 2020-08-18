package com.excilys.java.CDB.jersey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;
import com.excilys.java.CDB.model.Pagination;

public class ComputerJerseyTests {

	@Test
	public void testExistComputerFalse() {
		assertFalse(ComputerJersey.existComputer(10000000L));
	}

	@Test
	public void testExistComputerTrue() {
		assertTrue(ComputerJersey.existComputer(55L));
	}

	@Test
	public void testfindbyID() {
		Computer computer = new Computer.Builder().setId(55L).setName("Apple I")
				.setIntroduced(LocalDate.of(1976, 04, 01)).setDiscontinued(LocalDate.of(1977, 10, 01))
				.setCompany(new Company.Builder().setId(1L).setName("Apple Inc.").build()).build();
		assertEquals(computer, ComputerJersey.findbyID(55L));
	}

	@Test
	public void testCountComputer() {
		assertEquals(560, ComputerJersey.countComputer());
	}

	@Test
	public void testGetListPage() {
		Pagination page = new Pagination();
		List<Computer> computers = ComputerJersey.getListPage(page);
		assertEquals(20, computers.size());
	}
}
