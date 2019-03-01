package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/*
 Column 1 - Name VARCHAR2
 Column 2 - ID INT
 Column 3 - Status VARCHAR2
 Column 4 - Reason VARCHAR2
 Column 5 - address VARCHAR2
 Column 6 - contact VARCHAR2
 Column 7 - qualifications VARCHAR2
 Column 8 - course VARCHAR2
 Column 9 - attendence VARCHAR2
 
 */
public class StudentController {
	public ResultSet rss;
	@FXML
	public Label stuName;
	public Label stuID;
	public Label stuStatus;
	public Label stuReason;
	public TextField stuSearch;
	public TextArea addressText;
	public TextArea contactText;
	public TextArea qualificationsText;
	public TextArea courseText;
	public TextArea attendenceText;
	
	
	public void initalize() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://v.je:3306/EDS", "student", "student");
			Statement stmt = con.createStatement();
			
			ResultSet rs=stmt.executeQuery("SELECT * FROM students");
			this.rss = rs;
		}catch(Exception e) {System.out.print(e);}
	}
	
	public void studentSearch() {
		int search = Integer.valueOf(stuSearch.getText().toString()); 
		try {
			while (rss.next()) {
				if (rss.getInt(2)== search) {
					stuName.setText("Name:"+rss.getString(1));
					stuID.setText("ID:"+rss.getInt(2));
					stuStatus.setText("Status:"+rss.getString(3));
					stuReason.setText("Reason:"+rss.getString(4));
					addressText.setText(""+rss.getString(5));
					contactText.setText(""+rss.getString(6));
					qualificationsText.setText(""+rss.getString(7));
					courseText.setText(""+rss.getString(8));
					attendenceText.setText(""+rss.getString(9));
				}
				else {
					stuName.setText("Name: unavailable");
					stuID.setText("ID: unavailable");
					stuStatus.setText("Status: unavailable");
					stuReason.setText("Reason: unavailable");
					addressText.setText("Address: Unavailable");
					contactText.setText("Contact Information: Unavailable");
					qualificationsText.setText("Qualifications: Unavailable");
					courseText.setText("Course Detils: Unavailable");
					attendenceText.setText("Attendence: Unavailable");
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stuName.setText("Name: unavailable");
			stuID.setText("ID: unavailable");
			stuStatus.setText("Status: unavailable");
			stuReason.setText("Reason: unavailable");
			addressText.setText("Address: Unavailable");
			contactText.setText("Contact Information: Unavailable");
			qualificationsText.setText("Qualifications: Unavailable");
			courseText.setText("Course Detils: Unavailable");
			attendenceText.setText("Attendence: Unavailable");
		}
		
		
		
	}
	
	public void studentSave() {
		System.out.println("Saving student");
	}
	
	
	
	
	
}
