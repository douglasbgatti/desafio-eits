package br.com.eits.desafio.chat.web.controller.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import com.sun.glass.ui.Application;

import br.com.eits.desafio.chat.domain.entity.user.Roles;
import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;
import br.com.eits.desafio.chat.domain.service.user.MailSender;
import br.com.eits.desafio.chat.domain.service.user.UserService;
import br.com.eits.desafio.chat.security.ContextHolder;


@RestController
@RequestMapping("/restful/api/user")
public class UserController {
	
	private static final Logger LOG = Logger.getLogger(UserService.class);

	@Autowired
	private UserService userService;

	/**
	 * Finds the user details by searching for the users email
	 * @param email
	 * @return User
	 */
	@RequestMapping(value="/find-user-details-by-email/{email}", method=RequestMethod.GET )
	public @ResponseBody User findUserDetailsByEmail(@PathVariable String email) {
		return this.userService.findUserDetailsByEmail(email);
	}


	/**
	 * Insert users into system db
	 * @param user
	 * @return User
	 */
	@RequestMapping(value="/insert-user", method=RequestMethod.POST)
	public @ResponseBody User insertUser(@RequestBody User user) {
		return this.userService.insertUser(user);
	}

	/**
	 *  alters user by passing an user object as param returning the altered user after updating in db
	 * @param user
	 * @return User
	 */
	@RequestMapping(value="/alter-user", method=RequestMethod.POST)
	public @ResponseBody User alterUser(@RequestBody User user) {
		return this.userService.alterUser(user);
	}

	/**
	 * Sets the enabled flag to true in the db by passing the users id
	 * @param id 
	 * @return User
	 */
	@RequestMapping(value="/activate-user/{id}", method=RequestMethod.POST)
	public @ResponseBody User activateUser(@PathVariable Long id) {
		return this.userService.activateUser(id);
	}

	/**
	 * Deactivates the user by setting the enabled flag to false in the db  
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/deactivate-user/{id}", method=RequestMethod.POST)
	public @ResponseBody User deactivateUser(@PathVariable Long id) {
		return this.userService.deactivateUser(id);
	}

	/**
	 * Gets user by id
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/get-user/{id}", method=RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable Long id) {
		return this.userService.getUser(id);
	}

	/**
	 * Gets the full user including password by id
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/get-full-user/{id}", method=RequestMethod.GET)
	public @ResponseBody User getFullUser(@PathVariable Long id) {
		return this.userService.getFullUser(id);
	}

	/**
	 * Lists users by filtering name or email 
	 * @param filter
	 * @return List<User>
	 */
//	@RequestMapping(value="/list-users-by-filter/{filter}/{page},{limit}", method=RequestMethod.GET)
//	public @ResponseBody List<User> listUsersByFilter(@PathVariable String filter, @PathVariable int page, @PathVariable int limit ) {
//		return this.userService.listUsersByFilter(filter);
//	}

	/**
	 *  Lists all the users in the db
	 * @return List<User>
	 */
	@RequestMapping(value="/list-all-users", method=RequestMethod.GET)
	public @ResponseBody List<User> listAllUsers() {
		return this.userService.listAllUsers();
	}

	/**
	 * Gets the authenticated user
	 * @return User
	 */
	@RequestMapping(value="/get-authenticated-user", method=RequestMethod.GET)
	public @ResponseBody User getAuthenticatedUser() {
		return ContextHolder.getAuthenticatedUser();
	}

	/**
	 * Lists the users according to the role passed in the params
	 * @param role
	 * @return List<User>
	 */
	@RequestMapping(value="/list-users-by-role/{role}", method=RequestMethod.GET)
	public @ResponseBody List<User> listUsersByRole(@PathVariable Roles role) {
		return this.userService.listUsersByRole(role);
	}
}
