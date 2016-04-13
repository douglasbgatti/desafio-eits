package br.com.eits.desafio.chat.domain.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{ 
	
	@Query(value="Select new User(user.id, user.name, user.email, user.password, user.enabled, user.role) "
			+ " FROM User user WHERE user.email= :email " )
	User findUserByEmail(@Param("email")String email);
	
	@Query(value="Select new User(user.id, user.name, user.email, user.enabled, user.role) "
			+ " FROM User user WHERE user.email= :email " )
	User findUserDetailsByEmail(@Param("email")String email);
	
	@Query(value="Select new User(user.id, user.name, user.email, user.enabled, user.role) "
			+ " FROM User user " )
	List<User> listAllUsers();
	
	
	@Query(value="SELECT new User( user.id, user.name, user.email, user.enabled, user.role ) " +
			   "FROM User user " +
			  "WHERE user.name LIKE %:filter% "
			  	 + "OR user.email LIKE %:filter%" )
	List<User> listUsersByFilter(@Param("filter") String filter);
	
	
	@Query(value="Select new User(user.id, user.name, user.email, user.enabled, user.role) "
			+ " FROM User user WHERE user.id= :id " )
	User findUserById(@Param("id") Long id);
	
	
	@Query(value="Select new User(user.id, user.name, user.email, user.enabled, user.role) "
			+ " FROM User user WHERE user.role= :role " )
	List<User> listUsersByRoles(@Param("role") Roles role);

}
