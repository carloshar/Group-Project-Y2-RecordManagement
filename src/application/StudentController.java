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
	public ResultSet rssCon;
	public ResultSet rssCour;
	public ResultSet rssQua;
	public ResultSet rssAtt;
	public Statement stmt;
	
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
			Connection con=DriverManager.getConnection("jdbc:mysql://v.je:3306/groupProjectY2", "student", "student");
			Statement stmt = con.createStatement();
			this.stmt = stmt;
			ResultSet rs=stmt.executeQuery("SELECT * FROM student_records");
			
			this.rss = rs;
		}catch(Exception e) {System.out.print(e);}
	}
	
	public void studentSearch() {
		int search = Integer.valueOf(stuSearch.getText().toString()); 
		try {
			while (rss.next()) {
				if (rss.getInt(2)== search) {
					
					stuName.setText("Name:"+rss.getString(4)+rss.getString(5)+rss.getString(6));
					stuID.setText("ID:"+rss.getInt(1));
					stuStatus.setText("Status:"+rss.getString(2));
					stuReason.setText("Reason:"+rss.getString(3));
					ResultSet rs2 = stmt.executeQuery("SELECT * FROM contact_address WHERE address_id ="+ rss.getString(7) +"LIMIT 1");
					this.rssCon = rs2;
					while (rssCon.next()) {
						addressText.setText("House:"+rssCon.getString(2)+" Street:"+rssCon.getString(3)+" City:"+rssCon.getString(4)+" County:"+rssCon.getString(5)+" Postcode:"+rssCon.getString(6));
					}
					contactText.setText("Phone:"+rss.getString(8)+"Email:"+rss.getString(9));
					ResultSet rs3 = stmt.executeQuery("SELECT * FROM courses WHERE course_code ="+ rss.getString(10));
					this.rssCour = rs3;
					while(rssQua.next()) {
						qualificationsText.setText("Institution"+rssQua.getString(3)+"Subject"+rssQua.getString(4)+"Educational_Facility"+rssQua.getString(5)+"Grade"+rssQua.getString(6));
					}
					ResultSet rs4 = stmt.executeQuery("SELECT * FROM qualifications WHERE student_id ="+ rss.getInt(1));
					this.rssQua = rs4;
					while(rssCour.next()) {
						courseText.setText("Course Code:"+rssCour.getString(1)+"Course Title:"+rssCour.getString(2));
					}
					ResultSet rs5 = stmt.executeQuery("SELECT * FROM attendence WHERE student_id ="+ rss.getInt(1));
					this.rssAtt = rs5;
					while(rssAtt.next()) {
						attendenceText.setText("Currently Unavaliable");
					}
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
	
	public void studentAdd() {
		System.out.println("Adding student");
	}
	
}
