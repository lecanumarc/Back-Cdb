package com.excilys.java.CDB.DTO;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class ComputerDtoTest {

	@Test
	public void testValidBuilder() {
		CompanyDTO companyDTO = new CompanyDTO.Builder()
									.setCompanyId("1")
									.setCompanyName("Apple")
									.build();
		ComputerDTO computerDTO = new ComputerDTO.Builder()
									.setComputerId("1")
									.setComputerName("MacBook Pro 15.4 inch")
									.setIntroduced(LocalDate.now().toString())
									.setDiscontinued(LocalDate.now().plusYears((long) 1).toString())
									.setCompanyDTO(companyDTO)
									.build();
		
		assertEquals("computerDTO id is incorrect", "1", computerDTO.getComputerId());
		assertEquals("computerDTO name is incorrect", "MacBook Pro 15.4 inch", computerDTO.getComputerName());
		assertEquals("computerDTO introduced date is incorrect", LocalDate.now().toString(), computerDTO.getIntroduced());
		assertEquals("computerDTO discontinued date is incorrect", LocalDate.now().plusYears((long) 1).toString(), computerDTO.getDiscontinued());
		assertEquals("companyDTO id is incorrect", "1", computerDTO.getCompanyDTO().getCompanyId());
		assertEquals("companyDTO name is incorrect", "Apple", computerDTO.getCompanyDTO().getCompanyName());
	}
}
