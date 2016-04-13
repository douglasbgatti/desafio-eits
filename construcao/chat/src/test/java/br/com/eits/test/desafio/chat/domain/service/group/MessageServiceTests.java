package br.com.eits.test.desafio.chat.domain.service.group;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.CredentialException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.service.group.MessageService;
import br.com.eits.desafio.chat.domain.service.group.UserChatGroupService;
import br.com.eits.desafio.chat.security.ContextHolder;
import br.com.eits.test.desafio.chat.AbstractIntegrationTests;

@Sql("classpath:/scripts-sql/reset-sequences.sql")
@DbUnitConfiguration(databaseConnection = { "dataSource" })
public class MessageServiceTests extends AbstractIntegrationTests {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserChatGroupService userChatGroupService;

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testInsertMessageMustPass() {
		final UserChatGroup userChatGroup = this.userChatGroupService.getUserChatGroupById(1L);
		final Message message = new Message();
		message.setMessage("This is a new msg");
		message.setUserChatGroup(userChatGroup);

		this.messageService.insertMessage(message);
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testListAllMessagesByChatGroupIdMustPass() {
		final List<Message> messageList = this.messageService.listAllMessagesByChatGroupId(1L);
		Assert.assertNotNull(messageList);

		Assert.assertEquals(5, messageList.size());

	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testDeleteMessageMustPass() throws CredentialException {
		this.authenticate(1L);

		this.messageService.deleteMessage(1L);
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testDeleteMessageMustFail() {

		this.authenticate(2L);

		try {
			this.messageService.deleteMessage(1L);
			Assert.fail();
		} catch (CredentialException e) {
			
		}
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testGetMessageMustPass() {
		final Message msg = this.messageService.getMessage(1L);
		Assert.assertNotNull(msg);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = { "/dataset/UsersDataset.xml",
			"/dataset/ChatGroupDataset.xml", "/dataset/UserChatGroupDataset.xml", "/dataset/MessageDataset.xml" })
	public void testSetMessageStatusToVisualizedMustPass() {
		final Message msg = this.messageService.getMessage(5L);
		Assert.assertFalse(msg.getVisualized());
		
		this.messageService.setMessageStatusToVisualized(5L);
		
		final Message msgVisualized = this.messageService.getMessage(5L);
		Assert.assertTrue(msgVisualized.getVisualized());	
	}



}
