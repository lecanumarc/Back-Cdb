<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    
    <header class="navbar navbar-inverse navbar-fixed-top">
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<a class="navbar-brand" href="ListComputer"> Application - Computer Database </a>
			</div>
			<div class="pull-right">
				<a class="navbar-brand" href="logout" >Logout</a> 
				<div>
					<%-- <a id="en" href="EditComputer?lang=en"><spring:message code="lang.en" /></a> 
					| <a id="fr" href="EditComputer?lang=fr"><spring:message code="lang.fr" /></a> --%>
				</div>
			</div>
		</div>
	</header>
    
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${computer.computerId}"/>
                    </div>
                    <h1><spring:message code="label.editComputer" /></h1>

                    <form action="EditComputer" method="POST" onsubmit="return validateForm()">
                        <input type="hidden" value="${computer.computerId}" id="computerId" name = "computerId"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computer" /></label>
                                <input type="text" class="form-control" id="computerName" name = "computerName" value ="${computer.computerName}" placeholder="<spring:message code="label.computer" />">
                            	<p class="error">${errors['computerName']}</p>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introduced" /></label>
                                <input type="date" class="form-control" id="introduced" name = "introduced" value = "${computer.introduced}" placeholder="<spring:message code="label.introduced" />">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinued" /></label>
                                <input type="date" class="form-control" id="discontinued" name = "discontinued" value ="${computer.discontinued}" placeholder="<spring:message code="label.discontinued" />">                                
								<p class="error">${errors['discontinued']}</p>                            
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="${computer.companyDTO.companyId}" selected="selected"><c:out value=" ${computer.companyDTO.companyName}"/></option>
                                    <option value="0">--</option>
                                    	<c:forEach items="${listCompanies}" var="company" varStatus="status">
                                			<option value="${company.companyId}"><c:out value="${company.companyName}"/></option>
                                		</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                        	<input type ="submit" value=<spring:message code="button.edit" /> class="btn btn-primary" >
                            or
                            <a href="ListComputer" class="btn btn-default"><spring:message code="button.cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="js/validatorForm.js"></script>
</body>
</html>