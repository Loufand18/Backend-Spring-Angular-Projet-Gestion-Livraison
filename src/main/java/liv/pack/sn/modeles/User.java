package liv.pack.sn.modeles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Utilisateur")
public class User {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
@Column(nullable=false, updatable=false)
private String email;
private String usename;
private String role;
private String password;
private String ppwd;


public User(String email, String usename, String role, String password, String ppwd) {
	super();
	this.email = email;
	this.usename = usename;
	this.role = role;
	this.password = password;
	this.ppwd = ppwd;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUsename() {
	return usename;
}
public void setUsename(String usename) {
	this.usename = usename;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getPpwd() {
	return ppwd;
}
public void setPpwd(String ppwd) {
	this.ppwd = ppwd;
}




}
