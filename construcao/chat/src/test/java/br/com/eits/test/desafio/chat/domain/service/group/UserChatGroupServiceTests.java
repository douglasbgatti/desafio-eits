/**
 * 
 */
package br.com.eits.test.desafio.chat.domain.service.group;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.service.group.UserChatGroupService;
import br.com.eits.desafio.chat.security.ContextHolder;
import br.com.eits.test.desafio.chat.AbstractIntegrationTests;

/**
 * @author douglas
 *
 */
@Sql("classpath:/scripts-sql/reset-sequences.sql")
@DbUnitConfiguration(databaseConnection = { "dataSource" })
public class UserChatGroupServiceTests extends AbstractIntegrationTests {
	@Autowired
	private UserChatGroupService userChatGroupService;

	

	public void testGetUserChatGroupByChatGroupIdMustPass() {

		
		
		
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testGetUserChatGroupByIdMustPass() {
		final UserChatGroup userChatGroup = this.userChatGroupService.getUserChatGroupById(1L);
		Assert.assertNotNull(userChatGroup);
		Assert.assertEquals("Development Group", userChatGroup.getChatGroup().getGroupName());
		Assert.assertEquals("Douglas B. Gatti", userChatGroup.getUser().getName());
	}


	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testGetUserChatGroupByUserAndChatGroupMustPass() {
		final UserChatGroup userChatGroup = this.userChatGroupService.getUserChatGroupByUserAndChatGroup(1L, 1L);
		Assert.assertNotNull(userChatGroup);
		Assert.assertEquals("Development Group", userChatGroup.getChatGroup().getGroupName());
		Assert.assertEquals("Douglas B. Gatti", userChatGroup.getUser().getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testGetUserChatGroupByUserAndChatGroupMustFail() {
		final UserChatGroup userChatGroup = this.userChatGroupService.getUserChatGroupByUserAndChatGroup(2L, 3L);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListUserChatGroupByUserIdMustPass(){
		final List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupByUserId(1L);
		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());
		
		final UserChatGroup userChatGroup = list.get(0);
		Assert.assertEquals("Douglas B. Gatti", userChatGroup.getUser().getName());
	}
	

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListUserChatGroupByUserIdAndFilterMustPass(){
		List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupByUserIdAndFilter(1L, "Dev");
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		
		final UserChatGroup ucg = list.get(0);
		Assert.assertEquals("Development Group", ucg.getChatGroup().getGroupName());
		/*
		 * Empty String
		 */
		List<UserChatGroup> list2 = this.userChatGroupService.listUserChatGroupByUserIdAndFilter(1L, "");
		Assert.assertNotNull(list2);
		Assert.assertEquals(3, list2.size());		
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListAllUserChatGroupsMustPass(){
		List<UserChatGroup> list = this.userChatGroupService.listAllUserChatGroups();
		Assert.assertNotNull(list);
		Assert.assertEquals(5, list.size());		
	}
	
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListUserChatGroupsByUserMustPass(){
		this.authenticate(1L);
		
		List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupsByUser();
		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());

	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })	
	public void listUserChatGroupsByUserAndFilterMustPass(){
		this.authenticate(1L);
		
		List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupsByUserAndFilter("Dev");
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());	
		
		final UserChatGroup userChatGroup = list.get(0);
		Assert.assertEquals("Douglas B. Gatti", userChatGroup.getUser().getName());	
	}
		
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })	
	public void testInsertUserChatGroupMustPass(){
		final UserChatGroup userChatGroup = new UserChatGroup();
		
		ChatGroup chatGroup = new ChatGroup();
		chatGroup.setId(3L);
		
		User user = new User();
		user.setId(2L);
		
		userChatGroup.setChatGroup(chatGroup);
		userChatGroup.setUser(user);
		
		this.userChatGroupService.insertUserChatGroup(userChatGroup);
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })	
	public void testInsertUserChatGroupMustFail(){
		final UserChatGroup userChatGroup = new UserChatGroup();
		
		ChatGroup chatGroup = new ChatGroup();
		chatGroup.setId(1L);
		
		User user = new User();
		user.setId(1L);
		
		userChatGroup.setChatGroup(chatGroup);
		userChatGroup.setUser(user);
		
		this.userChatGroupService.insertUserChatGroup(userChatGroup);	
	}
	

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })	
	public void testListUserChatGroupsByChatGroupIdMustPass(){
		List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupsByChatGroupId(1L);
		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testDeleteAllUserChatGroupsByChatGroupIdMustPass(){
		this.userChatGroupService.deleteAllUserChatGroupsByChatGroupId(1L);
		
		List<UserChatGroup> list = this.userChatGroupService.listUserChatGroupsByChatGroupId(1L);
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testRemoveUserFromChatGroupMustPass(){
		this.userChatGroupService.removeUserFromChatGroup(1L, 1L);
		
		UserChatGroup ucg = null;
		try{
			ucg = this.userChatGroupService.getUserChatGroupByUserAndChatGroup(1L, 1L);
			
		}catch(IllegalArgumentException e){
			Assert.assertNull(ucg);
		}
			
	}
	

}
