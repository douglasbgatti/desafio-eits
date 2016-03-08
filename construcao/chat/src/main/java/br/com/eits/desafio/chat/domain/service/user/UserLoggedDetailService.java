package br.com.eits.desafio.chat.domain.service.user;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.entity.user.UserLogged;

@Service
public class UserLoggedDetailService implements UserDetailsService {
	private static final Logger LOG = Logger.getLogger(UserDetailsService.class);

	@Autowired
	private UserService service;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try{
			user = service.findUserByUsername(username);
			LOG.info("User found: {" + username + "}.");
		}catch(Exception ex){
			LOG.error("User not found: {" + username + "}.");
			throw new UsernameNotFoundException("User [" + username + "] not found in the system.");				
		}
		
		return new UserLogged(user);
	}

}
