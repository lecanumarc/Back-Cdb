package com.excilys.java.CDB.jersey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Pagination;

public class CompanyJerseyTests {

	@Test
	public void testFindbyID() {
		Company company = new Company.Builder().setId(2L).setName("Thinking Machines").build();
		assertEquals(company, CompanyJersey.findbyID(2L));
	}

	@Test
	public void testExistCompanyFalse() {
		assertFalse(CompanyJersey.existCompany(100000L));
	}

	@Test
	public void testExistCompanyTrue() {
		assertTrue(CompanyJersey.existCompany(1L));
	}

	@Test
	public void testCountCompany() {
		assertEquals(41, CompanyJersey.countCompany());
	}

	@Test
	public void testGetListPage() {
		Pagination page = new Pagination();
		List<Company> companies = CompanyJersey.getListPage(page);
		assertEquals(20, companies.size());
	}

}
