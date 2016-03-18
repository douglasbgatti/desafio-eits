package br.com.eits.desafio.chat.domain.entity.group;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.eits.desafio.chat.domain.entity.user.User;

@Entity
@Table(name="user_group")
@Audited
@SequenceGenerator(name="USER_GROUP_SEQUENCE", sequenceName="USER_GROUP_SEQUENCE", allocationSize=1)
public class UserGroup {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="USER_GROUP_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) 
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "chat_group_id", referencedColumnName = "id", nullable = false) 
	private ChatGroup chatGroup;
	
	@OneToMany
	private List<Message> sentMessages;
	
}
