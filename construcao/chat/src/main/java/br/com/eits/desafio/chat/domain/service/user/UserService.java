package br.com.eits.desafio.chat.domain.service.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;


@Service
@Transactional
@RemoteProxy (name="userService")
public class UserService {
	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private MailSender mailSenderService;
	
	
	@Transactional(readOnly=true)
	public User findUserByEmail(String email){
		return this.userRepository.findUserByEmail(email);
	}


	@Transactional(readOnly=false)
	public User insertUser(User user){
		String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(hashPassword);
		user = this.userRepository.save(user);
		 
		 if(user != null){
			 	String subject = new String("Welcome to Desafio Chat!");
				StringBuilder body = new StringBuilder();
				body.append("Hello " + user.getName() + "!\n\n");
				body.append("Your account has been created at Desafio-Chat.\n");
				body.append("Login with your E-mail and start chatting now!");
			
				this.mailSenderService.sendMail(subject, body.toString() , user.getEmail());			 
		 }
		
		return user;
	}
	
	@Transactional
	public User alterUser(User user){		
		if(user.getPassword() != null && !user.getPassword().equals("")){
			String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(hashPassword);
		}else{
			user.setPassword( getFullUser(user.getId() ).getPassword() );
		}
		
		
		return this.userRepository.saveAndFlush(user);
	}
	
	
	@Transactional
	public User activateUser(Long id){
		User user = getFullUser(id);
		user.setEnabled(true);
		
		return this.userRepository.saveAndFlush(user);
	}
	
	@Transactional
	public User deactivateUser(Long id){
		User user = getFullUser(id);
		user.setEnabled(false);
		
		return this.userRepository.saveAndFlush(user);
	}
	
//	public Page<User> getAllUsers(String username){
//		return  (Page<User>) userRepository.findAll();
//	}
	
	@Transactional(readOnly=true)
	public User getUser(Long id){
		return this.userRepository.findUserById(id);
	}
	
	@Transactional(readOnly=true)
	public User getFullUser(Long id){
		return this.userRepository.findOne(id);
	}
	
	@Transactional(readOnly=true)
	public List<User> listUsersByFilter(String filter){
		return this.userRepository.listUsersByFilter(filter);
	}
	
	@Transactional(readOnly=true)
	public List<User> listAllUsers(){
		return this.userRepository.findAll();
	}
	
	public User getDummyUser(){
		return new User(1L, "Jose", "21352623567", "email@gmail.com", Boolean.TRUE, Roles.ADMINISTRATOR);
	}
	
	
}
