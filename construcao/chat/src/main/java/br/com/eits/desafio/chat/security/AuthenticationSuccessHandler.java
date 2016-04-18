package br.com.eits.desafio.chat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import br.com.eits.desafio.chat.domain.entity.user.User;

/**
 * 
 * @author douglas
 *
 */
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{
	private static final Logger LOG = Logger.getLogger( AuthenticationSuccessHandler.class.getName() );
	
	/**
	 * 
	 */
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{
		User usuario = ( User ) authentication.getPrincipal();		
		ContextHolder.getAuthenticatedUser();
		LOG.info( "onAuthenticationSuccess :: " + authentication );
	}
}
