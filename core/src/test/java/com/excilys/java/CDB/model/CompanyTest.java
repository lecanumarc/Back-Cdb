package com.excilys.java.CDB.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void testValidBuilder() {
		Company company = new Company.Builder()
									.setId( new Long(1))
									.setName("Apple")
									.build();
		assertEquals("company's id is incorrect", new Long(1), company.getId());
		assertEquals("company's name is incorrect", "Apple", company.getName());
	}
}
