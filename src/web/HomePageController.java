package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import datastructures.LoginForm;
import datastructures.Topic;

@Controller
public class HomePageController {
	
	 protected final Log logger = LogFactory.getLog(getClass());

	    @RequestMapping(value="/homepage",method = RequestMethod.GET)
	    public ModelAndView handleRequest(@ModelAttribute("loginForm")LoginForm loginForm,HttpServletRequest request, 
	    		HttpServletResponse response) throws ServletException, IOException {
	    	logger.info("Passing to  Home Page");
	    	return new ModelAndView("homepage");
	        
	    }
	    
	    @RequestMapping(value="/getTopics",method = RequestMethod.GET,headers="Accept=application/json")
	    public @ResponseBody List<Topic> getTopics(HttpServletRequest request,HttpServletResponse response) 
	    		throws ServletException, IOException {
	    	ArrayList<Topic> topicsList=new ArrayList<Topic>();
	    	Topic topic;
	    	
	    	try{  
				Class.forName("com.mysql.jdbc.Driver");  

				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","ajinkya","Test@123");  

				Statement stmt=con.createStatement();  

				ResultSet rs=stmt.executeQuery("select * from Topics");  

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

			}catch(Exception e){
				System.out.println(e);
			}  

	        return topicsList;
	        
	    }
}
