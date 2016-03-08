package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.eits.desafio.chat.domain.entity.message.Message;
import br.com.eits.desafio.chat.domain.entity.user.User;

@Entity
@Table(name="message")
//@Audited
@SequenceGenerator(name="CHAT_GROUP_SEQUENCE", sequenceName="CHAT_GROUP_SEQUENCE", allocationSize=1)
public class ChatGroup implements Serializable{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CHAT_GROUP_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="group_name", nullable=false, length=60)
	private String groupName;
	
	/**
	 * lista de usuários pertencentes ao grupo de chat
	 */
	@NotAudited
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinTable(name = "chat_group_members", 
		joinColumns ={ 
			@JoinColumn(name = "chat_group_id", referencedColumnName = "id", nullable = false) 
		},
		inverseJoinColumns = { 
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) 
		}
	)
	private List<User> userList = new ArrayList<User>();
	
	@NotAudited
	@Transient
	private Message latestMessage;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Message getLatestMessage() {
		return latestMessage;
	}

	public void setLatestMessage(Message latestMessage) {
		this.latestMessage = latestMessage;
	}
	
	
}
