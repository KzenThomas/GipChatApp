package ChatApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Messages")
	public class Messages {
		
		@Id
		@GeneratedValue
		@Column(name = "id")
		private Integer id = 0;
		
		@Column(name = "name")
		private String name;
		
		@Column(name = "message")
		private String message;
		

		public Integer getid() {
			return id;
		}
		
		public String getname() {
			return name;
		}
		
		public String getmessage() {
			return message;
		}
		
		public void setid(Integer id) {
			this.id = id;
		}
		
		public void setname(String name) {
			this.name = name;
		}
		
		public void setmessage(String message) {
			this.message = message;
		}
		
		public Messages() {
			
		}
		
		public Messages(String text) {
			this.message = text;
			this.name = "thomas";
		}		
	}




