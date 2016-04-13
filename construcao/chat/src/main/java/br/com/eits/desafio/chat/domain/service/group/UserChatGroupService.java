package br.com.eits.desafio.chat.domain.service.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.service.user.UserService;
import br.com.eits.desafio.chat.security.ContextHolder;

@Service
@Transactional
@RemoteProxy(name = "userChatGroupService")
public class UserChatGroupService {
	private static final Logger LOG = Logger.getLogger(UserChatGroupService.class);

	@Autowired
	private IUserChatGroupRepository userChatGroupRepository;
	@Autowired
	private ChatGroupService chatGroupService;


	@Transactional(readOnly=true)
	public UserChatGroup getUserChatGroupById(Long userChatGroupId) {
		return userChatGroupRepository.findOne(userChatGroupId);
	}

	@Transactional(readOnly=true)
	public UserChatGroup getUserChatGroupByUserAndChatGroup(Long userId, Long chatGroupId) {
		UserChatGroup userChatGroup = this.userChatGroupRepository.findUserChatGroupByUserAndChatGroup(userId, chatGroupId);
		
		Assert.notNull(userChatGroup, "User is not subscribed to this Chat Group!");
		
		userChatGroup.getUser().setPassword("");		
		
		return userChatGroup;
	}
	
	@Transactional(readOnly=true)
	public List<UserChatGroup> listUserChatGroupByUserId(Long id){
		return this.userChatGroupRepository.findUserChatGroupByUserId(id);
	}
	
	@Transactional(readOnly=true)
	public List<UserChatGroup> listUserChatGroupByUserIdAndFilter(Long id, String filter){
		return this.userChatGroupRepository.findUserChatGroupByUserIdAndFilter(id, filter);
	}
	
	@Transactional(readOnly=true)
	public List<UserChatGroup> listAllUserChatGroups(){
		return this.userChatGroupRepository.findAllUserChatGroups();
	}
	
	
	/**
	 * lista os usuarios de acordo com o role do administrador 
	 * Se o user tiver o role Roles.ADMINISTRAOR lista todos os grupos 
	 * 		-se do tipo Roles.USER lista somente os grupos em que esta inscrito
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserChatGroup> listUserChatGroupsByUser(){
		//TODO USER ROLE CONDITION
		User user = ContextHolder.getAuthenticatedUser();
		List<UserChatGroup> userChatGroupList = new ArrayList<UserChatGroup>();
		
		userChatGroupList = listUserChatGroupByUserId(user.getId());
		
		//settar as ultimas mensagens do chatGroup
		for (Iterator iterator = userChatGroupList.iterator(); iterator.hasNext();) {
			UserChatGroup userChatGroup = (UserChatGroup) iterator.next();
			
			if(userChatGroup.getSentMessages().size() > 0){
				userChatGroup.getChatGroup().setLatestMessage(userChatGroup.getSentMessages().get(0));
			}
		}
		return userChatGroupList;
	}
	
	/**
	 * lista os usuarios de acordo com o role do administrador 
	 * Se o user tiver o role Roles.ADMINISTRAOR lista todos os grupos 
	 * 		-se do tipo Roles.USER lista somente os grupos em que esta inscrito
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserChatGroup> listUserChatGroupsByUserAndFilter(String filter){
		if( filter == null || filter.equals("")){
			return this.listUserChatGroupsByUser();			
		}
		
		User user = ContextHolder.getAuthenticatedUser();
		List<UserChatGroup> userChatGroupList = new ArrayList<UserChatGroup>();
		
		userChatGroupList = listUserChatGroupByUserIdAndFilter(user.getId(), filter);
		
		//settar as ultimas mensagens do chatGroup
		for (Iterator iterator = userChatGroupList.iterator(); iterator.hasNext();) {
			UserChatGroup userChatGroup = (UserChatGroup) iterator.next();
			
			if(userChatGroup.getSentMessages().size() > 0){
				userChatGroup.getChatGroup().setLatestMessage(userChatGroup.getSentMessages().get(0));
			}
		}
		return userChatGroupList;
	}
	
	
	@Transactional(readOnly=false)
	public UserChatGroup insertUserChatGroup(UserChatGroup userChatGroup){
		Assert.notNull(userChatGroup);
		UserChatGroup ucg = null;
		try{
			//Verifies if user is already subscribed to the chat group
			ucg = this.getUserChatGroupByUserAndChatGroup(userChatGroup.getUser().getId(), userChatGroup.getChatGroup().getId());
		}catch(IllegalArgumentException e){
		}
		
		//if the user is not already subscribed to the group a userChatGroup is created
		if(ucg == null){
			userChatGroup = this.userChatGroupRepository.save(userChatGroup);
		}else{
			throw new IllegalArgumentException("User already in this ChatGroup!");
		}
		
		
		return userChatGroup;
	}
	
	
	
	@Transactional(readOnly=true)
	public List<UserChatGroup> listUserChatGroupsByChatGroupId(Long id){
		return this.userChatGroupRepository.listUserChatGroupsByChatGroupId(id);
	}
	
	@Transactional(readOnly=false)
	public void deleteAllUserChatGroupsByChatGroupId(Long chatGroupId){
		this.userChatGroupRepository.deleteAllUserChatGroupsByChatGroupId(chatGroupId);
	}
	
	@Transactional(readOnly=false)
	public void removeUserFromChatGroup(Long userId, Long chatGroupId){
		UserChatGroup userChatGroup = this.getUserChatGroupByUserAndChatGroup(userId, chatGroupId);
		
		this.userChatGroupRepository.delete(userChatGroup.getId());
	}
}
