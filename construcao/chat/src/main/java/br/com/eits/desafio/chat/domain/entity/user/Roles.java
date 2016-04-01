package br.com.eits.desafio.chat.domain.entity.user;

import java.io.Serializable;

import org.directwebremoting.annotations.DataTransferObject;
import org.springframework.security.core.GrantedAuthority;

@DataTransferObject(type="enum")
public enum Roles implements Serializable, GrantedAuthority{
	
	ADMINISTRATOR,
	USER;

	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}

}
