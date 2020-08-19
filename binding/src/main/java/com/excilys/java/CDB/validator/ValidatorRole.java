package com.excilys.java.CDB.validator;

import com.excilys.java.CDB.exception.RoleException;
import com.excilys.java.CDB.model.Role;

public class ValidatorRole {
	public static void validatorName(String name) throws RoleException {
		if (name == null || name.trim().isEmpty()) {
		    throw new RoleException("role name can't be empty");
		}
	}

	public static void validate(Role role) throws RoleException {
		if(role == null) {
			throw new RoleException("role can't be null");
		}
		validatorName(role.getName());
	}
}
