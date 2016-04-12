package br.com.eits.desafio.chat.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.service.user.UserService;



@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	private static final Logger LOG = Logger.getLogger( CustomAuthenticationProvider.class.getName() );

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		
		String email = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		LOG.info("EMAIL=" + email + "  PASS:" + password);
		
		User user = userService.findUserByEmail(email);
		
		if(user == null){
			 throw new BadCredentialsException("Email not found.");
		}
		
		LOG.info("USER:" + user);
		if(user.isEnabled() == false){
			throw new DisabledException("User is not Enabled!");
		}
		
		 if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
             throw new BadCredentialsException("Email not found.");
         }
 		 
		 BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
 	 
         if (!pwdEncoder.matches(password, user.getPassword())) {
             throw new BadCredentialsException("Wrong password.");
         }
         
         Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
              
     	
		return new UsernamePasswordAuthenticationToken(user, password, authorities );
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
