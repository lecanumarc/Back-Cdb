package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.exception.CompanyNameException;

public class ValidatorCompanyDTO {

	public static void validatorName(String name) throws CompanyException {
		if (name == null || name.trim().isEmpty()) {
		    throw new CompanyNameException("companyDTO name can't be empty");
		}
	}

	public static void validate(CompanyDTO companyDTO) throws CompanyException {
		if(companyDTO == null) {
			throw new CompanyException("companyDTO can't be null");
		}
		validatorName(companyDTO.getCompanyName());
	}
}
