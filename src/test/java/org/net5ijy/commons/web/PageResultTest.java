package org.net5ijy.commons.web;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.net5ijy.commons.web.pagination.PageResult;

public class PageResultTest {

	@Test
	public void testGetPageNum() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 2, 10, 104, 5);

		assertEquals(2, pageUsers.getPageNum());
	}

	@Test
	public void testGetPageSize() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 2, 10, 104, 5);

		assertEquals(10, pageUsers.getPageSize());
	}

	@Test
	public void testGetTotal() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 2, 10, 104, 5);

		assertEquals(104, pageUsers.getTotal());
	}

	@Test
	public void testGetPageCount() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 2, 10, 100, 5);

		assertEquals(10, pageUsers.getPageCount());
	}

	@Test
	public void testGetStartPage() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 1, 10, 104, 5);
		assertEquals(1, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 2, 10, 104, 5);
		assertEquals(1, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 3, 10, 104, 5);
		assertEquals(1, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 4, 10, 104, 5);
		assertEquals(2, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 5, 10, 104, 5);
		assertEquals(3, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 6, 10, 104, 5);
		assertEquals(4, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 7, 10, 104, 5);
		assertEquals(5, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 8, 10, 104, 5);
		assertEquals(6, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 9, 10, 104, 5);
		assertEquals(7, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 10, 10, 104, 5);
		assertEquals(7, pageUsers.getStartPage());

		pageUsers = new PageResult<User>(users, 11, 10, 104, 5);
		assertEquals(7, pageUsers.getStartPage());
	}

	@Test
	public void testGetEndPage() {

		List<User> users = getUsers(10);

		PageResult<User> pageUsers = new PageResult<User>(users, 1, 10, 104, 6);
		assertEquals(6, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 2, 10, 104, 6);
		assertEquals(6, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 3, 10, 104, 6);
		assertEquals(6, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 4, 10, 104, 6);
		assertEquals(7, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 5, 10, 104, 6);
		assertEquals(8, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 6, 10, 104, 6);
		assertEquals(9, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 7, 10, 104, 6);
		assertEquals(10, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 8, 10, 104, 6);
		assertEquals(11, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 9, 10, 104, 6);
		assertEquals(11, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 10, 10, 104, 6);
		assertEquals(11, pageUsers.getEndPage());

		pageUsers = new PageResult<User>(users, 11, 10, 104, 6);
		assertEquals(11, pageUsers.getEndPage());
	}

	private List<User> getUsers(int count) {
		List<User> users = new ArrayList<User>();
		for (int i = 1; i <= count; i++) {
			User u = new User(i, "admin", "123456");
			users.add(u);
		}
		return users;
	}

	public class User {

		private Integer id;

		private String username;

		private String password;

		public User() {
			super();
		}

		public User(Integer id, String username, String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
