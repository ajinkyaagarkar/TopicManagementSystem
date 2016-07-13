<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<script type="text/javascript" src="resources/js/login.js"></script>
<link type="text/css" rel="stylesheet" href="resources/css/login.css"/>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Topic Management System</title>
</head>
<body>
<div class="login-page">
  <div class="form">
    
 	<form:form method="POST" action="login.action" commandName="loginForm" class="login-form">
      <form:input path="emailAddress" placeholder="emailaddress"/>
      <input type="submit" value="Login"/>
    </form:form>
    <!--Added comments-->
  </div>
</div>
</body>
</html>