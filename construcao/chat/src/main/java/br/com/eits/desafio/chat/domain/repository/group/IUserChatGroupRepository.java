package br.com.eits.desafio.chat.domain.repository.group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;

public interface IUserChatGroupRepository extends JpaRepository<UserChatGroup, Long>{

	@Query(value="Select new UserChatGroup(userChatGroup.id) "
			+ "FROM UserChatGroup userChatGroup "
			+ "WHERE userChatGroup.chatGroup.id = :chatGroupId " )
	UserChatGroup getUserChatGroupByChatGroupId(@Param("chatGroupId") Long chatGroupId);

}
