package br.com.eits.desafio.chat.domain.service.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserService {
	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	
	public User findUserByUsername(String username){
		return userRepository.findUserByUsername(username);
	}

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
	
	public List<User> getAllUsers(String username){
		return (List<User>) userRepository.findAll();
	}
	
	
	public User getUser(Long id){
		return userRepository.findOne(id);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
}
