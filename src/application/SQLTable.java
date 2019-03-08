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
	
	public ResultSet findAll() {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM "+this.table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet find(String column,String pk) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM "+this.table+"WHERE "+column+" = "+pk);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
