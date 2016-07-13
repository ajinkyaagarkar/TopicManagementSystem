package web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import datastructures.LoginForm;
import model.ApplicationModel;


@Controller
@RequestMapping("/login")
public class LoginController{

    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewRegistration(Map<String, Object> model) {
        LoginForm loginForm = new LoginForm();    
        model.put("loginForm", loginForm);
        return new ModelAndView("index");
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleRequest(@ModelAttribute("loginForm") LoginForm loginForm,HttpServletRequest request, 
    		HttpServletResponse response) throws ServletException, IOException {
    	String userEmailId=loginForm.getEmailAddress();
    	String userId=ApplicationModel.getInstance().getUserId(userEmailId);
    	if(userId==null){
    		logger.info("User Does Not Exist");
    		userId=ApplicationModel.getInstance().insertUser(userEmailId);
    	}
    	request.getSession().setAttribute("userId",userId);
    	return ("redirect:homepage.action");
    }
}