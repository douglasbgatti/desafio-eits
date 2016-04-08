package br.com.eits.desafio.chat.domain.repository.group;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;



public interface IChatGroupRepository extends JpaRepository<ChatGroup, Long>{
	
	@Query(value="Select new ChatGroup(chatGroup.id, chatGroup.groupName) "
			+ " FROM ChatGroup chatGroup WHERE chatGroup.groupName = :name " )
	ChatGroup verifyChatGroupNameIsUsed(@Param("name") String name);

	

}
