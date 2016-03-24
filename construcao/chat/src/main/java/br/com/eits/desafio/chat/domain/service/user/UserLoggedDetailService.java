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
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = null;
//		try{
//			user = service.findUserByEmail(email);
//			LOG.info("User found: {" + email + "}.");
//		}catch(Exception ex){
//			LOG.error("User not found: {" + email + "}.");
//			throw new UsernameNotFoundException("User [" + email + "] not found in the system.");				
//		}
		
		return new UserLogged(user);
	}

}
