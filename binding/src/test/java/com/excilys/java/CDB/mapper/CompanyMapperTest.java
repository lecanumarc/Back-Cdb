package com.excilys.java.CDB.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.mapper.CompanyMapper;
import com.excilys.java.CDB.model.Company;

public class CompanyMapperTest {

	@Test
	public void validMapDtoToCompany() {
		CompanyDTO companyDTO = new CompanyDTO.Builder()
				.setCompanyId("1")
				.setCompanyName("Apple")
				.build();
		Company company = CompanyMapper.mapDtoToCompany(companyDTO);
		assertEquals("company id is incorrect", new Long(1), company.getId());
		assertEquals("company name is incorrect", "Apple", company.getName());
	}

	@Test()
	public void mapDtoToCompanyWithNullDto() {
		CompanyDTO companyDTO = null;
		Company company = CompanyMapper.mapDtoToCompany(companyDTO);
		assertEquals("company should be null", null, company);
	}

	@Test()
	public void validMapCompanyToDto() {
		Company company = new Company.Builder()
				.setId(new Long(1))
				.setName("Apple")
				.build();
		CompanyDTO companyDTO = CompanyMapper.mapCompanyToDTO(company);

		assertEquals("companyDTO id is incorrect", "1", companyDTO.getCompanyId());
		assertEquals("companyDTO name is incorrect", "Apple", companyDTO.getCompanyName());
	}

	@Test()
	public void mapCompanyToDtoWithNullCompany() {
		Company company = null;
		CompanyDTO companyDTO = CompanyMapper.mapCompanyToDTO(company);

		assertEquals("companyDTO should be null", null, companyDTO);
	}


	@Test()
	public void mapCompanyToDtoWithNullName() {
		Company company = new Company.Builder()
				.setId(new Long(1))
				.setName(null)
				.build();
		CompanyDTO companyDTO = CompanyMapper.mapCompanyToDTO(company);

		assertEquals("companyDTO should be null", null, companyDTO);
	}
}
