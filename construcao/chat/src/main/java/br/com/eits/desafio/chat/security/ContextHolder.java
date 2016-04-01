package br.com.eits.desafio.chat.security;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.eits.desafio.chat.domain.entity.user.User;

/**
 *
 * @author rodrigo
 * @since 24/07/2013
 * @version 1.0
 * @category
 */
public class ContextHolder
{
	/**
	 *
	 * @return
	 */
	public static User getAuthenticatedUser()
	{
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if ( authentication != null && authentication.getPrincipal() instanceof User )
		{
			return ( User ) authentication.getPrincipal();
		}

		throw new InsufficientAuthenticationException( "Não há um usuário autenticado." );
	}
	
}
