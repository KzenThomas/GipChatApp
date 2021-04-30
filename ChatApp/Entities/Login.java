package ChatApp.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@Entity(name = "Login")
public class Login {

	@Id
	@GeneratedValue
	@Column(name = "loginid")
	private Integer loginid = 0;

	@Column(name = "username")
	private String username;
	
	@Column(name = "wachtwoord")
	private String wachtwoord;

	public Integer getloginid() {
		return loginid;
	}
	
	public Integer setloginid() {
		return loginid;
	}
	
	public String getusername() {
		return username;
	}
	
	public String setusername() {
		return username;
	}
	
	public String getwachtwoord() {
		return wachtwoord;
	}

	public String setwachtwoord() {
		return wachtwoord;
	}

	public Login( String userText , String pwdText) {
		this.username = userText;
		this.wachtwoord = pwdText;
	}
	
	public Login( String userText) {
		this.username = userText;
	}
	
	public Login() {
		
	}
	
	 @ManyToMany(cascade = { CascadeType.ALL })
	    @JoinTable(
	        name = "conversationstologin", 
	        joinColumns = { @JoinColumn(name = "conversationsid") }, 
	        inverseJoinColumns = { @JoinColumn(name = "loginid") }
	    )
	    Set<Conversations> conversations = new HashSet<>();
	
	@Override
	public String toString() {
		return this.username; 
	}
}