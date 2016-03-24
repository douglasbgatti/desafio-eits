package br.com.eits.desafio.chat.domain.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.desafio.chat.domain.entity.group.UserGroup;


@Entity
@Table(name="users")
@Audited
@SequenceGenerator(name="USER_SEQUENCE", sequenceName="USER_SEQUENCE", allocationSize=1)
@DataTransferObject(javascript="User")
public class User{
	
	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="USER_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="name", nullable=false, length=144)
	private String name;
	
	@Column(name="password", nullable=false, length=144)
	private String password;
	
	@Column(name="email", nullable=false, length=144, unique = true)
	private String email;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@Column(name="role_type", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private Roles role;
	
	
	@OneToMany
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();
	
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	
	
	public User(){}
	
	public User(String name, String email, Roles role) {
		this.name = name;		
		this.password = "12345678";
		this.email = email;
		this.enabled = true;
		this.role = role;
	}
	
	public User(Long id, String name, String password, String email, Boolean enabled, Roles role) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", enabled="
				+ enabled + ", role=" + role + "]";
	}
	
	
	
}
