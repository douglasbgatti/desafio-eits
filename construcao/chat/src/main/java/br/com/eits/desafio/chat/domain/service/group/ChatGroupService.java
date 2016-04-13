package br.com.eits.desafio.chat.domain.service.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.service.user.UserService;
import br.com.eits.desafio.chat.exception.DuplicateChatGroupNameException;
import br.com.eits.desafio.chat.security.ContextHolder;

@Service
@Transactional
@RemoteProxy (name="chatGroupService")
public class ChatGroupService {
	private static final Logger LOG = Logger.getLogger(ChatGroupService.class);
	
	@Autowired
	private IChatGroupRepository chatGroupRepository;
	@Autowired 
	private UserChatGroupService userChatGroupService;
	@Autowired
	private UserService userService;	

	@Transactional(readOnly=true)
	public List<ChatGroup> listAllChatGroups(){
		return this.chatGroupRepository.findAll();
	}
	
	/**
	 * Gets chatGroup by Id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public ChatGroup getChatGroup(Long id){		
		return this.chatGroupRepository.findOne(id);
	}
	
	/**
	 * Insert new chatGroup if groupName doesnt exist in db
	 * @param chatGroup
	 * @return
	 * @throws DuplicateChatGroupNameException
	 */
	@Transactional(readOnly=false)
	public ChatGroup insertChatGroup(ChatGroup chatGroup) throws DuplicateChatGroupNameException{		
		if((verifyChatGroupNameIsUsed(chatGroup.getGroupName()))== null){
			chatGroup = chatGroupRepository.save(chatGroup);
			
			List<User> adminUserList = this.userService.listUsersByRole(Roles.ADMINISTRATOR);
			
			//	INSERT ADMINISTRATOR USERS INTO GROUP
			for (Iterator<User> iterator = adminUserList.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();				
				UserChatGroup userChatGroup = new UserChatGroup();
				userChatGroup.setUser(user);				
				chatGroup.getUserGroupList().add(userChatGroup);				
			}
			
			for (Iterator<?> iterator = chatGroup.getUserGroupList().iterator(); iterator.hasNext();) {
				UserChatGroup userChatGroup = (UserChatGroup) iterator.next();
				userChatGroup.setChatGroup(chatGroup);
				
				this.userChatGroupService.insertUserChatGroup(userChatGroup);		
			}
		}
		else{
			throw new DuplicateChatGroupNameException("Chat Group name already exists in Database!");
		}
		return chatGroup;
	}
	
	/**
	 * Verify is chatGroupName already exists in Database ;
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public ChatGroup verifyChatGroupNameIsUsed(String name){
		return chatGroupRepository.verifyChatGroupNameIsUsed(name);
	}
	
	/**
	 * Gets chat group and the users that are subscribed to  this group;
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=false)
	public ChatGroup getChatGroupAndUsersList(Long id){
		ChatGroup chatGroup = getChatGroup(id);
		chatGroup.setUserList(new ArrayList<User>());
		
		List<UserChatGroup> userChatGroupList = this.userChatGroupService.listUserChatGroupsByChatGroupId(id);
		
		for (Iterator<UserChatGroup> iterator = userChatGroupList.iterator(); iterator.hasNext();) {
			UserChatGroup userChatGroup = (UserChatGroup) iterator.next();
			chatGroup.getUserList().add(userChatGroup.getUser());			
		}			
		return chatGroup;
	}
	
	
	@Transactional(readOnly=false)
	public ChatGroup editChatGroup(ChatGroup chatGroup){
		return this.chatGroupRepository.saveAndFlush(chatGroup);
	}
	
	@Transactional(readOnly=false)
	public void deleteChatGroup(Long id){		
		this.chatGroupRepository.delete(id);
	}
		
}
