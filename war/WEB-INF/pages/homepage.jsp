<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type='text/javascript' src='resources/js/jquery.js'></script>
<script type='text/javascript' src='resources/js/jquery-ui-1.9.2.custom.min.js'></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.8/angular.min.js"></script>
<script type='text/javascript' src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.8/angular-route.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.8/angular-resource.min.js"></script>
<script type='text/javascript' src="resources/angular/homepagecontroller.js"></script>
<script type="text/javascript">
function fun()
{
	var errorMsg='${errorMsg}';
	if(errorMsg!='')
		alert(errorMsg);
}
</script>
<head>

    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Topic Management System</title>

    <!-- Bootstrap Core CSS -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="resources/css/thumbnail-gallery.css" rel="stylesheet">
</head>
<body onLoad="fun()" ng-controller="homepageCtrl" ng-init="getTopics();" ng-app="homepageapp"> 

    <!-- Navigation -->
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
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <div class="col-lg-12">
                <h1 class="page-header">Available Topics</h1>
            </div>

			<table>
				<tr ng-repeat="topic in topicslist">
					<td>
						<a class="thumbnail" href="showTopic.action?topicId={{topic.topicId}}">
                    	{{topic.topicTitle}}
                		</a>
                	</td>
				</tr>
			</table>
	   </div>

        <hr>

        <!-- Footer -->
  

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="resources/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="resources/js/bootstrap.min.js"></script>

</body>

</html>