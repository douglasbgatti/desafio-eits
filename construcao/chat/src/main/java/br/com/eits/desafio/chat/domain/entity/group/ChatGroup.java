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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.eits.desafio.chat.domain.entity.user.User;

@Entity
@Table(name="chat_group")
@Audited
@SequenceGenerator(name="CHAT_GROUP_SEQUENCE", sequenceName="CHAT_GROUP_SEQUENCE", allocationSize=1)
@DataTransferObject(javascript="ChatGroup")
public class ChatGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CHAT_GROUP_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="group_name", nullable=false, unique=true, length=50)
	private String groupName;
	
	/**
	 * utilizado na inclusao de usuarios ao grupo no momento de criacao
	 */
	@Transient
	private List<UserChatGroup> userGroupList = new ArrayList<UserChatGroup>();
	
	/**
	 * utilizado para listar os usuarios vinculados ao grupo;
	 */
	@Transient
	private List<User> userList = new ArrayList<User>();
	
	@Transient
	private Message latestMessage;

	public ChatGroup(){}
	
	
	public ChatGroup(Long id, String groupName) {
		this.id = id;
		this.groupName = groupName;
	}

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
	
	@Transient
	public Message getLatestMessage() {
		return latestMessage;
	}
	@Transient
	public void setLatestMessage(Message latestMessage) {
		this.latestMessage = latestMessage;
	}
	
	@Transient
	public List<UserChatGroup> getUserGroupList() {
		return userGroupList;
	}
	@Transient
	public void setUserGroupList(List<UserChatGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}

	@Transient
	public List<User> getUserList() {
		return userList;
	}

	@Transient
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
}
