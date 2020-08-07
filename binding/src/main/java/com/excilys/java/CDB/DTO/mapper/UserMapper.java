package com.excilys.java.CDB.DTO.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.UserDTO;
import com.excilys.java.CDB.model.User;

public class UserMapper {

	private static Logger logger = LoggerFactory.getLogger(UserMapper.class);

	/**
	 * Convert a UserDTO to a User
	 * 
	 * @param userDTO
	 * @return user
	 */
	public static User mapDtoToUser(UserDTO userDTO) {
		User user = new User();

		try {
			Long id = null;
			if (userDTO.getUserId() != null) {
				id = Long.valueOf(userDTO.getUserId());
			}
			user.setId(id);
			user.setUsername(userDTO.getUsername());
			user.setPassword(userDTO.getPassword());
			user.setRole(userDTO.getRole());

		} catch (Exception e) {
			logger.error("Error when mapping a UserDTO to a User", e);
		}
		return user;
	}

}
