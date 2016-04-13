package br.com.eits.desafio.chat.web.controller.group;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;
import br.com.eits.desafio.chat.domain.service.group.ChatGroupService;
import br.com.eits.desafio.chat.exception.DuplicateChatGroupNameException;

@RestController
@RequestMapping("/restful/api/chat-group")
public class ChatGroupController {

	private static final Logger LOG = Logger.getLogger(ChatGroupController.class);

	@Autowired
	private ChatGroupService chatGroupService;

	/**
	 * 
	 * @param chatGroup
	 * @return
	 * @throws DuplicateChatGroupNameException 
	 */
	@RequestMapping(value="/insert-chat-group", method=RequestMethod.POST )
	public @ResponseBody ChatGroup insertChatGroup(@RequestBody ChatGroup chatGroup) throws DuplicateChatGroupNameException {
		return this.chatGroupService.insertChatGroup(chatGroup);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/verify-chat-group-name-is-used/{name}", method=RequestMethod.GET )
	public @ResponseBody ChatGroup verifyChatGroupNameIsUsed(@PathVariable String name) {
		return this.chatGroupService.verifyChatGroupNameIsUsed(name);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/get-chat-group-and-users-list/{id}", method=RequestMethod.GET )
	public @ResponseBody ChatGroup getChatGroupAndUsersList(@PathVariable Long id) {
		return this.chatGroupService.getChatGroupAndUsersList(id);
	}

	/**
	 * 
	 * @param chatGroup
	 * @return
	 */
	@RequestMapping(value="/edit-chat-group", method=RequestMethod.POST )
	public @ResponseBody ChatGroup editChatGroup(@RequestBody ChatGroup chatGroup) {
		return this.chatGroupService.editChatGroup(chatGroup);
	}

	/**
	 * 
	 * @param id
	 */
	@RequestMapping(value="/delete-chat-group/{id}", method=RequestMethod.DELETE )
	public void deleteChatGroup(@PathVariable Long id) {
		this.chatGroupService.deleteChatGroup(id);
	}

}
