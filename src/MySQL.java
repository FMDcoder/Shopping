import java.sql.*;

import javax.swing.JOptionPane;

public class MySQL {
	
	private String 
		username = "root",
		password = "031218MIa%%%",
		url = "jdbc:mysql://localhost:3306/handel";
	
	private Connection con;
	
	private void connect() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(
					null, 
					"Could not connect to server", 
					"Error", JOptionPane.OK_OPTION, 
					null
			);
			
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
