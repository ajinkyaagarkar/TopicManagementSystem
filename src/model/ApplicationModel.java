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
	
	public void insertUser(String emailAddress){
		
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
			
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			logger.error("Error Adding User ",e);
		}
		
		
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
				topic.setTopicId(rs.getInt("topic_id"));
				topic.setTopicTitle(rs.getString("topic_name"));
				topic.setTopicCategory(rs.getString("topic_cat"));
				topic.setTopicDescription(rs.getString("topic_desc"));
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
			PreparedStatement ps =con.prepareStatement("select t.*,u.email_id from Topics t,users u where t.topic_id=? and t.user_id=u.user_id"); 
			ps.setInt(1,topicId);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				topic=new Topic();
				topic.setTopicId(rs.getInt("topic_id"));
				topic.setTopicTitle(rs.getString("topic_name"));
				topic.setTopicCategory(rs.getString("topic_cat"));
				topic.setTopicDescription(rs.getString("topic_desc"));
				topic.setUserEmail(rs.getString("email_id"));
				topic.setAddDate(formatter.format(rs.getTimestamp("upd_date")));
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
					+ "where co.topic_id=? and co.user_id=us.user_id and co.score >= -10"); 
			ps.setInt(1,topicId);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ResultSet rs=ps.executeQuery();  
			while(rs.next()){
				comment=new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setContent(rs.getString("content"));
				comment.setScore(rs.getInt("score"));
				comment.setAuthorEmail(rs.getString("email_id"));
				comment.setDateAdded(formatter.format(rs.getTimestamp("upd_date")));
				commentsList.add(comment);
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			} 
			con.close();  
		} catch (ClassNotFoundException | IOException | SQLException e) {
			logger.error("Error Fetching Topic",e);
		}
		return commentsList;
		
	}
	
	public boolean saveComment(Comment comment){
		
		try{  
			Connection con = DBConnection.getDBConnection();
			PreparedStatement ps =con.prepareStatement("insert into comments(content,user_id,topic_id,upd_date) values(?,?,?,?)");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			ps.setString(1,comment.getContent());
			ps.setString(2,comment.getUserId());
			ps.setInt(3,comment.getTopicId());
			ps.setString(4,dateFormat.format(date));
			ps.executeUpdate();
			con.close();  

		}catch(Exception e){
			logger.error("Error Fetching Topic",e);
			return false;
		}  
		return true;
	}
	
	public Comment updateScore(int score,int commentId){
		Comment comment=null;
		
		try{  
			Connection con = DBConnection.getDBConnection(); 
			PreparedStatement ps =con.prepareStatement("update comments set score=score + (?) where comment_id= ?");
			ps.setInt(1,score);
			ps.setInt(2,commentId);
			ps.executeUpdate();
			con.close();  

		}catch(Exception e){
			logger.error("Error Updating Score",e);
		}  

		return comment;
	}
	
	public Comment getComment(int commentId){
		Comment comment=new Comment();
		try{
			Connection con = DBConnection.getDBConnection(); 
			PreparedStatement ps =con.prepareStatement("select co.comment_id,co.content,co.score,co.upd_date, us.email_id from comments co,users us where co.comment_id = ? "+
					" and co.user_id=us.user_id and co.score >= -10;");
			ps.setInt(1,commentId);
			ResultSet rs=ps.executeQuery();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while(rs.next()){
				comment=new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setContent(rs.getString("content"));
				comment.setScore(rs.getInt("score"));
				comment.setAuthorEmail(rs.getString("email_id"));
				comment.setDateAdded(formatter.format(rs.getTimestamp("upd_date")));
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			}
			con.close();
			
		}catch(Exception e){
			logger.error("Error fetching Comment",e);
		}
		return comment;
	}
	
	

}
