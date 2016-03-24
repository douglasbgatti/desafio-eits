package br.com.eits.desafio.chat.domain.entity.user;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type="Roles")
public enum Roles {
	
	ADMINISTRATOR,
	USER;

}
