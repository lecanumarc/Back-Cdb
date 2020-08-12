package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.exception.UserException;
import com.excilys.java.CDB.model.User;

public class ValidatorUser {
	public static void validatorName(String name) throws UserException {
		if (name == null || name.trim().isEmpty()) {
		    throw new UserException("user name can't be empty");
		}
	}
	public static void validatorId(Long id) throws UserException {
		if (id == null || id == 0) {
		    throw new UserException("id can't be null");
		}
	}

	public static void validate(User user) throws UserException {
		if(user == null) {
			throw new UserException("user can't be null");
		}
		validatorName(user.getUsername());
		validatorId(user.getId());
	}
}
