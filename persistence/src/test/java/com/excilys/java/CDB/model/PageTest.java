package com.excilys.java.CDB.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PageTest {
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNextPage() {
		Pagination page = Mockito.spy(new Pagination());
		page.nextPage();
		assertEquals(2,page.getCurrentPage());
		assertEquals(21,page.getFirstLine());
	}

	@Test
	public void testPreviousPage() {
		Pagination page = Mockito.spy(new Pagination());
		page.setCurrentPage(3);
		page.setFirstLine(41);
		page.previousPage();
		assertEquals(2,page.getCurrentPage());
		assertEquals(21,page.getFirstLine());
	}
	
	@Test
	public void testTotaLPages() {
		Pagination page = Mockito.spy(new Pagination());
		int totalPage = page.getTotalPages(45);
		assertEquals(3, totalPage);
	}
}
