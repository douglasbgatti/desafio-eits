package br.com.eits.desafio.chat.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.eits.desafio.chat.domain.entity.group.Message;

@Controller
@RequestMapping("/")
public class MainController {
	
	@RequestMapping
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String loginPage(){
		return "login-index";
	}
	
	@RequestMapping(value="login-fail", method=RequestMethod.GET)
	public String loginFail(){
		return "login-fail";
	}
	
}
