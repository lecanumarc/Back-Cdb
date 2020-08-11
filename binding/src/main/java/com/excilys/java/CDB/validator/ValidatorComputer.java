package com.excilys.java.CDB.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;

import com.excilys.java.CDB.exception.CompanyException;
import com.excilys.java.CDB.exception.ComputerDateException;
import com.excilys.java.CDB.exception.ComputerException;
import com.excilys.java.CDB.exception.ComputerNameException;
import com.excilys.java.CDB.model.Company;
import com.excilys.java.CDB.model.Computer;

public class ValidatorComputer {
	private static Logger logger = LoggerFactory.getLogger(ValidatorComputer.class);

	public static void validatorName(String name) throws ComputerNameException {
		if (name == null || name.trim().isEmpty()) {
			throw new ComputerNameException("ComputerDTO name can't be empty");
		}
	}

	public static void validatorDate(LocalDate introduced, LocalDate discontinued) throws ComputerDateException {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		if(introduced != null) {
			try {
				formatter.parse(introduced.toString());
			} catch (ParseException e) {
				throw new ComputerDateException("computer's introduced date has bad format");
			}
		}
		if(discontinued != null) {
			try {
				formatter.parse(discontinued.toString());
			} catch (ParseException e) {
				throw new ComputerDateException("computer's discontinued date has bad format");
			}
		}
		if(introduced != null && discontinued != null) {
			if (discontinued.isBefore(introduced)) {
				throw new ComputerDateException("Discontinued date can't be before introduced date in computerDTO");
			}
		}
	}

	public static void validatorCompany(Company company) throws ComputerException {
		try {
			ValidatorCompany.validate(company);
		} catch (CompanyException e) {
			throw new ComputerException("Company exception" +e.getMessage());
		}
	}

	public static void validate(Computer computer) throws ComputerException {
		if(computer == null) {
			throw new ComputerException("computer can't be null");
		}
		validatorName(computer.getName());
		validatorDate(computer.getIntroduced(), computer.getDiscontinued());
		if(computer.getCompany() != null) {
			validatorCompany(computer.getCompany());
		}
	}
}
