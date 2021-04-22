package ChatApp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	public void setid(Integer conversationsid) {
		this.conversationsid = conversationsid;
	}

	public void setsender(String name) {
		this.name = name;
	}
	 @ManyToMany(mappedBy = "coversations")
	    private Set<Messages> employees = new HashSet<>();

	public Conversations(String gespreksnaam) {
		this.name = "gesprek1";

	}
	
	public Conversations() {
		
	}
}