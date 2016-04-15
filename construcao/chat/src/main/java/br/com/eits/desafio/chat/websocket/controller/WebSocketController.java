package br.com.eits.desafio.chat.websocket.controller;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.entity.group.NotificationType;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.repository.group.IMessageRepository;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.service.group.MessageService;
import br.com.eits.desafio.chat.domain.service.group.UserChatGroupService;

@Controller
public class WebSocketController {
	private static final Logger LOG = Logger.getLogger(WebSocketController.class);

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * Notifies the users in the chatgroup of a new message
	 * @param chatGroupId
	 * @param message
	 */
	@MessageMapping("/websocket/{chatGroupId}")
	public void sendMessage(@DestinationVariable Long chatGroupId, String message) {
		TextMessage txt = new TextMessage(message);

		template.convertAndSend("/topic/message/" + chatGroupId, txt);
	}

	
	/**
	 * Notifies the users when a new group is created so the chat groups can be reloaded
	 */
	@MessageMapping("/websocket/groups")
	public void notifyGroups(String chatGroup) {
		template.convertAndSend("/topic/new-group/", new TextMessage(chatGroup));
	}

}
