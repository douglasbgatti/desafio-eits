package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;


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
	
	
	/**
	 * Used for the websocket connection - to differ from new-message / deleted-message / visualized message
	 * 
	 */
	@Transient
	private NotificationType notificationType;
	

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
	
	public Message(Long id, String message, Calendar sentTime, Boolean visualized, UserChatGroup userChatGroup, NotificationType type) {
		this.id = id;
		this.message = message;
		this.sentTime = sentTime;
		this.visualized = visualized;
		this.userChatGroup = userChatGroup;
		this.notificationType = type;
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

	@Transient
	public NotificationType getNotificationType() {
		return notificationType;
	}

	@Transient
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", sentTime=" + sentTime + ", visualized=" + visualized
				+ ", userChatGroup=" + userChatGroup + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((sentTime == null) ? 0 : sentTime.hashCode());
		result = prime * result + ((userChatGroup == null) ? 0 : userChatGroup.hashCode());
		result = prime * result + ((visualized == null) ? 0 : visualized.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (sentTime == null) {
			if (other.sentTime != null)
				return false;
		} else if (!sentTime.equals(other.sentTime))
			return false;
		if (userChatGroup == null) {
			if (other.userChatGroup != null)
				return false;
		} else if (!userChatGroup.equals(other.userChatGroup))
			return false;
		if (visualized == null) {
			if (other.visualized != null)
				return false;
		} else if (!visualized.equals(other.visualized))
			return false;
		return true;
	}	

}
