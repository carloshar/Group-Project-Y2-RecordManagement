package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;
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
	
	@FXML
	public Label stuName;
	public Label stuID;
	public Label stuStatus;
	public Label stuReason;
	public TextField stuSearch;
	public TextField houseText;
	public TextField streetText;
	public TextField cityText;
	public TextField countyText;
	public TextField postcodeText;
	public TextField contactPhoneText;
	public TextField contactEmailText;
	public TextField courseText;
	public TextArea qualificationsText;
	public TextArea attendenceText;
	
	PopupInputs dialog = new PopupInputs();
	SQLTable courseConnection = new SQLTable("courses");
	SQLTable contactConnection = new SQLTable("contact_address");
	SQLTable studentConnection = new SQLTable("student_records");
	SQLTable qualificationConnection = new SQLTable("qualifications");
	SQLTable attendanceConnection = new SQLTable("total_attendance");

	public void initalize() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://v.je:3306/groupProjectY2", "student", "student");
			
		}catch(Exception e) {System.out.print(e);}
	}
	
	public void studentSearch() {
		int search = Integer.valueOf(stuSearch.getText()); 
		try { 
			ResultSet studentResultSet = studentConnection.findAll();
			while (studentResultSet.next()) {
				if (studentResultSet.getInt(1) == search) {
					
					stuName.setText("Name: " + studentResultSet.getString(4) + " " + studentResultSet.getString(5) + " " + studentResultSet.getString(6));
					stuID.setText("ID:"+studentResultSet.getInt(1));
					stuStatus.setText("Status:"+studentResultSet.getString(2));
					stuReason.setText("Reason:"+studentResultSet.getString(3));
					ResultSet contactResultSet = contactConnection.findAllWhere("address_id", studentResultSet.getString(7));
					while (contactResultSet.next()) {
						houseText.setText(contactResultSet.getString(2));
						streetText.setText(contactResultSet.getString(3));
						cityText.setText(contactResultSet.getString(4));
						countyText.setText(contactResultSet.getString(5));
						postcodeText.setText(contactResultSet.getString(6));
					}
					contactPhoneText.setText(studentResultSet.getString(8));
					contactEmailText.setText(studentResultSet.getString(9));
					ResultSet courseResultSet = courseConnection.findAllWhere("course_code", studentResultSet.getString(10));
					ResultSet qualificationResultSet = qualificationConnection.findAllWhere("student_id", studentResultSet.getInt(1));
					while(qualificationResultSet.next()) {
						qualificationsText.setText("Institution"+qualificationResultSet.getString(3)+"Subject"+qualificationResultSet.getString(4)+"Educational_Facility"+qualificationResultSet.getString(5)+"Grade"+qualificationResultSet.getString(6));
					}
					while(courseResultSet.next()) {
						courseText.setText(courseResultSet.getString(1) + " - " + courseResultSet.getString(2));
					}
					ResultSet attendanceResultSet = attendanceConnection.findAllWhere("student_id", studentResultSet.getInt(1));
					while(attendanceResultSet.next()) {
						attendenceText.setText("Currently Unavaliable");
					}
				}
				else {
					stuName.setText("Name: unavailable");
					stuID.setText("ID: unavailable");
					stuStatus.setText("Status: unavailable");
					stuReason.setText("Reason: unavailable");
					houseText.setText("Address: Unavailable");
					streetText.setText("Address: Unavailable");
					cityText.setText("Address: Unavailable");
					countyText.setText("Address: Unavailable");
					postcodeText.setText("Address: Unavailable");
					contactEmailText.setText("Contact Information: Unavailable");
					contactPhoneText.setText("Contact Information: Unavailable");
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
			houseText.setText("Address: Unavailable");
			streetText.setText("Address: Unavailable");
			cityText.setText("Address: Unavailable");
			countyText.setText("Address: Unavailable");
			postcodeText.setText("Address: Unavailable");
			contactEmailText.setText("Contact Information: Unavailable");
			contactPhoneText.setText("Contact Information: Unavailable");
			qualificationsText.setText("Qualifications: Unavailable");
			courseText.setText("Course Detils: Unavailable");
			attendenceText.setText("Attendence: Unavailable");
		}
	}
	
	public void studentSave() {
		System.out.println("Saving student");
	}
	
	public void studentAdd() {
		String[] textFieldNames = {"Firstname", "Middle Name", "Surname", "House", "Street", "City", "County", "Postcode", "Contact Phone", "Email"};
		
		ResultSet courseResultSet = courseConnection.findAll();
		
		ArrayList<String> courseArrayList = new ArrayList<String>();
		
		try {
			while (courseResultSet.next()) {
				courseArrayList.add(courseResultSet.getString(1) + "-" + courseResultSet.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		
		String[] courseList = new String[courseArrayList.size()];
		
		for (int i = 0; i<courseArrayList.size(); i++) {
			courseList[i] = courseArrayList.get(i);
		}
		
		Pair<String, String[]> coursePair = new Pair<String, String[]>("Course", courseList);
		ArrayList<String> studentDetail = dialog.inputDialog("Add a Student", "Add a Student", "Add", textFieldNames, null, coursePair);
		
		if (studentDetail.size() == 11) {
			studentConnection.insertStudent(studentDetail.get(0), studentDetail.get(1), studentDetail.get(2), studentDetail.get(3), studentDetail.get(4), studentDetail.get(5), studentDetail.get(6), studentDetail.get(7), studentDetail.get(8), studentDetail.get(9), studentDetail.get(10).split("-")[0]);
		}
	}
}
