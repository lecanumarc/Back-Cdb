package com.excilys.java.CDB.DTO.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.model.Company;

/**
 * Mapping a ResultSet to a Company
 * @author ninonV
 */

public class CompanyMapper {
	private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	/**
	 * Convert a CompanyDTO to a Company
	 * @param companyDTO
	 * @return company
	 */
	public static Company mapDtoToCompany(CompanyDTO companyDTO){
		Company company = null;
		try {
			if(companyDTO == null) {
				throw new CompanyException("companyDTO can't be null");
			} else if(companyDTO.getCompanyId() == null || companyDTO.getCompanyName() == null) {
				throw new CompanyException("companyDTO object is invalid");
			} else {
				company = new Company.Builder().setId(Long.valueOf(companyDTO.getCompanyId()))
						.setName(companyDTO.getCompanyName())
						.build();
			}
		} catch (CompanyException e) {
			logger.error(e.getMessage() +e.getStackTrace());
		}
		return company;
	}

	/**
	 * Convert a Company to a CompanyDTO
	 * @param company
	 * @return companyDTO
	 */
	public static CompanyDTO mapCompanyToDTO(Company company){
		CompanyDTO companyDTO = null;
		try {
			if(company == null) {
				throw new CompanyException("Company can't be null");
			} else if(company.getId() == null || company.getName() == null) {
				throw new CompanyException("Company object is invalid");
			} else {
				companyDTO = new CompanyDTO.Builder()
						.setCompanyId(company.getId().toString())
						.setCompanyName(company.getName())
						.build();
			}
		} catch (CompanyException e) {
			logger.error(e.getMessage() +e.getStackTrace());
		}
		return companyDTO;
	}

}