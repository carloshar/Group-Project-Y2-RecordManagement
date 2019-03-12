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
	
	public ResultSet find(String column, int pk) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM "+this.table+"WHERE "+column+" = "+pk);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	
	
	
	
}
