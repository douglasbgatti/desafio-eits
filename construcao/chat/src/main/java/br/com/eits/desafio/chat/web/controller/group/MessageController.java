package br.com.eits.desafio.chat.web.controller.group;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.CredentialException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IMessageRepository;
import br.com.eits.desafio.chat.domain.service.group.MessageService;

@RestController
@RequestMapping("/restful/api/message")
public class MessageController {
private static final Logger LOG = Logger.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	@RequestMapping(value="/insert-message", method=RequestMethod.POST )
	public @ResponseBody Message insertMessage(@RequestBody Message message){
		return this.messageService.insertMessage(message);
	}
	
//	/**
//	 * 
//	 * @param chatGroupId
//	 * @return
//	 */
//	@RequestMapping(value="/list-all-messages-by-chat-group-id/{chatGroupId}", method=RequestMethod.GET )
//	public @ResponseBody Page<Message> listAllMessagesByChatGroupId(@PathVariable Long chatGroupId, @RequestBody PageRequest pageRequest){		
//		return this.messageService.listAllMessagesByChatGroupId(chatGroupId, pageRequest);
//	}
	
	/**
	 * 
	 * @param messageId
	 * @throws CredentialException
	 */
	@RequestMapping(value="/delete-message/{messageId}", method=RequestMethod.DELETE )
	public void deleteMessage(@PathVariable Long messageId) throws CredentialException{
		this.messageService.deleteMessage(messageId);
	}
	
	
	/**
	 * 
	 * @param messageId
	 */
	@RequestMapping(value="/set-message-status-to-visualized/{messageId}", method=RequestMethod.POST )
	public void setMessageStatusToVisualized(@PathVariable Long messageId){
		this.messageService.setMessageStatusToVisualized(messageId);
	}

}
