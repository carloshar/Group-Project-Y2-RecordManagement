package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLTable {
	private String table;
	private Statement stmt;
	
	
	public SQLTable(String table) {
		try {
			DriverManager.setLoginTimeout(1);
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://v.je:3306/groupProjectY2", "student", "student");
			Statement stmt = con.createStatement();
			this.stmt = stmt;
			}catch(Exception e) {
				
			}
		
		this.table = table;
	}
	
	public void insert(String column1, String column2) {
		try {
			stmt.execute("INSERT INTO " + this.table + " VALUES (\"" + column1 + "\", \"" + column2 + "\")");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void insert(String column1, String column2, String column3) {
		try {
			stmt.execute("INSERT INTO " + this.table + " VALUES (\"" + column1 + "\", \"" + column2 + "\", \"" + column3 + "\")");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void insert(String column1, String column2, String column3, String column4) {
		try {
			stmt.execute("INSERT INTO " + this.table + " VALUES (\"" + column1 + "\", \"" + column2 + "\", \"" + column3 + "\", \"" + column4 + "\")");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void insert(String column1, String column2, String column3, String column4, String column5) {
		try {
			stmt.execute("INSERT INTO " + this.table + " VALUES (\"" + column1 + "\", \"" + column2 + "\", \"" + column3 + "\", \"" + column4 + "\", \"" + column5 + "\")");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void insertStudent(String column1, String column2, String column3, String column4, String column5, String column6, String column7, String column8, String column9, String column10, String column11) {
		try {
			stmt.execute("INSERT INTO contact_address (house, street, city, county, postcode) VALUES (\"" + column4 + "\", \"" + column5 + "\", \"" + column6 + "\", \"" + column7 + "\", \"" + column8 + "\")");
			ResultSet rs = stmt.executeQuery("SELECT address_id FROM contact_address WHERE house = \"" + column4 + "\" && street = \"" + column5 + "\" && city = \"" + column6 + "\" && county = \"" + column7 + "\" && postcode = \"" + column8 + "\"");
			int address_id = 0;
			while (rs.next()) {
				address_id = rs.getInt(1);
			}
			stmt.execute("INSERT INTO student_records (firstname, record_status, middle_name, surname, address_id, contact_phone, contact_email, course_code) VALUES (\"" + column1 + "\", \"PROVISIONAL\", \"" + column2 + "\", \"" + column3 + "\", " + address_id + ", \"" + column9 + "\", \"" + column10 + "\", \"" + column11 + "\")");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	public void updateStudent(String id, String column1, String column2, String column3, String column4, String column5, String column6, String column7, String column8, String column9, String column10, String column11) {
		try {
			ResultSet addressIDSet = stmt.executeQuery("SELECT address_id FROM student_records WHERE student_id = " + id);
			String addressID = "";
			while (addressIDSet.next()) {
				addressID = addressIDSet.getString(1);
			}
			stmt.execute("UPDATE contact_address SET house = \"" + column4 + "\" WHERE address_id = " + addressID);
			stmt.execute("UPDATE contact_address SET street = \"" + column5 + "\" WHERE address_id = " + addressID);
			stmt.execute("UPDATE contact_address SET city = \"" + column6 + "\" WHERE address_id = " + addressID);
			stmt.execute("UPDATE contact_address SET county = \"" + column7 + "\" WHERE address_id = " + addressID);
			stmt.execute("UPDATE contact_address SET postcode = \"" + column8 + "\" WHERE address_id = " + addressID);
	
			stmt.execute("UPDATE student_records SET firstname = \"" + column1 + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET middle_name = \"" + column2 + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET surname = \"" + column3 + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET contact_phone = \"" + column9 + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET contact_email = \"" + column10 + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET course_code = \"" + column11 + "\" WHERE student_id = " + id);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	public void updateStudentStatus(String id, String record, String dormancy) {
		try {
			stmt.execute("UPDATE student_records SET record_status = \"" + record + "\" WHERE student_id = " + id);
			stmt.execute("UPDATE student_records SET dormancy_reason = \"" + dormancy + "\" WHERE student_id = " + id);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	public ResultSet findAll() {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM "+this.table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet findAllWhere(String column, String pk) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM "+this.table+" WHERE "+column+" = \""+pk+"\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet find(String column) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT " + column + " FROM " + this.table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet findAllWhere(String column, int pk) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM " + this.table + " WHERE " + column + " = " + pk);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	
	
	
	
}
