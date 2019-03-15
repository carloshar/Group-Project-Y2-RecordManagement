package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StaffController {
	
	@FXML
	public TextField searchField;
	@FXML
	public Label staffName;
	public Label staffID; // Found through staffConnection
	public Label staffStatus; // Need status in database
	public Label staffReason; // Need reason in database
	@FXML
	public TextArea staffAddress; // Links to contactConnection
	public TextArea staffContact; // Contact_phone and Contact_Email through staffConnection
	public TextArea staffModules; // Staff_modules list all modules
	
	
	SQLTable staffConnection; // Contact info, Name, Status, Reason
	SQLTable contactConnection; // Address information
	SQLTable moduleConnection; // Module info for course
	
	
	String[] list = new String[10];
	
	public void initalize() {
		try {
			this.staffConnection = new SQLTable("staff");
			this.contactConnection = new SQLTable("contact_address");
			this.moduleConnection = new SQLTable("staff_modules");
		}catch(Exception e) {System.out.print(e);}
	}
	
	
	public void staffSearch() {
		try {
			if(!searchField.getText().equals("")) {
				int search = Integer.valueOf(searchField.getText());
				try {
					ResultSet staffResultSet = staffConnection.findAll();
					while(staffResultSet.next()) {
						if(staffResultSet.getInt(1)==search) {
							staffName.setText("FirstName: "+ staffResultSet.getString(2)+" Middle Name(s): "+ staffResultSet.getString(3)+" Surname: "+staffResultSet.getString(4));
							staffID.setText("Staff ID:"+staffResultSet.getInt(1));
							ResultSet contactResultSet = contactConnection.findAllWhere("address_id",staffResultSet.getInt(5));
							while(contactResultSet.next()) {
								staffAddress.setText("House: " + contactResultSet.getString(2) +"\n"+
													 "Street: "+ contactResultSet.getString(3)+"\n"+
													 "City: " + contactResultSet.getString(4)+ "\n"+
													 "County: " +contactResultSet.getString(5)+"\n"+
													 "postcode: "+contactResultSet.getString(6));
							}
							staffContact.setText("Phone: " +staffResultSet.getString(6) +" Email: "+ staffResultSet.getString(7));
							ResultSet courseResultSet = contactConnection.findAllWhere("staff_id", staffResultSet.getInt(1));
							while(courseResultSet.next()) {
								int i = 0;
								this.list[i] = courseResultSet.getString(2);
								i ++;
							}
							staffModules.setText("Modules: " + list[0] +"\n"
												+list[1]+"\n"
												+list[2]+"\n"
												+list[3]+"\n"
												+list[4]+"\n"
												+list[5]+"\n"
												+list[6]+"\n"
												+list[7]+"\n"
												+list[8]+"\n"
												+list[9]+"\n"
												);
							
						}else {
							staffName.setText("Name: Unavaliable");
							staffID.setText("Staff ID:" + searchField.getText());
							staffStatus.setText("Status: Unavaliable");
							staffReason.setText("Reason: Unavaliable");
							staffAddress.setText("Address: Unavaliable");
							staffContact.setText("Contact: Unavaliable");
							staffModules.setText("Modules: Unavaliable");
						}
					}
				}catch(Exception e) {
					staffName.setText("Name: Unavaliable");
					staffID.setText("Staff ID: "+ searchField.getText());
					staffStatus.setText("Status: Unavaliable");
					staffReason.setText("Reason: Unavaliable");
					staffAddress.setText("Address: Unavaliable");
					staffContact.setText("Contact: Unavaliable");
					staffModules.setText("Modules: Unavaliable");
				}
			}
			
		}catch(Exception e){
			staffName.setText("Name: Unavaliable");
			staffID.setText("Staff ID:"+ searchField.getText());
			staffStatus.setText("Status: Unavaliable");
			staffReason.setText("Reason: Unavaliable");
			staffAddress.setText("Address: Unavaliable");
			staffContact.setText("Contact: Unavaliable");
			staffModules.setText("Modules: Unavaliable");
		}
		//System.out.println("Searching Staff");
	}
	
	public void staffSave(){
		
		System.out.println("Saving Staff Record");
	}
	
	
	
	
	
}
