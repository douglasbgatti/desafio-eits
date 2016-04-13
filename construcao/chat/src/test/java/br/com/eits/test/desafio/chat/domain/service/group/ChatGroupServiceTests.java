package br.com.eits.test.desafio.chat.domain.service.group;

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
import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.service.group.ChatGroupService;
import br.com.eits.desafio.chat.exception.DuplicateChatGroupNameException;
import br.com.eits.test.desafio.chat.AbstractIntegrationTests;

@Sql("classpath:/scripts-sql/reset-sequences.sql")
@DbUnitConfiguration(databaseConnection = { "dataSource" })
public class ChatGroupServiceTests extends AbstractIntegrationTests {

	@Autowired
	private ChatGroupService chatGroupService;
	

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListAllChatGroupsMustPass(){
		final List<ChatGroup> chatGroupList = this.chatGroupService.listAllChatGroups();
		
		Assert.assertEquals( 3 , chatGroupList.size() );
		final ChatGroup chatGroup = chatGroupList.get(0);
		Assert.assertNotNull(chatGroup);
	}
	
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testGetChatGroupMustPass(){	
		final ChatGroup chatGroup = this.chatGroupService.getChatGroup(1L);
		Assert.assertNotNull(chatGroup);		
		Assert.assertEquals("Development Group", chatGroup.getGroupName());
	}
	

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testInsertChatGroupMustPass(){	
		final ChatGroup chatGroup = new ChatGroup();
		
		chatGroup.setGroupName("New Group");
		ChatGroup newChatGroup = null;
		try {
			newChatGroup = this.chatGroupService.insertChatGroup(chatGroup);
			
		} catch (DuplicateChatGroupNameException e) {
				Assert.fail();
		}
		
		Assert.assertNotNull(newChatGroup);
		
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testInsertChatGroupMustFail(){	
		final ChatGroup chatGroup = new ChatGroup();
		ChatGroup newChatGroup = null;
		
		chatGroup.setGroupName("Development Group");
		
		try {
			newChatGroup = this.chatGroupService.insertChatGroup(chatGroup);
			Assert.fail();
		} catch (DuplicateChatGroupNameException e) {
			Assert.assertNull(newChatGroup);
		}
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void verifyChatGroupNameIsUsedMustPass(){
		final ChatGroup chatGroup = this.chatGroupService.verifyChatGroupNameIsUsed("Development Group");
		Assert.assertNotNull(chatGroup);
		
		final ChatGroup nonExistentChatGroup = this.chatGroupService.verifyChatGroupNameIsUsed("blablabla");
		Assert.assertNull(chatGroup);		
	}
	
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void getChatGroupAndUsersListMustPass(){
		final ChatGroup chatGroup = this.chatGroupService.getChatGroupAndUsersList(1L);
		Assert.assertNotNull(chatGroup);
		Assert.assertNotNull(chatGroup.getUserGroupList());
		
		Assert.assertEquals(3, chatGroup.getUserList().size());
	
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void editChatGroup(){
		final ChatGroup chatGroup = this.chatGroupService.getChatGroup(1L);
		chatGroup.setGroupName("Dev. Team");
		this.chatGroupService.editChatGroup(chatGroup);
		
		final ChatGroup editedChatGroup = this.chatGroupService.getChatGroup(1L);
		Assert.assertEquals("Dev. Team", editedChatGroup.getGroupName());
	}
	
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void deleteChatGroupMustPass(){		
		this.chatGroupService.deleteChatGroup(1L);
		
		final ChatGroup chatGroup = this.chatGroupService.getChatGroup(1L);
		Assert.assertNull(chatGroup);
	}
}
