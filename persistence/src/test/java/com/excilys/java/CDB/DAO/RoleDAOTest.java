package com.excilys.java.CDB.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.java.CDB.configuration.SpringTestConfig;
import com.excilys.java.CDB.model.Role;
import com.excilys.java.CDB.model.User;
import com.excilys.java.CDB.persistence.DAO.RoleDAO;
import com.excilys.java.CDB.persistence.DAO.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class, loader = AnnotationConfigContextLoader.class)
@Sql({"/script.sql"})
public class RoleDAOTest {

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	UserDAO userDAO;

	@Test
	public void addValidRole() {
		Role role = new Role.Builder()
				.setId( new Long(3))
				.setName("newRole")
				.build();
		
		roleDAO.save(role);
		Role roleFound = roleDAO.findById((long)3).get();
		assertEquals("Error during role add", roleFound.getId(), role.getId());
		assertEquals("Error during role add", roleFound.getName(), role.getName());

	}

	@Test
	public void editValidRole() {
		Role role = new Role.Builder()
				.setId( new Long(1))
				.setName("userUpdated")
				.build();
		roleDAO.save(role);

		Role roleFound = roleDAO.findById((long)1).get();

		assertEquals("role's name is incorrect", "userUpdated",  roleFound.getName());
		assertEquals("role's id is incorrect", new Long(1), roleFound.getId());

	}

	@Test
	public void validDeleteRoleWithLinkedComputers() {
		Long roleId = new Long(1);
		List<User> users = userDAO.findAllByRoleId(roleId);
		users.stream().forEach((User user) -> {userDAO.delete(user);});
		roleDAO.deleteById(roleId);

		assertEquals("Wrong number of users linked to a specific role", 2, users.size());
		assertFalse("Role still exists after deletion", roleDAO.existsById(roleId));
		assertEquals("linked users still exist after role deletion", 0, userDAO.findAllByRoleId(roleId).size());
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void invalidDeleteRoleWithLinkedComputers() {
		roleDAO.deleteById(new Long(1));
	}
	
	@Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
	public void invalidDeleteRole() {
		roleDAO.deleteById(new Long(50));
	}

	@Test
	public void validFindRoleById() {
		Role roleFound = roleDAO.findById(new Long(1)).get();

		Role roleExpected = new Role.Builder()
				.setName("user")
				.setId(new Long(1))
				.build();

		assertEquals("role's name is incorrect", roleExpected.getName(), roleFound.getName());
		assertEquals("role's id is incorrect", roleExpected.getId(), roleFound.getId());
		assertEquals("Error during role finding", roleExpected, roleFound);
	}

	@Test
	public void validListByPage() {
		int index = 0;
		int rows = 5;
		Page<Role> page = roleDAO.findAll(PageRequest.of(index, rows));
		List<Role> list = page.getContent();
		Role firstRole = new Role.Builder()
				.setName("user")
				.setId(new Long(1))
				.build();
		Role lastRole = new Role.Builder()
				.setName("admin")
				.setId(new Long(1))
				.build();
		assertTrue("page has empty content", page.hasContent());
		assertEquals("Wrong amount of lines", 2, page.getSize());
		assertEquals("Wrong first role value", firstRole, list.get(0));
		assertEquals("Wrong last role value", lastRole, list.get(1));
	}

	@Test
	public void validFindAll() {
		List<Role> list = roleDAO.findAll();
		Role firstRole = new Role.Builder()
				.setName("user")
				.setId(new Long(1))
				.build();
		Role lastRole = new Role.Builder()
				.setName("admin")
				.setId(new Long(2))
				.build();

		assertEquals("Wrong amount of lines", 2, list.size());
		assertEquals("Wrong first role value", firstRole.getId(), list.get(0).getId());
		assertEquals("Wrong first role value", firstRole.getName(), list.get(0).getName());
		assertEquals("Wrong first role value", lastRole.getId(), list.get(1).getId());
		assertEquals("Wrong first role value", lastRole.getName(), list.get(1).getName());
	}
	
//	@Test
//	public void validFilteredOrderedPage() {
//		int index = 0;
//		int rows = 5;
//		String filter = "Apple";
//		String column = "id";
//		
//		PageRequest pageReq = PageRequest.of(index, rows , Sort.by(column).ascending());
//		
//		Page<Role> page = roleDAO.findByNameContaining(filter, pageReq);
//
//		
//		List<Role> list = page.getContent();
//		Role firstRole = new Role.Builder()
//				.setName("Apple Inc.")
//				.setId(new Long(1))
//				.build();
//		
//		assertTrue("page has empty content", page.hasContent());
//		assertEquals("Wrong list size", 1, page.getSize());
//		assertEquals("Wrong first role value", firstRole, list.get(0));
//	}

}
