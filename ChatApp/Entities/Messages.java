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
import javax.persistence.ManyToMany;

@Entity(name = "Messages")
public class Messages {
		
		@Id
		@GeneratedValue
		@Column(name = "messageid")
		private Integer messageid = 0;
		
		@Column(name = "sender")
		private String sender;
		
		@Column(name = "messagetext")
		private String messagetext;
		
		@Column(name = "offset")
		private Integer offset = 0;
		
		@Column(name = "messagedate")
		private LocalDateTime messagedate;
		

		public Integer getmessageid() {
			return messageid;
		}
		
		public String getsender() {
			return sender;
		}
		
		public String getmessagetext() {
			return messagetext;
		}
		
		public void setmessageid(Integer messageid) {
			this.messageid = messageid;
		}
		public Integer getoffset() {
			return offset;
		}
		
		public void setoffset(Integer offset) {
			this.offset = offset;
		}
		
		public void setsender(String sender) {
			this.sender = sender;
		}
		
		public void setmessagetext(String messagetext) {
			this.messagetext = messagetext;
		}
		
		public LocalDateTime getMessagedate() {
			return messagedate;
		}

		public void setMessagedate(LocalDateTime messagedate) {
			this.messagedate = messagedate;
		}
		
		 @ManyToMany(cascade = { CascadeType.ALL })
		    @JoinTable(
		        name = "Messages Conversations", 
		        joinColumns = { @JoinColumn(name = "Messageid") }, 
		        inverseJoinColumns = { @JoinColumn(name = "Conversationsid") }
		    )
		    Set<Conversations> conversations = new HashSet<>();
		   

		public Messages() {
			
		}
		
		public Messages(String text, Integer positie , LocalDateTime formattedDate) {
			this.messagetext = text;
			this.sender = "thomas";
			this.offset = positie;
			this.messagedate = formattedDate;
		}		
	}