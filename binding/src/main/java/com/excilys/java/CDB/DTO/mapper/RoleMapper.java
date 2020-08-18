package com.excilys.java.CDB.DTO.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.CDB.DTO.RoleDTO;
import com.excilys.java.CDB.model.Role;

public class RoleMapper {

	private static Logger logger = LoggerFactory.getLogger(UserMapper.class);

	/**
	 * Convert a RoleDTO to a Role
	 * 
	 * @param roleDTO
	 * @return role
	 */
	public static Role mapDtoToRole(RoleDTO roleDTO) {
		Role role = null;
		try {
			if(roleDTO != null) {
				Role.Builder builder = new Role.Builder();
				if (roleDTO.getId() != null) {
					builder.setId(Long.valueOf(roleDTO.getId()));
				}
				if (roleDTO.getName() != null) {
					builder.setName(roleDTO.getName());
				}
				role = builder.build();
			}

		} catch (Exception e) {
			logger.error("Error when mapping a RoleDTO to a Role", e);
		}
		return role;
	}
	
	/**
	 * Convert a Role to a RoleDTO
	 * 
	 * @param role
	 * @return roleDto
	 */
	public static RoleDTO mapRoleToDto(Role role) {
		RoleDTO roleDto = new RoleDTO();
		try {
			if(role != null) {
				if (role.getId() != null) {
					roleDto.setId(role.getId().toString());
				}
				if (role.getName() != null) {
					roleDto.setName(role.getName());
				}
			}

		} catch (Exception e) {
			logger.error("Error when mapping a Role to a RoleDTO", e);
		}
		
		return roleDto;
		
	}
}
