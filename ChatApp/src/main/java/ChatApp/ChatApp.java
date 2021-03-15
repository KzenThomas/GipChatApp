package ChatApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatApp {
	Connection dbconnection;
	
	public ChatApp() {
		try {
			dbconnection = DriverManager.getConnection("jdbc:sqlite:U:\\SQL\\sqlite-tools-win32-x86-3330000\\ChatApp.db", "root", null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
