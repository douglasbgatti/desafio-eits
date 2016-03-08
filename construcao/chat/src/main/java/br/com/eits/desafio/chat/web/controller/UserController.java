package br.com.eits.desafio.chat.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.Sex;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.service.user.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public @ResponseBody List<User> findAll(){
		return  userService.findAll();
	}
	
	@RequestMapping(value="/insertUser", method=RequestMethod.POST)
	public @ResponseBody User insertUser(@RequestBody(required = true) User user){
		System.out.println("USER INSERT: " + user.toString()); 
		return userService.insertUser(user);
	}
	
	@RequestMapping(value="/user/{id}")
	public User getUser(@PathVariable("id") Long id){
		return userService.getUser(id);
	}
	
	@RequestMapping(value="/getDummyUser", method=RequestMethod.GET)
	public @ResponseBody User getDummyUser(){
		return new User("Maria", "da silva", "maria", "maaria@email.com",Roles.USER, Sex.FEMININO);
	}
	
	@RequestMapping(value="/alterUser")
	public User updateUser(@RequestBody(required=true) User user) {
		return userService.alterUser(user);
	}
	
	
}
