package br.com.eits.desafio.chat.domain.entity.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import org.hibernate.envers.Audited;




@Entity
@Table(name="users")
@Audited
@SequenceGenerator(name="USER_SEQUENCE", sequenceName="USER_SEQUENCE", allocationSize=1)
public class User{
	
	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="USER_SEQUENCE")
	@Column(name="id", unique=true)
	private Long id;
	
	@Column(name="first_name", nullable=false, length=144)
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=144)
	private String lastName;
	
	@Column(name="username", nullable=false, length=144, unique=true)
	private String username;
	
	@Column(name="password", nullable=false, length=144)
	private String password;
	
	@Column(name="email", nullable=false, length=144)
	private String email;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="role_type", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private Roles role;
	
	@Column(name="sex", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private Sex sex;
	
	
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	public User(String firstName, String lastName, String username, String email, Roles role, Sex sex) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = "12345678";
		this.email = email;
		this.enabled = true;
		this.role = role;
		this.sex = sex;
	}
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", enabled=" + enabled + ", role=" + role + ", sex="
				+ sex + "]";
	}
	public  static void main(String[] args){

		
	}
	

}
