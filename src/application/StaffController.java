package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;

public class StaffController {
	public ResultSet rss;
	@FXML
	
	
	
	public void initalize() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://v.je:3306/EDS", "student", "student");
			Statement stmt = con.createStatement();
			
			ResultSet rs=stmt.executeQuery("SELECT * FROM students");
			this.rss = rs;
		}catch(Exception e) {System.out.print(e);}
	}
	
	
	public void staffSearch() {
		System.out.println("Searching Staff");
	}
	
	public void staffSave(){
		System.out.println("Saving Staff Record");
	}
	
	
	
	
	
}
