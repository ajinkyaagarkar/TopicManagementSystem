package web;

import java.io.IOException;
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
import model.ApplicationModel;

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
	    	List<Topic> topicsList=new ArrayList<Topic>();
	    	topicsList=ApplicationModel.getInstance().getTopics();
	    	return topicsList;
	        
	    }
}
