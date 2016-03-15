package br.com.eits.desafio.chat.domain.repository.group;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.desafio.chat.domain.entity.group.ChatGroup;



public interface IGroupRepository extends JpaRepository<ChatGroup, Long>{


}
