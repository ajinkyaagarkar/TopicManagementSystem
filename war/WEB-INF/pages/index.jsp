<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>

<script type='text/javascript' src='resources/js/jquery.js'></script>
<script type='text/javascript' src='resources/js/jquery-ui-1.9.2.custom.min.js'></script>
<script type="text/javascript" src="resources/js/login.js"></script>
<link type="text/css" rel="stylesheet" href="resources/css/login.css"/>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/thumbnail-gallery.css" rel="stylesheet">
<script src="resources/js/bootstrap.min.js"></script>

<script type="text/javascript">
function fun()
{
	var errorMsg='${errorMsg}';
	if(errorMsg!='')
		alert(errorMsg);
}

function check(){
	if(document.getElementById('emailId').value==""){
		alert('Email Id Cannot be Empty. Please Enter Email Id');
		return false;
	}else{
		return true;
	}
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Topic Management System</title>
</head>
<body onload="fun()">
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
<div class="login-page">
  <div class="form">
    
 	<form:form method="POST" action="login.action" onsubmit="return check();" commandName="loginForm" class="login-form">
      <form:input path="emailAddress" placeholder="john@tms.com" id="emailId"/>
      <input type="submit" value="Get In"/>
    </form:form>
 </div>
</div>
</body>
</html>