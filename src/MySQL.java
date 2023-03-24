import java.sql.*;

import javax.swing.JOptionPane;

public class MySQL {
	
	
	// Database authentication
	private String 
		username = "username",
		password = "password",
		url = "jdbc:mysql://localhost:3306/handel";
	
	private Connection con;
	
	
	// Attempts to connect to database
	private void connect() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(
					null, 
					"Kunde inte koppla till servern: "+e.getErrorCode(), 
					"Error", JOptionPane.ERROR_MESSAGE, 
					null
			);
			
			e.printStackTrace();
		}
	}
	
	// disconnects from database
	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// return result from a SQL query
	public ResultSet sendResultQuery(String sql) {
		try {
			connect();
			
			Statement state = con.createStatement();
			ResultSet result = state.executeQuery(sql);
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// sends Query but return no result
	public void sendVoidQuery(String sql) {
		try {
			
			connect();
			
			Statement state = con.createStatement();
			state.execute(sql);
			state.close();
			
			disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
