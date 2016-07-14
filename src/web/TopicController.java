package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import datastructures.Comment;
import datastructures.Topic;
import model.ApplicationModel;


@Controller
public class TopicController{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value="/showTopic",method = RequestMethod.GET)
	public ModelAndView showTopic(ModelMap model,HttpServletRequest request) {
		
		String topicId=request.getParameter("topicId");
		Topic topic=ApplicationModel.getInstance().showTopic(topicId==""?0:Integer.valueOf(topicId));
		if(topic==null){
			model.addAttribute("errorMsg","No such topic to Show");
			return new ModelAndView("homepage");
		}
		model.addAttribute("topic",topic);
		model.addAttribute("userId",request.getSession().getAttribute("userId"));
		return new ModelAndView("topic");
	}


	@RequestMapping(value="/getComments",method = RequestMethod.GET,produces=("application/json"))
	public @ResponseBody List<Comment> getComments(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		List<Comment> commentsList=null;
		String topicId=request.getParameter("topicId");
		commentsList=ApplicationModel.getInstance().getComments(topicId==""?0:Integer.valueOf(topicId));
		return commentsList;

	}

	@RequestMapping(value="/saveComment",method = RequestMethod.POST,produces=("application/json"),consumes=("application/json"))
	public @ResponseBody List<Comment> saveComment(HttpServletRequest request,HttpServletResponse response,@RequestBody Comment comment) 
			throws ServletException, IOException {
		ArrayList<Comment> commentsList=new ArrayList<Comment>();
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  

			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","ajinkya","Test@123");  

			Statement stmt=con.createStatement();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			stmt.executeUpdate("insert into comments(content,user_id,topic_id,upd_date) values('"+comment.getContent()+"','"+comment.getUserId()+"','"+comment.getTopicId()+"','"+dateFormat.format(date)+"')");  
			
			ResultSet rs=stmt.executeQuery("select co.comment_id,co.content,co.score,co.upd_date, us.email_id from comments co,users us where co.topic_id="+comment.getTopicId()+
					" and co.user_id=us.user_id and co.score >= -10;");  

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

		}catch(Exception e){
			System.out.println(e);
		}  

		return commentsList;

	}
	
	@RequestMapping(value="/updateScore",method = RequestMethod.POST,produces=("application/json"),consumes=("application/json"))
	public @ResponseBody Comment updateScore(HttpServletRequest request,HttpServletResponse response,@RequestBody int score) 
			throws ServletException, IOException {
		
		Comment comment=null;
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  

			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","ajinkya","Test@123");  

			Statement stmt=con.createStatement();
			int commentId=Integer.valueOf(request.getParameter("commentId"));
			stmt.executeUpdate("update comments set score=score+"+score+" where comment_id= "+commentId);
			
			ResultSet rs=stmt.executeQuery("select co.comment_id,co.content,co.score,co.upd_date, us.email_id from comments co,users us where co.comment_id="+commentId+
					" and co.user_id=us.user_id and co.score >= -10;");  
			
			while(rs.next()){
				comment=new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setContent(rs.getString("content"));
				comment.setScore(rs.getInt("score"));
				comment.setAuthorEmail(rs.getString("email_id"));
				comment.setDateAdded(rs.getString("upd_date"));
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
			}


			con.close();  

		}catch(Exception e){
			System.out.println(e);
		}  

		return comment;

	}



}
