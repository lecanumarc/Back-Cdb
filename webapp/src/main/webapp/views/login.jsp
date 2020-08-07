<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<a class="navbar-brand" href="login"> Application -	Computer Database </a>
			</div>
			<div class="pull-right">
				 <a id="en" href="login?lang=en"><spring:message code="lang.en" /></a> 
				| <a id="fr" href="login?lang=fr"><spring:message code="lang.fr" /></a>	
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="pull-right">
					<a class="btn btn-default" id="registerUser" href="RegisterUser"><spring:message code="label.register" /></a> 
				</div>
				<div class="col-xs-6 col-xs-offset-2 box">
					<h1>
						<spring:message code="label.login" />
					</h1>
					
					
					<form name= "login" action="login" method="POST" >
					
						<fieldset>
							<div class="form-group">
								<label for="username"><spring:message code="label.username" /></label>
								<input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="label.username" />">
							</div>
							<div class="form-group">
								<label for="password"><spring:message code="label.password" /></label> 
								<input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="label.password" />">
							</div>

						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value=<spring:message code="label.login" /> class="btn btn-primary"> 
						</div>
	
					<c:if test="${param.error=='true'}">
						<p class="error"><spring:message code="label.userInvalid" /></p>
       				 </c:if>
						<c:if test="${param.logout =='true'}">
            				<p class="success"><spring:message code="label.logout" /></p>
       				</c:if>
					</form>
				</div>
			</div>
		</div>
	</section>
<body>
</html>