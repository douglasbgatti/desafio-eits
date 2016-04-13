package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.eits.desafio.chat.domain.entity.user.User;


@Entity
@Table(name="message")
@Audited
@SequenceGenerator(name="MESSAGE_SEQUENCE", sequenceName="MESSAGE_SEQUENCE", allocationSize=1)
@DataTransferObject(javascript="Message")
public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="MESSAGE_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="message", nullable=false, length=255)
	private String message;

	
	@Column(name="sent_time", nullable=false)
	private Calendar sentTime;
	
	@Column(name="visualized", nullable=true)
	private Boolean visualized;
	
	
	@ManyToOne(fetch=FetchType.EAGER ,optional=true)
	private UserChatGroup userChatGroup;
	

	public Message(){}	
	
	public Message(String message, boolean visualized) {
		this.message = message;
		this.sentTime = Calendar.getInstance();
		this.visualized = visualized;
	}
	
	public Message(Long id, String message, Calendar sentTime, Boolean visualized, UserChatGroup userChatGroup) {
		this.id = id;
		this.message = message;
		this.sentTime = sentTime;
		this.visualized = visualized;
		this.userChatGroup = userChatGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getSentTime() {
		return sentTime;
	}

	public void setSentTime(Calendar sentTime) {
		this.sentTime = sentTime;
	}

	public Boolean getVisualized() {
		return visualized;
	}

	public void setVisualized(Boolean visualized) {
		this.visualized = visualized;
	}

	public UserChatGroup getUserChatGroup() {
		return userChatGroup;
	}

	public void setUserChatGroup(UserChatGroup userChatGroup) {
		this.userChatGroup = userChatGroup;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", sentTime=" + sentTime + ", visualized=" + visualized
				+ ", userChatGroup=" + userChatGroup + "]";
	}	

}
