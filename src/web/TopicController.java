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
		List<Comment> commentsList=new ArrayList<Comment>();
		ApplicationModel.getInstance().saveComment(comment);
		commentsList=ApplicationModel.getInstance().getComments(comment.getTopicId());
		return commentsList;

	}
	
	@RequestMapping(value="/updateScore",method = RequestMethod.POST,produces=("application/json"),consumes=("application/json"))
	public @ResponseBody Comment updateScore(HttpServletRequest request,HttpServletResponse response,@RequestBody int score) 
			throws ServletException, IOException {
		
		int commentId=request.getParameter("commentId")==""?0:Integer.valueOf(request.getParameter("commentId"));
		ApplicationModel.getInstance().updateScore(score, commentId);
		Comment comment=ApplicationModel.getInstance().getComment(commentId);
		return comment;
	}



}
