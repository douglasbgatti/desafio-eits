package br.com.eits.desafio.chat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="auth")
public class LoginController {

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "error", required = false ) boolean error,
			@RequestParam(value = "logout", required = false) boolean logout, 
			ModelMap model){
		
		if(error){
			model.addAttribute("error", "Username or Password incorrect!");
			return new ModelAndView("login", model);
		}
		
		if(logout){
			model.addAttribute("logout", "User has logged out successfully!");
			return new ModelAndView("login", model);
		}
		
		return new ModelAndView("redirect:/");
	}
	
	
	@RequestMapping(value="/denied", method = RequestMethod.GET)
	public ModelAndView accessDenied(){
		return new ModelAndView("error", "message","Access Denied: Restricted area!");
	}
	
}
