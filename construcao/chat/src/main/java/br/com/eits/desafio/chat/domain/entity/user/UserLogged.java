package br.com.eits.desafio.chat.domain.entity.user;

import org.springframework.security.core.authority.AuthorityUtils;

public class UserLogged extends org.springframework.security.core.userdetails.User{
	
	private User user;

	public UserLogged(User user) {
		super(
				user.getUsername(),
				user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRole().toString()));
		
		this.user = user;
	}
	
	public Long getId(){
		return user.getId();
	}
	
	public Roles getRoles(){
		return user.getRole();
	}
	

}
