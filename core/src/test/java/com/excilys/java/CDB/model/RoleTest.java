package com.excilys.java.CDB.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoleTest {

	@Test
	public void testValidBuilder() {
		Role role = new Role.Builder()
									.setId( new Long(1))
									.setName("user")
									.build();
		assertEquals("role's id is incorrect", new Long(1), role.getId());
		assertEquals("role's name is incorrect", "user", role.getName());
	}
}
