package br.com.eits.desafio.chat.domain.service.group;

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
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.service.user.UserService;

@Service
@Transactional
@RemoteProxy (name="chatGroupService")
public class ChatGroupService {
	private static final Logger LOG = Logger.getLogger(ChatGroupService.class);
	
	@Autowired
	private IChatGroupRepository chatGroupRepository;
	@Autowired
	private IUserChatGroupRepository userChatRepository;

	public ChatGroup addMemberGroup(Long chatGroupId, Long userId){		
		return null;
	}
	
	public Page<ChatGroup> getAllChatGroups(){
		return null;
	}
	
	public ChatGroup getChatGroup(Long id){
		return null;
	}
	
	public ChatGroup insertChatGroup(ChatGroup chatGroup){
		
		if((verifyChatGroupNameIsUsed(chatGroup.getGroupName()))== null){
			chatGroup = chatGroupRepository.save(chatGroup);
			
//			for (Iterator iterator = chatGroup.getUserGroupList().iterator(); iterator.hasNext();) {
//				UserChatGroup userChatGroup = (UserChatGroup) iterator.next();
//				userChatGroup.setChatGroup(chatGroup);
//				
//				insertUserChatGroup(userChatGroup);			
//			}
		}				
		
		
		return chatGroup;
	}
	
	public UserChatGroup insertUserChatGroup(UserChatGroup userChatGroup){
		return this.userChatRepository.save(userChatGroup);
	}
	
	public void removeChatGroup(ChatGroup chatGroup){
		
	}
	
	public ChatGroup removeMember(Long chatGroupID, Long userID){
		return null;
	}
	
	public ChatGroup updateChatGroup(ChatGroup chatGroup){
		return null;
	}
	
	@Transactional(readOnly=true)
	public ChatGroup verifyChatGroupNameIsUsed(String name){
		return chatGroupRepository.verifyChatGroupNameIsUsed(name);
	}
}
