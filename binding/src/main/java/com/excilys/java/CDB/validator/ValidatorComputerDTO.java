package com.excilys.java.CDB.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.CompanyDTO;
import com.excilys.java.CDB.DTO.ComputerDTO;
import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.exception.ComputerDateException;
import com.excilys.java.CDB.exception.ComputerException;
import com.excilys.java.CDB.exception.ComputerNameException;

public class ValidatorComputerDTO {
	private static Logger logger = LoggerFactory.getLogger(ValidatorComputerDTO.class);

	public static void validatorName(String name) throws ComputerNameException {
		if (name == null || name.trim().isEmpty()) {
		    throw new ComputerNameException("ComputerDTO name can't be empty");
		}
	}

	public static void validatorDate(String introduced, String discontinued) throws ComputerDateException {
		if(introduced != null && discontinued != null) {
			if(!introduced.isEmpty() && !discontinued.isEmpty()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate introducedDate = LocalDate.parse(introduced, formatter);
				LocalDate discontinuedDate = LocalDate.parse(discontinued, formatter);
				if (discontinuedDate.isBefore(introducedDate)) {
					throw new ComputerDateException("Discontinued date can't be before introduced date in computerDTO");
				}
			}
		}
	}
	
	public static void validatorCompany(CompanyDTO companyDTO) throws ComputerException {
		try {
			ValidatorCompanyDTO.validate(companyDTO);
		} catch (CompanyException e) {
			throw new ComputerException("CompanyDTO exception" +e.getMessage());
		}
	}
	
	public static void validate(ComputerDTO computerDTO) throws ComputerException {
		if(computerDTO == null) {
			throw new ComputerException("computerDTO can't be null");
		}
		validatorName(computerDTO.getComputerName());
		validatorDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		if(computerDTO.getCompanyDTO() != null) {
			validatorCompany(computerDTO.getCompanyDTO());
		}
	}
}
