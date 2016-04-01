package br.com.eits.desafio.chat.domain.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;

public interface IUserChatGroupRepository extends JpaRepository<UserChatGroup, Long>{

}
