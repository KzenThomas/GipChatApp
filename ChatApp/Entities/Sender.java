package ChatApp.Entities;

import java.awt.Menu;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Sender")
public class Sender {

	@Id
	@GeneratedValue
	@Column(name = "senderid")
	private Integer senderid = 0;

	@Column(name = "name")
	private String name;

	public Integer getsenderid() {
		return senderid;
	}

	public String getname() {
		return name;
	}

	public void setsenderid(Integer senderid) {
		this.senderid = senderid;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public Sender(String naam) {
		this.name = "thomas";
	}
	

	
	public Sender() {
		
	}
}