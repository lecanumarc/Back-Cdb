package com.excilys.java.CDB.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {

	@Test
	public void testValidBuilder() {
		Role role = new Role.Builder()
									.setId( new Long(1))
									.setName("user")
									.build();
		User user = new User.Builder()
				.setId(new Long(1))
				.setPassword("$2a$10$Zfmq54qcd.OCxUI6cX3/N.1KHLYSUinBcnvesovIPX/LOiGIFRLke")
				.setUsername("user")
				.setRole(role)
				.build();
		
		assertEquals("role's id is incorrect", new Long(1), role.getId());
		assertEquals("role's name is incorrect", "user", role.getName());

		assertEquals("user's id is incorrect", new Long(1), user.getId());
		assertEquals("user's name is incorrect", "user", user.getUsername());
		assertEquals("user's name is incorrect", "$2a$10$Zfmq54qcd.OCxUI6cX3/N.1KHLYSUinBcnvesovIPX/LOiGIFRLke", user.getPassword());
		assertEquals("user's role is incorrect", role, user.getRole());
	}
}
