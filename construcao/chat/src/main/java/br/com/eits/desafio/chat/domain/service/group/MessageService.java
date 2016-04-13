package br.com.eits.desafio.chat.domain.service.group;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.CredentialException;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	/**
	 * Persists a new message adding the current time 
	 * @param message
	 * @return
	 */
	@Transactional(readOnly=false)
	public Message insertMessage(Message message){
		message.setSentTime(Calendar.getInstance());		
		return this.messageRepository.save(message);
	}
	
	
	/**
	 * Lists all messages from a specific chatGroup
	 * @param chatGroupId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Message> listAllMessagesByChatGroupId(Long chatGroupId){		
		return this.messageRepository.listAllMessagesByChatGroupId(chatGroupId);
	}
	
	/**
	 * Deletes a msg only if the message belongs to the authenticated user
	 * @param messageId
	 * @throws CredentialException
	 */
	@Transactional(readOnly=false)
	public void deleteMessage(Long messageId) throws CredentialException{
		Message message = this.getMessage(messageId);
		
		//only deletes the message if the authenticated user sent the message
		if(message.getUserChatGroup().getUser().getId() == ContextHolder.getAuthenticatedUser().getId()){		
			this.messageRepository.delete(messageId);
		}else{
			throw new CredentialException("User can only delete his own messages!");
		}
	}
	
	/**
	 * Sets a message status to visualized
	 * @param messageId
	 */
	@Transactional(readOnly=false)
	public void setMessageStatusToVisualized(Long messageId){
		Message message = this.messageRepository.findOne(messageId);
		message.setVisualized(Boolean.TRUE);
		
		this.messageRepository.saveAndFlush(message);
	}
	
	/**
	 * Gets a message by the messages id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Message getMessage(Long id){
		return this.messageRepository.findOne(id);
	}

}
