package br.com.eits.desafio.chat.domain.service.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@RemoteProxy (name="userService")
public class UserService {
//	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	
//	public User findUserByEmail(String email){
//		return userRepository.findUserByEmail(email);
//	}

	@Transactional(readOnly = false)
	public User insertUser(User user){
		String hashPassword = new BCryptPasswordEncoder().encode("12345678");
		user.setPassword(hashPassword);
		return userRepository.save(user);
	}
	

	@Transactional(readOnly = false)
	public User alterUser(User user){
		return userRepository.saveAndFlush(user);
	}
	
//	public Page<User> getAllUsers(String username){
//		return  (Page<User>) userRepository.findAll();
//	}
	
	
	public User getUser(Long id){
		return userRepository.findOne(id);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User getDummyUser(){
		return new User(1L, "Jose", "21352623567", "email@gmail.com", Boolean.TRUE, Roles.ADMINISTRATOR);
	}
	
}
