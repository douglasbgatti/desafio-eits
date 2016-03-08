package br.com.eits.desafio.chat.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.eits.desafio.chat.domain.entity.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{ 
	
//	@Query(value="Select new User(user.id, user.firstName, user.lastName, user.username, user.password, user.enabled, user.role) "
//			+ "FROM User user WHERE user.username=:username" )
	User findUserByUsername(String username);

}
