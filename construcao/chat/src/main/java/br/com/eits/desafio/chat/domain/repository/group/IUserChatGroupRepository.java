package br.com.eits.desafio.chat.domain.repository.group;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.desafio.chat.domain.entity.group.Message;
import br.com.eits.desafio.chat.domain.entity.group.UserChatGroup;

public interface IUserChatGroupRepository extends JpaRepository<UserChatGroup, Long>{

	@Query(value="Select new UserChatGroup(userChatGroup.id) "
			+ "FROM UserChatGroup userChatGroup "
			+ "WHERE userChatGroup.chatGroup.id = :chatGroupId " )
	UserChatGroup getUserChatGroupByChatGroupId(@Param("chatGroupId") Long chatGroupId);
	
	
	@Query(value="Select new UserChatGroup(userChatGroup.id, userChatGroup.user, userChatGroup.chatGroup) " 
			+ "FROM UserChatGroup userChatGroup "
			+ "WHERE userChatGroup.chatGroup.id = :chatGroupId "
			+ "	AND userChatGroup.user.id = :userId " )
	UserChatGroup findUserChatGroupByUserAndChatGroup(@Param("userId") Long userId ,@Param("chatGroupId") Long chatGroupId);
	
	@Query(value="Select new UserChatGroup(userChatGroup.id, userChatGroup.user, userChatGroup.chatGroup) " 
			+ "FROM UserChatGroup userChatGroup "
			+ "WHERE userChatGroup.chatGroup.id = :chatGroupId ")
	List<UserChatGroup> listUserChatGroupsByChatGroupId(@Param("chatGroupId") Long chatGroupId);
	
	
	@Query(value="Select new UserChatGroup(userChatGroup.id, userChatGroup.chatGroup) " 
			+ " FROM UserChatGroup userChatGroup "
			+ " WHERE userChatGroup.user.id = :userId ")
	List<UserChatGroup> findUserChatGroupByUserId(@Param("userId") Long userId);
	
	@Query(value="Select new UserChatGroup(userChatGroup.id, userChatGroup.chatGroup) " 
			+ " FROM UserChatGroup userChatGroup "
			+ " WHERE userChatGroup.user.id = :userId AND userChatGroup.chatGroup.groupName LIKE %:filter% ")
	List<UserChatGroup> findUserChatGroupByUserIdAndFilter(@Param("userId") Long userId, @Param("filter") String filter);
	
	@Query(value="Select new UserChatGroup(userChatGroup.id, userChatGroup.chatGroup) " 
			+ "FROM UserChatGroup userChatGroup ")
	List<UserChatGroup> findAllUserChatGroups();
	
	@Modifying
	@Query(value="DELETE FROM UserChatGroup userChatGroup "
			+ " WHERE userChatGroup.chatGroup.id = :chatGroupId ")
	void deleteAllUserChatGroupsByChatGroupId(@Param("chatGroupId") Long chatGroupId);
	


}
