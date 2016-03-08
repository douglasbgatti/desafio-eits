package br.com.eits.desafio.chat.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, code= HttpStatus.NOT_FOUND)
	public ModelAndView noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex){
		ModelAndView view = new ModelAndView("error");
		view.addObject("mensagem", "This page does not exist!");
		view.addObject("url", req.getRequestURL());
		view.addObject("exception", ex);		
		
		return view;
	}

}
