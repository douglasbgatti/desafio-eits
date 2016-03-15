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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

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
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "user_group_id", referencedColumnName = "id", nullable = false) 
	private List<UserGroup> userGroupList = new ArrayList<UserGroup>();
	
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



	public Message getLatestMessage() {
		return latestMessage;
	}

	public void setLatestMessage(Message latestMessage) {
		this.latestMessage = latestMessage;
	}

	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}
	
	
}
