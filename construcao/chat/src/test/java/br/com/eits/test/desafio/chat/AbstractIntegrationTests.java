package br.com.eits.test.desafio.chat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import br.com.eits.desafio.chat.domain.entity.user.User;
import br.com.eits.desafio.chat.domain.repository.group.IChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.group.IMessageRepository;
import br.com.eits.desafio.chat.domain.repository.group.IUserChatGroupRepository;
import br.com.eits.desafio.chat.domain.repository.user.IUserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class 
    })
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-jpa.xml" , "file:src/main/webapp/WEB-INF/spring/spring-security.xml"})
@Transactional
public abstract class AbstractIntegrationTests {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IChatGroupRepository chatGroupRepository;
	@Autowired
	private IUserChatGroupRepository userChatGroupRepository;
	@Autowired
	private IMessageRepository messageRepository;
	
	
	protected UsernamePasswordAuthenticationToken authenticate( Long userId )
    {
		final User user = this.userRepository.findOne( userId );
		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( user, null, user.getAuthorities() );
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication( token );
		return token;
    }
}
