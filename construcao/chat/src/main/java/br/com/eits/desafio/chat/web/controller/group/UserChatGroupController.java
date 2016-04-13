package br.com.eits.desafio.chat.web.controller.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.service.group.ChatGroupService;
import br.com.eits.desafio.chat.domain.service.group.UserChatGroupService;
import br.com.eits.desafio.chat.security.ContextHolder;

@RestController
@RequestMapping("/restful/api/user-chat-group")
public class UserChatGroupController {
	private static final Logger LOG = Logger.getLogger(UserChatGroupController.class);

	@Autowired
	private UserChatGroupService userChatGroupService;
	/**
	 * 
	 * @param userId
	 * @param chatGroupId
	 * @return
	 */
	@RequestMapping(value="/get-user-chat-group-by-user-and-chat-group/{userId}/{chatGroupId}", method=RequestMethod.POST )
	public @ResponseBody UserChatGroup getUserChatGroupByUserAndChatGroup(@PathVariable("userId") Long userId, @PathVariable("chatGroupId")Long chatGroupId) {
		return this.userChatGroupService.getUserChatGroupByUserAndChatGroup(userId, chatGroupId);
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/list-user-chat-groups-by-user", method=RequestMethod.GET )
	public @ResponseBody List<UserChatGroup> listUserChatGroupsByUser(){
		return this.userChatGroupService.listUserChatGroupsByUser();
	}
	
	/**
	 * 
	 * @param userChatGroup
	 * @return
	 */
	@RequestMapping(value="/insert-user-chat-group", method=RequestMethod.POST )
	public @ResponseBody UserChatGroup insertUserChatGroup(@RequestBody UserChatGroup userChatGroup){
		return this.userChatGroupService.insertUserChatGroup(userChatGroup);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/list-user-chat-groups-by-chat-group-id/{id}", method=RequestMethod.GET )
	public @ResponseBody List<UserChatGroup> listUserChatGroupsByChatGroupId(@PathVariable Long id){
		return this.userChatGroupService.listUserChatGroupsByChatGroupId(id);
	}

	/**
	 * 
	 * @param userId
	 * @param chatGroupId
	 */
	@RequestMapping(value="/removeUserFromChatGroup", method=RequestMethod.DELETE )
	public void removeUserFromChatGroup(Long userId, Long chatGroupId){	
		this.userChatGroupService.removeUserFromChatGroup(userId, chatGroupId);
	}
}
