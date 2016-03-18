package br.com.eits.desafio.chat.domain.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.desafio.chat.domain.entity.group.Message;

public interface IMessageRepository  extends JpaRepository<Message, Long>{

}
