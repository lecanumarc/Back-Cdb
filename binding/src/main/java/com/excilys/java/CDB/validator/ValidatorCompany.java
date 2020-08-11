package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.exception.CompanyNameException;
import com.excilys.java.CDB.model.Company;

public class ValidatorCompany {
	public static void validatorName(String name) throws CompanyException {
		if (name == null || name.trim().isEmpty()) {
		    throw new CompanyNameException("company name can't be empty");
		}
	}

	public static void validate(Company company) throws CompanyException {
		if(company == null) {
			throw new CompanyException("company can't be null");
		}
		validatorName(company.getName());
	}
}
