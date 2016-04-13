package br.com.eits.test.desafio.chat.domain.service.user;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;
import br.com.eits.desafio.chat.domain.service.user.UserService;
import br.com.eits.test.desafio.chat.AbstractIntegrationTests;

import org.junit.Assert;


@Sql("classpath:/scripts-sql/reset-sequences.sql")
@DbUnitConfiguration(databaseConnection={"dataSource"})
public class UserServiceTests extends AbstractIntegrationTests{
	private static final Logger LOG = Logger.getLogger(UserServiceTests.class.getName());
	
	@Autowired
	private UserService userService;



	
	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testFindUserByEmailMustPass() {
		this.authenticate(1L);
		String email = "dbg@gmail.com";
		String name = "Douglas B. Gatti";
		
		final User user = this.userService.findUserByEmail(email);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getName(), name);
		Assert.assertNotEquals(null, user.getPassword());
		
	}
	
	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testFindUserDetailsByEmailMustPass() {
		this.authenticate(1L);
		String email = "dbg@gmail.com";
		String name = "Douglas B. Gatti";
		
		final User user = this.userService.findUserDetailsByEmail(email);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getName(), name);
		assertEquals(user.getPassword(), null);

	}
	
	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testInsertUserMustPass() {
		this.authenticate(1L);
		final User user = new User("Joe", "joe@gmail.com", "1234", Roles.USER);
		this.userService.insertUser(user);		
	}

	
	@Test(expected = IllegalArgumentException.class)
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testInsertUserMustFail() {
		this.authenticate(1L);
		//email already in database
		final User user = new User("Joe", "dbg@gmail.com", "1234", Roles.USER);
		this.userService.insertUser(user);
		
	}



	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testAlterUserMustPass() {
		final User user = this.userService.getUser(1L);
		
		user.setEmail("douglasbgatti@gmail.com");
		this.userService.alterUser(user);
		
		final User userAltered = this.userService.getUser(1L);
		assertEquals(userAltered.getEmail(), "douglasbgatti@gmail.com");
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testAlterUserWithDuplicateUniqueFieldMustFail() {
		final User user = this.userService.getUser(1L);
		LOG.warn("USER:" + user);
		
		user.setEmail("jose@gmail.com");
		this.userService.alterUser(user);
	}
	

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testActivateUserMustPass() {
		this.userService.activateUser(2L);
		final User user = this.userService.getUser(2L);
		assertEquals(user.getEnabled(), Boolean.TRUE);
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testDeactivateUserMustPass() {
		this.userService.deactivateUser(1L);
		final User user = this.userService.getUser(1L);
		assertEquals(user.getEnabled(), Boolean.FALSE);
	}


	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testGetUserMustPass() {
		final User user = this.userService.getUser(1L);
		String email = "dbg@gmail.com";
		String name = "Douglas B. Gatti";

		assertEquals(user.getEmail(), email);
		assertEquals(user.getName(), name);
		assertEquals(user.getPassword(), null);
		
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testGetFullUserMustPass() {
		final User user = this.userService.getFullUser(1L);
		String email = "dbg@gmail.com";
		String name = "Douglas B. Gatti";

		assertEquals(user.getEmail(), email);
		assertEquals(user.getName(), name);
		Assert.assertNotEquals(null, user.getPassword());
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testListUsersByFilterMustPass() {
		final List<User> usersList = this.userService.listUsersByFilter("gmail");
		Assert.assertNotNull(usersList);
		Assert.assertEquals(2, usersList.size());
		
		final User user = usersList.get(0);
		Assert.assertNotNull(user.getId());
		Assert.assertNotNull(user.getName());
		Assert.assertNotNull(user.getEmail());
		Assert.assertNull(user.getPassword());
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testListAllUsers() {
		final List<User> usersList = this.userService.listAllUsers();
		Assert.assertNotNull(usersList);
		Assert.assertEquals(3, usersList.size());
		
		final User user = usersList.get(0);
		Assert.assertNotNull(user.getId());
		Assert.assertNotNull(user.getName());
		Assert.assertNotNull(user.getEmail());
		Assert.assertNull(user.getPassword());
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
	{		"/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml",
			"/dataset/UserChatGroupDataset.xml",
			"/dataset/MessageDataset.xml"
			})
	public void testGetAuthenticatedUserMustPass() {
		this.authenticate(1L);
		
		final User user = this.userService.getAuthenticatedUser();
		Assert.assertEquals("dbg@gmail.com", user.getEmail());
		
	}

	@Test
	@DatabaseSetup( type=DatabaseOperation.CLEAN_INSERT, value =
{		"/dataset/UsersDataset.xml",
		"/dataset/ChatGroupDataset.xml",
		"/dataset/UserChatGroupDataset.xml",
		"/dataset/MessageDataset.xml"
		})
	public void testListUsersByRoleMustPass() {
		final List<User> usersList = this.userService.listUsersByRole(Roles.ADMINISTRATOR);
		Assert.assertEquals(1, usersList.size());
		
		final User user = usersList.get(0);
		Assert.assertEquals("dbg@gmail.com", user.getEmail());
	}

}
