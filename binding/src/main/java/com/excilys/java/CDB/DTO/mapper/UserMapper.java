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
			if (userDTO.getUserId() != null) {
				user.setId(Long.valueOf(userDTO.getUserId()));
			}
			user.setUsername(userDTO.getUsername());
			user.setPassword(userDTO.getPassword());
			if(userDTO.getRole() != null) {
				user.setRole(RoleMapper.mapDtoToRole(userDTO.getRole()));
			}
		} catch (Exception e) {
			logger.error("Error when mapping a UserDTO to a User", e);
		}
		return user;
	}

	
	public static UserDTO mapUserToDto(User user) {
		UserDTO userDTO = new UserDTO();
		try {
			if(user.getId() != null) {
				userDTO.setUserId(user.getId().toString());
			}
			if(user.getUsername() != null) {
				userDTO.setUsername(user.getUsername());
			}
			if(user.getPassword() != null) {
				userDTO.setPassword(user.getPassword());
			}
			if(userDTO.getRole() != null) {
				user.setRole(RoleMapper.mapDtoToRole(userDTO.getRole()));
			}
		} catch (Exception e) {
			logger.error("Error when mapping a User to a UserDTO", e);
		}
		
		return userDTO;
		
	}
}
