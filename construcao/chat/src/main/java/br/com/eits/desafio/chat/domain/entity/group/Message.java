package br.com.eits.desafio.chat.domain.entity.group;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

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

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.eits.desafio.chat.domain.entity.user.User;


@Entity
@Table(name="message")
@Audited
@SequenceGenerator(name="MESSAGE_SEQUENCE", sequenceName="MESSAGE_SEQUENCE", allocationSize=1)
public class Message implements Serializable{
	
	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="MESSAGE_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="message", nullable=false, length=255)
	private String message;

	
	@Column(name="sent_time", nullable=false)
	private LocalDate sentTime;
	
	@Column(name="visualized")
	private Boolean visualized;
	

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "user_group_id", referencedColumnName = "id", nullable = true) 
	private UserGroup userGroup;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getSentTime() {
		return sentTime;
	}
	public void setSentTime(LocalDate sentTime) {
		this.sentTime = sentTime;
	}
	public boolean isVisualized() {
		return visualized;
	}
	public void setVisualized(boolean visualized) {
		this.visualized = visualized;
	}
	public Message(String message, boolean visualized) {
		super();
		this.message = message;
		this.sentTime = LocalDate.now();
		this.visualized = visualized;
	}
	
	
	public Message(){}
	
	

}
