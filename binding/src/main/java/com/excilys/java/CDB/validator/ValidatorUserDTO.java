package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.DTO.UserDTO;
import com.excilys.java.CDB.exception.UserException;

public class ValidatorUserDTO {
	public static void validatorName(String name) throws UserException {
		if (name == null || name.trim().isEmpty()) {
		    throw new UserException("userDTO name can't be empty");
		}
	}
	public static void validatorId(String id) throws UserException {
		if (id == null || id == "0") {
		    throw new UserException("id can't be null");
		}
	}

	public static void validate(UserDTO userDto) throws UserException {
		if(userDto == null) {
			throw new UserException("userDto can't be null");
		}
		validatorName(userDto.getUsername());
		validatorId(userDto.getUserId());
	}
}