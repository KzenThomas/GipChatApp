create table conversations(
   conversationsid integer primary key,
   name varchar(255) not null
);

create table Messages(
   messageid integer primary key,
   loginid integer not null,
   messagetext varchar(255) not null,
   offset integer not null,
   messagedate DATETIME not null,
   foreign key(loginid) references login(loginid)
);

create table MessagesToConversations(
	conversationsid integer NOT NULL,
    messageid integer NOT NULL,
    FOREIGN KEY (conversationsid) REFERENCES conversations(conversationsid), 
    FOREIGN KEY (messageid) REFERENCES Messages(messageid)
);

create table login(
	loginid integer primary key,
	username varchar(255) not null,
	wachtwoord varchar(255) not null
);

create table ConversationsToLogin(
	conversationsid integer not null,
	loginid integer not null,
	FOREIGN KEY(conversationsid)REFERENCES conversations(conversationsid),
	foreign key(loginid) references login(loginid)
);



drop table Messages;
drop table conversations;
drop table MessagesToConversations;
drop table login;
drop table ConversationsToLogin;

