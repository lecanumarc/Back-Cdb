package com.excilys.java.CDB.DTO.mapper;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.model.Company;

/**
 * Mapping a ResultSet to a Company
 * @author ninonV
 */

public class CompanyMapper {
	
	/**
	 * Convert a CompanyDTO to a Company
	 * @param companyDTO
	 * @return company
	 */
	public static Company mapDtoToCompany(CompanyDTO companyDTO){
		Company company = new Company.Builder()
									.setId(Long.valueOf(companyDTO.getCompanyId()))
									.setName(companyDTO.getCompanyName())
									.build();
		return company;
	}
	
	/**
	 * Convert a Company to a CompanyDTO
	 * @param company
	 * @return companyDTO
	 */
	public static CompanyDTO mapCompanyToDTO(Company company){
		CompanyDTO companyDTO = new CompanyDTO.Builder()
											.setCompanyId(company.getId().toString())
											.setCompanyName(company.getName())
											.build();
		return companyDTO;
	}
	
}
