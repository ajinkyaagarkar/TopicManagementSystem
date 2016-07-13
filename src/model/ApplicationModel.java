package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dbutil.DBConnection;

public class ApplicationModel {
	
	private static ApplicationModel instance=new ApplicationModel();
	
	
	public static ApplicationModel getInstance(){
		return instance;
	}
	
	private ApplicationModel(){
		
	}
	
	public String getUserId(String emailAddress){
		
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps =con.prepareStatement("select user_id from users where email_id=?"); 
			ps.setString(1,emailAddress);
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				return rs.getString("user_id");
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String insertUser(String emailAddress){
		
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps=con.prepareStatement("insert into users(email_id,upd_date) values(?,?)");
			ps.setString(1,emailAddress);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			ps.setString(2,dateFormat.format(date));
			ps.executeUpdate();  
			ps =con.prepareStatement("select user_id from users where email_id=?"); 
			ps.setString(1,emailAddress);
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				return rs.getString("user_id");
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
