package br.com.eits.desafio.chat.domain.service.group;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.CredentialException;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IMessageRepository;
import br.com.eits.desafio.chat.security.ContextHolder;

@Service
@RemoteProxy (name="messageService")
public class MessageService{
	private static final Logger LOG = Logger.getLogger(MessageService.class);
	
	@Autowired
	private IChatGroupRepository chatGroupRepository;
	@Autowired
	private IMessageRepository messageRepository;
	
	
	public Message insertMessage(Message message){
		message.setSentTime(Calendar.getInstance());
		LOG.info("MESSAGE:" + message.toString());
		
		return this.messageRepository.save(message);
	}
	

	public Message updateMessage(Message message){
		return this.messageRepository.saveAndFlush(message);
	}
	
	public List<Message> listAllMessagesByChatGroupId(Long chatGroupId){
		
		return this.messageRepository.listAllMessagesByChatGroupId(chatGroupId);
	}
	
	public void deleteMessage(Long messageId) throws CredentialException{
		Message message = this.getMessage(messageId);
		
		if(message.getUserChatGroup().getUser().getId() == ContextHolder.getAuthenticatedUser().getId()){		
			this.messageRepository.delete(messageId);
		}else{
			new CredentialException("User can only delete his own messages!");
		}
	}
	
	public void setMessageStatusToVisualized(Long messageId){
		Message message = this.messageRepository.findOne(messageId);
		message.setVisualized(Boolean.TRUE);
		
		this.messageRepository.saveAndFlush(message);
	}
	
	public Message getMessage(Long id){
		return this.messageRepository.findOne(id);
	}
	

}
