package com.excilys.java.CDB.DTO;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyDtoTest {

	@Test
	public void testValidBuilder() {
		CompanyDTO companyDTO = new CompanyDTO.Builder()
									.setCompanyId("1")
									.setCompanyName("Apple")
									.build();
		assertEquals("companyDTO id is incorrect", "1", companyDTO.getCompanyId());
		assertEquals("companyDTO name is incorrect", "Apple", companyDTO.getCompanyName());
	}
}
