package ChatApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Messages")
	public class Messages {
		
		@Id
		@Column(name = "id")
		private Integer id;
		
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
		
		public Messages(String text) {
			this.message = message;
			this.name = "test";
		}		
	}



