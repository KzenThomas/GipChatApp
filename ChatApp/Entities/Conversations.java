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

@Entity(name = "Conversations")
public class Conversations {

	@Id
	@GeneratedValue
	@Column(name = "conversationsid")
	private Integer conversationsid = 0;

	@Column(name = "name")
	private String name;

	public Integer getconversationsid() {
		return conversationsid;
	}

	public String getname() {
		return name;
	}

	public void setconversationsid(Integer conversationsid) {
		this.conversationsid = conversationsid;
	}

	public void setname(String name) {
		this.name = name;
	}
	 @ManyToMany(cascade = { CascadeType.ALL })
	    @JoinTable(
	        name = "ConversationToMessage", 
	        joinColumns = { @JoinColumn(name = "messageid") }, 
	        inverseJoinColumns = { @JoinColumn(name = "conversationsid") }
	    )
	    Set<Conversations> conversations = new HashSet<>();

	public Conversations(String gespreksnaam) {
		this.name = "gesprek1";

	}
	
	public Conversations() {
		
	}
}