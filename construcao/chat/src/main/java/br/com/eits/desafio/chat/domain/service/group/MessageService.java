package br.com.eits.desafio.chat.domain.service.group;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.service.user.UserService;


public class MessageService{
	private static final Logger LOG = Logger.getLogger(MessageService.class);
	
	public Message setVisualizedMessage(Long messageId){
		return null;
	}
	
	public Page<Message> getLatestMessagesByGroup(Long groupId){
		return null;
	}
	
	public Message saveMessage(Message message){
		return message;
	}

}
