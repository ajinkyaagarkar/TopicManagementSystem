<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en-US">
<script type='text/javascript' src='resources/js/jquery.js'></script>
<script type='text/javascript' src='resources/js/jquery-ui-1.9.2.custom.min.js'></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>	
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-animate.min.js"></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-aria.min.js"></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angular_material/1.0.5/angular-material.min.js"></script>
<script type='text/javascript' src="https://rawgit.com/ismarslomic/angular-read-more/master/dist/readmore.min.js"></script>
<script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/angular-sanitize/1.5.0/angular-sanitize.min.js"></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-route.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-resource.min.js"></script>
<script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/ngInfiniteScroll/1.2.1/ng-infinite-scroll.js"></script>
<script type='text/javascript' src="resources/angular/topiccontroller.js"></script>
<link rel="stylesheet" href="resources/css/main.css" type="text/css" />
<link rel="stylesheet" href="resources/css/topic.css" type="text/css" />
<link rel="stylesheet" href="https://material.angularjs.org/1.0.6/angular-material.css" type="text/css" />
<link rel="stylesheet" href="https://material.angularjs.org/1.0.6/docs.css" type="text/css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic" type="text/css" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/thumbnail-gallery.css" rel="stylesheet">
<script src="resources/js/bootstrap.min.js"></script>

<head>
	<title>Comment On Your Topics</title>
	<meta charset="utf-8" />
</head>


<body  ng-controller="topicCtrl" ng-app="topicapp" ng-init="getComments();" id="index" class="home" class="ng-cloak">
	<input type="hidden" value="${topic.topicId}" id="topicId">
	<input type="hidden" value="${userId}" id="userId">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Topic Management System</a>
            </div>
       </div>
   	</nav>
	
	
	<section id="content" class="body">
	  
	  <article class="hentry">	
			<header>
				<h2 class="entry-title"><a href="javascript:void(0)" rel="bookmark" title="${topic.topicTitle}">
				<c:out value="${topic.topicTitle}"/></a></h2>
			</header>
			<br>
			<div class="post-info">
				<div class="published" title="2012-02-10T14:07:00-07:00">
					<c:out value="${topic.addDate}"/>
				</div>

				<div class="vcard author">
					By <a href="javascript:void(0)"><c:out value="${topic.userEmail}"/></a>
				</div>
			</div>
			<br>
			<div class="entry-content">
				<c:out value="${topic.topicDescription}"/>
			</div>
		</article>
			
	</section>
	
	<section id="comments" class="body">
	  
	  <header>
			<h2>Comments</h2>
		</header>
	<div infinite-scroll='loadMore()' infinite-scroll-distance='2'>
		<ol>
			<li ng-repeat="comment in comments | orderBy:'score':true | limitTo:limit" class="hentry">
				
						<div class="post-info">
	    					<div title="{{comment.dateAdded}}">
	    						{{comment.dateAdded}}
	    					</div>
							
	    					<address class="vcard author">
	    						By <a class="url fn" href="javascript:void(0)">{{comment.authorEmail}}</a>
	    					</address>
	    				</div>
	
	    				<div class="entry-content">
	    					<div hm-read-more hm-text="{{comment.content}}" hm-limit="{{commentLength}}" hm-more-text="{{moreText}}" 
	    						hm-less-text="{{lessText}}" hm-dots-class="{{dotsClass}}" hm-link-class="{{linkClass}}">
      						</div>
	    					
	    					
	    				</div>
	    				<table>
	    					<tr>
	    						<td><a href="javascript:void(0)" ng-click="updateScore(true,comment.commentId)">LIKE</a></td>
	    						<td><a href="javascript:void(0)" ng-click="updateScore(false,comment.commentId)">DISLIKE</a></td>
	    						<td><a>SCORE {{comment.score}}</a></td>
	    						
	    					</tr>
	    				</table>
					
			</li>
		</ol>
	</div>
    
		
	<div id="respond">

      <h3>Leave a Comment</h3>

      
        <label for="comment" class="required">Your message</label>
        <textarea name="comment" id="comment" rows="10" tabindex="4"  required="required"></textarea>

        <input name="submit" type="submit" value="Submit comment" ng-click="submitComment()"/>
        
      
      
    </div>
			
	</section>
</body>
</html>