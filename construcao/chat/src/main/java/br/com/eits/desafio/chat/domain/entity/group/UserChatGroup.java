package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OrderBy;
import org.hibernate.envers.Audited;

import br.com.eits.desafio.chat.domain.entity.user.User;

@Entity
@Table(name="user_chat_group")
@Audited
@SequenceGenerator(name="USER_GROUP_SEQUENCE", sequenceName="USER_GROUP_SEQUENCE", allocationSize=1)
@DataTransferObject(javascript="UserChatGroup")
public class UserChatGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="USER_GROUP_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@ManyToOne(optional=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(optional=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
//	@JoinColumn(name = "chat_group_id", referencedColumnName = "id") 
	private ChatGroup chatGroup;
	
	@OneToMany(mappedBy = "userChatGroup", fetch = FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@OrderBy(clause = "id ASC")
	private List<Message> sentMessages = new ArrayList<Message>();
	
	public UserChatGroup() {
	}
	
	public UserChatGroup(Long id, User user, ChatGroup chatGroup, List<Message> sentMessages) {
		this.id = id;
		this.user = user;
		this.chatGroup = chatGroup;
		this.sentMessages = sentMessages;
	}
	
	public UserChatGroup(Long id, User user, ChatGroup chatGroup) {
		this.id = id;
		this.user = user;
		this.chatGroup = chatGroup;
	}
	
	public UserChatGroup(Long id, ChatGroup chatGroup) {
		this.id = id;
		this.chatGroup = chatGroup;
	}
	
	
	
	
	
	public UserChatGroup(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}



	
	
}
