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

	public UserChatGroup getUserChatGroupByChatGroupId(Long chatGroupId) {
		return this.userChatGroupRepository.getUserChatGroupByChatGroupId(chatGroupId);
	}

	public UserChatGroup getUserChatGroupById(Long userChatGroupId) {
		return userChatGroupRepository.findOne(userChatGroupId);
	}

	@Transactional(readOnly=true)
	public UserChatGroup getUserChatGroupByUserAndChatGroup(Long userId, Long chatGroupId) {
		UserChatGroup userChatGroup = this.userChatGroupRepository.findUserChatGroupByUserAndChatGroup(userId, chatGroupId);
		if(userChatGroup != null)
			userChatGroup.getUser().setPassword(null);
		
		return userChatGroup;
	}
	
	public List<UserChatGroup> listUserChatGroupByUserId(Long id){
		return this.userChatGroupRepository.findUserChatGroupByUserId(id);
	}
	
	public List<UserChatGroup> listUserChatGroupByUserIdAndFilter(Long id, String filter){
		return this.userChatGroupRepository.findUserChatGroupByUserIdAndFilter(id, filter);
	}
	
	public List<UserChatGroup> listAllUserChatGroups(){
		return this.userChatGroupRepository.findAllUserChatGroups();
	}
	
	
	/**
	 * lista os usuarios de acordo com o role do administrador 
	 * Se o user tiver o role Roles.ADMINISTRAOR lista todos os grupos 
	 * 		-se do tipo Roles.USER lista somente os grupos em que esta inscrito
	 * @return
	 */
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
	public List<UserChatGroup> listUserChatGroupsByUserAndFilter(String filter){
		if( filter == null || filter.equals("")){
			return this.listUserChatGroupsByUser();			
		}
		//TODO USER ROLE CONDITION
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
	
	
	public UserChatGroup updateUserChatGroup(UserChatGroup userChatGroup){
		return this.userChatGroupRepository.saveAndFlush(userChatGroup);
	}
	
	public UserChatGroup insertUserChatGroup(UserChatGroup userChatGroup){
		Assert.notNull(userChatGroup);
		
		UserChatGroup ucg = this.getUserChatGroupByUserAndChatGroup(userChatGroup.getUser().getId(), userChatGroup.getChatGroup().getId());
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
	
//	public UserChatGroup addUserToChatGroup(User user, Long chatGroupId){		
//		ChatGroup chatGroup = this.chatGroupService.getChatGroup(chatGroupId);
//		
//		UserChatGroup userChatGroup = new UserChatGroup();
//		userChatGroup.setUser(user);
//		userChatGroup.setChatGroup(chatGroup);
//		
//		return this.insertUserChatGroup(userChatGroup);
//	}
	
	public void removeUserFromChatGroup(Long userId, Long chatGroupId){
		UserChatGroup userChatGroup = this.getUserChatGroupByUserAndChatGroup(userId, chatGroupId);
		
		this.userChatGroupRepository.delete(userChatGroup.getId());
	}

}
