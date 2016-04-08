package br.com.eits.desafio.chat.domain.service.group;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IMessageRepository;

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
	
	public void deleteMessage(Long messageId){
		this.messageRepository.delete(messageId);
	}
	
	public void setMessageStatusToVisualized(Long messageId){
		Message message = this.messageRepository.findOne(messageId);
		message.setVisualized(Boolean.TRUE);
		
		this.messageRepository.saveAndFlush(message);
	}
	

}
