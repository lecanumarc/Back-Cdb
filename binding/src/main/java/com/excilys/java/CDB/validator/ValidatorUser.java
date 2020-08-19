package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.exception.RoleException;
import com.excilys.java.CDB.exception.UserException;
import com.excilys.java.CDB.model.User;

public class ValidatorUser {
	public static void validatorName(String name) throws UserException {
		if (name == null || name.trim().isEmpty()) {
			throw new UserException("user name can't be empty");
		}
	}

	public static void validate(User user) throws UserException {
		if(user == null) {
			throw new UserException("user can't be null");
		}
		validatorName(user.getUsername());
		if(user.getRole() == null) {
			throw new UserException("user's role can't be null");
		}
		try {
			ValidatorRole.validate(user.getRole());
		} catch (RoleException e) {
			throw new UserException("Error in user's role");
		}
	}
}
