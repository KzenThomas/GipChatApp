package ChatApp;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Sender")
public class Sender {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id = 0;

	@Column(name = "name")
	private String name;

	public Integer getid() {
		return id;
	}

	public String getname() {
		return name;
	}

	public void setid(Integer id) {
		this.id = id;
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