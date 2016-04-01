package br.com.eits.desafio.chat.domain.service.group;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;

//@Service
//@RemoteProxy (name="messageService")
public class MessageService{
	private static final Logger LOG = Logger.getLogger(MessageService.class);
	
	@Autowired
	private IChatGroupRepository groupRepository;
	
//	public Message setVisualizedMessage(Long messageId){
//		return null;
//	}
//	
//	public Page<Message> getLatestMessagesByGroup(Long groupId){
//		return null;
//	}
//	
//	public Message saveMessage(Message message){
//		return message;
//	}

	public String getDummyString(){
		return "dummy";
	}
}
