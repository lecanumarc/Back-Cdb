package com.excilys.java.CDB.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ComputerTest {
	Computer computer;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testGetId() {
		Computer computer = new Computer();
		computer.setId(new Long(1));
		assertEquals("computer id is incorrect", new Long(1), computer.getId());
	}
	
	@Test
	public void testGetName() {
		Computer computer = new Computer();
		computer.setName("Mac");
		assertEquals("computer id is incorrect", "Mac", computer.getName());
	}
	
	@Test
	public void testValidBuilder() {
		Company company = new Company.Builder()
				.setId( new Long(1))
				.setName("Apple")
				.build();
		Computer computer = new Computer.Builder()
									.setId( new Long(1))
									.setName("Mac")
									.setIntroduced(LocalDate.now())
									.setDiscontinued(LocalDate.now().plusYears(1))
									.setCompany(company)
									.build();
		assertEquals("computer's id is incorrect", new Long(1), computer.getId());
		assertEquals("computer's name is incorrect", "Mac", computer.getName());
		assertEquals("computer's introduced date is incorrect", LocalDate.now(), computer.getIntroduced());
		assertEquals("computer's discontinued date is incorrect", LocalDate.now().plusYears(1), computer.getDiscontinued());
		assertEquals("computer's company date is incorrect", company, computer.getCompany());
	}
	
	@Test(expected=Exception.class)
	public void testNullIntroDate() {
		Computer computer = new Computer();
//		computer.setIntroduced(null);
		
//		exception.expect(ComputerValidatorException.class);
//	    exception.expectMessage("Name cannot be empty");
//		computer = new ComputerBuilder("").build();
//	
	}
//	if (introduced != null && this.discontinued != null && introduced.isAfter(this.discontinued)) {
//		throw new Exception("Introduced date is after discontinued date");
//	}
}
