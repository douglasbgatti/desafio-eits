package br.com.eits.desafio.chat.domain.service.group;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.service.user.UserService;


public class ChatGroupService {
	private static final Logger LOG = Logger.getLogger(ChatGroupService.class);

	public ChatGroup addMemberGroup(Long chatGroupId, Long userId){
		
		return null;
	}
	
	public List<ChatGroup> getAllChatGroups(){
		return null;
	}
	
	public ChatGroup getChatGroup(Long id){
		return null;
	}
	
	public ChatGroup insertChatGroup(ChatGroup chatGroup){
		return chatGroup;
	}
	
	public void removeChatGroup(ChatGroup chatGroup){
		
	}
	
	public ChatGroup removeMember(Long chatGroupID, Long userID){
		return null;
	}
	
	public ChatGroup updateChatGroup(ChatGroup chatGroup){
		return null;
	}
}
