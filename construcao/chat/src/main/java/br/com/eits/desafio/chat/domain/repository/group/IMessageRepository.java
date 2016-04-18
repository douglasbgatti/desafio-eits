package br.com.eits.desafio.chat.domain.repository.group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.desafio.chat.domain.entity.group.Message;

public interface IMessageRepository extends JpaRepository<Message, Long> {


	@Query(value = "select new Message(message.id, message.message, message.sentTime, message.visualized, message.userChatGroup) "
			+ " FROM Message message " 
			+ " WHERE message.userChatGroup.chatGroup.id = :chatGroupId ORDER BY message.id ASC")
	List<Message> listAllMessagesByChatGroupId(@Param("chatGroupId") Long chatGroupId);
}
