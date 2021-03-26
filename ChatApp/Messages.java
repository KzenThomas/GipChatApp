package ChatApp;

import java.time.LocalDateTime;

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
		
		@Column(name = "sender")
		private String sender;
		
		@Column(name = "message")
		private String message;
		
		@Column(name = "messagedate")
		private LocalDateTime messagedate;
		

		public Integer getid() {
			return id;
		}
		
		public String getsender() {
			return sender;
		}
		
		
		
		public String getmessage() {
			return message;
		}
		
		public void setid(Integer id) {
			this.id = id;
		}
		
		public void setsender(String sender) {
			this.sender = sender;
		}
		
		public void setmessage(String message) {
			this.message = message;
		}
		
		
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public LocalDateTime getMessagedate() {
			return messagedate;
		}

		public void setMessagedate(LocalDateTime messagedate) {
			this.messagedate = messagedate;
		}

		public Messages() {
			
		}
		
		public Messages(String text, LocalDateTime formattedDate) {
			this.message = text;
			this.sender = "thomas";
			this.messagedate = formattedDate;
		}		
	}