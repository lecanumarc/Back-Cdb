package com.excilys.java.CDB.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.exception.ComputerDateException;
import com.excilys.java.CDB.exception.ComputerNameException;

public class ValidatorComputer {
	
	private static Logger logger = LoggerFactory.getLogger(ValidatorComputer.class);

	public static void validatorName(String name) throws ComputerNameException {
		if ( name == null || name.trim().length()==0 ) {
			logger.info("Name is empty");
		    throw new ComputerNameException();
		}
	}

	public static void validatorDate(String introduced, String discontinued) throws ComputerDateException {
		if(!introduced.isEmpty() && !discontinued.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate introducedDate = LocalDate.parse(introduced, formatter);
			LocalDate discontinuedDate = LocalDate.parse(discontinued, formatter);
			if (discontinuedDate.isBefore(introducedDate)) {
				logger.info("Discontinued date is before introduced date");
				throw new ComputerDateException();
			}
		}
	}
}
