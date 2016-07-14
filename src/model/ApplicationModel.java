package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import datastructures.Comment;
import datastructures.Topic;
import dbutil.DBConnection;

public class ApplicationModel {
	
	protected final Log logger = LogFactory.getLog(getClass());
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
			logger.error("Error Getting User Id",e);
		}
		return null;
		
	}
	
	public String insertUser(String emailAddress){
		
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps=con.prepareStatement("insert into users(email_id,upd_date) values(?,?)");
			if(emailAddress==null || emailAddress==""){
				ps.setString(1,null);
			}else{
				ps.setString(1,emailAddress);
			}
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
			logger.error("Error Adding User ",e);
		}
		return null;
		
	}
	
	public List<Topic> getTopics(){
		ArrayList<Topic> topicsList=new ArrayList<Topic>();
    	Topic topic;
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps=con.prepareStatement("select * from Topics");
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				topic=new Topic();
				topic.setTopicId(rs.getInt(1));
				topic.setTopicTitle(rs.getString(2));
				topic.setTopicCategory(rs.getString(3));
				topic.setTopicDescription(rs.getString(4));
				topicsList.add(topic);
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			logger.error("Error Getting Topics List",e);
		}
		return topicsList;
		
	}
	
	public Topic showTopic(int topicId){
		Topic topic=null;
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps =con.prepareStatement("select * from Topics where topic_id=?"); 
			ps.setInt(1,topicId);
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				topic=new Topic();
				topic.setTopicId(rs.getInt(1));
				topic.setTopicTitle(rs.getString(2));
				topic.setTopicCategory(rs.getString(3));
				topic.setTopicDescription(rs.getString(4));
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			logger.error("Error Fetching Topic",e);
		}
		return topic;
		
	}
	
	public List<Comment> getComments(int topicId){
		ArrayList<Comment> commentsList=new ArrayList<Comment>();
		Comment comment;
		try {
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps =con.prepareStatement("select co.comment_id,co.content,co.score,co.upd_date, us.email_id from comments co,users us "
					+ "where co.topic_id=? and co.user_id=us.user_id and co.score >= -10;"); 
			ps.setInt(1,topicId);
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				comment=new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setContent(rs.getString("content"));
				comment.setScore(rs.getInt("score"));
				comment.setAuthorEmail(rs.getString("email_id"));
				comment.setDateAdded(rs.getString("upd_date"));
				commentsList.add(comment);
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			logger.error("Error Fetching Topic",e);
		}
		return commentsList;
		
	}
	
	

}
