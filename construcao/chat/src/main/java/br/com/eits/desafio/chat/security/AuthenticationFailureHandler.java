package br.com.eits.desafio.chat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication failures Handlers and HttpServlet responses for each type of exception
 * @author dougl
 *
 */
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler
{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException
	{
		if ( exception instanceof BadCredentialsException )
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage() );
		}

		if ( exception instanceof LockedException || exception instanceof DisabledException )
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_FORBIDDEN, exception.getMessage() );
		}

		// lança excessao caso a senha esteja expirada
		if ( exception instanceof CredentialsExpiredException )
		{
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.sendError( HttpServletResponse.SC_NOT_ACCEPTABLE, exception.getMessage() );
		}
	}
}