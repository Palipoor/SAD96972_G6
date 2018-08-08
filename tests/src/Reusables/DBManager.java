package Reusables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager{
	Connection conn;
	public Connection connect() {
		String url = "jdbc:postgresql://localhost/sadproject";
		String user = "sad";
		String password = "sad";
		try {
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return this.conn;
	}

	public void addInitialUsers(){
		String query = "";
		try {
			Statement stmt = this.conn.createStatement();
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	public void addInitialTransactions(){
	}
	public void setUsersToDefault(){
	}
	public void deleteTestUsers(){
	}
	public void deleteTestTransactions(){
	}
	public void deleteUser(String username){
	}
	public void setUserToDefault(String username){
	}

	public static void main(String[] args) {
		DBManager manager = new DBManager();
		manager.connect();
	}

}