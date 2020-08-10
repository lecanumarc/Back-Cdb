package com.excilys.java.CDB.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class ComputerTest {
	@Test
	public void testValidBuilder() {
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple")
				.build();
		Computer computer = new Computer.Builder()
									.setId( new Long(1))
									.setName("MacBook Pro 15.4 inch")
									.setIntroduced(LocalDate.now())
									.setDiscontinued(LocalDate.now().plusYears(1))
									.setCompany(company)
									.build();
		assertEquals("computer's id is incorrect", new Long(1), computer.getId());
		assertEquals("computer's name is incorrect", "MacBook Pro 15.4 inch", computer.getName());
		assertEquals("computer's introduced date is incorrect", LocalDate.now(), computer.getIntroduced());
		assertEquals("computer's discontinued date is incorrect", LocalDate.now().plusYears(1), computer.getDiscontinued());
		assertEquals("computer's company is incorrect", company, computer.getCompany());
	}

}
