package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ConnectionObject {
    private static final String URL = "jdbc:postgresql://localhost:5432/NetBankingApp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "tiger";

    // Get a connection to the database
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver"); // Load driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
public class DatabaseOperations {
	public boolean checkCid(int cid) {
		String query = "SELECT COUNT(*) FROM users WHERE customer_id = ?";
		
		try (Connection conn = ConnectionObject.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	            
	            ps.setInt(1, cid);
	            ResultSet rs = ps.executeQuery();
	            
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // If count > 0, CID exists
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false; // CID does not exist
		
		
	}
	
	public boolean registerUser(int cid, String name,String password) {
		
		boolean success = false;
		String query = "INSERT INTO users (customer_id,customer_name,password) VALUES(?,?,?)";
		
		try(Connection con = ConnectionObject.getConnection();
			PreparedStatement ps = con.prepareStatement(query)
		){
			ps.setInt(1, cid);
			ps.setString(2, name);
			ps.setString(3, password);
			
			int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
            
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return success;
	}
	
	public String validateUser(int cid, String password) {
		String name = null;
        try (Connection con = ConnectionObject.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE customer_id = ? AND password = ?")) {

            ps.setInt(1, cid);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name=rs.getString("customer_name");	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
	

}
