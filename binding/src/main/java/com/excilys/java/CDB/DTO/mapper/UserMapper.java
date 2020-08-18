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
		User user = null;
		try {
			User.Builder builder = new User.Builder();
			if (userDTO.getUserId() != null) {
				builder.setId(Long.valueOf(userDTO.getUserId()));
			}
			builder.setUsername(userDTO.getUsername());
			builder.setPassword(userDTO.getPassword());
			if(userDTO.getRole() != null) {
				builder.setRole(RoleMapper.mapDtoToRole(userDTO.getRole()));
			}
			user = builder.build();
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
				userDTO.setRole(RoleMapper.mapRoleToDto(user.getRole()));
			}
		} catch (Exception e) {
			logger.error("Error when mapping a User to a UserDTO", e);
		}
		
		return userDTO;
		
	}
}
