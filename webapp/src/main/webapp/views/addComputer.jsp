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
				<%--<a id="en" href="AddComputer?lang=en"><spring:message code="lang.en" /></a> 
					| <a id="fr" href="AddComputer?lang=fr"><spring:message code="lang.fr" /></a>--%>
				</div>
			</div>
		</div>
	</header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="label.addComputer" /></h1>
                    <form action="AddComputer" method="POST" onsubmit="return validateForm()">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computer" /></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" value="<c:out value="${param.computerName}"/>" placeholder="<spring:message code="label.computer" />">
                                <p class="error">${errors['computerName']}</p>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introduced" /></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="<c:out value="${param.introduced}"/>" placeholder="<spring:message code="label.introduced" />">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinued" /></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="<c:out value="${param.discontinued}"/>" placeholder="<spring:message code="label.discontinued" />">
                                <p class="error">${errors['discontinued']}</p>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0" selected="selected">--</option>
                                     	<c:forEach items="${listCompanies}" var="company" varStatus="status">
                                			<option value="${company.companyId}"><c:out value="${company.companyName}"/></option>
                                		</c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code="button.add" /> class="btn btn-primary">
                            <a href="ListComputer" class="btn btn-default"><spring:message code="button.cancel" /></a>
                        </div>
                        <p class="${empty errors ? 'success' : 'error'}">${resultCreation}</p>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="js/validatorForm.js"></script>
</body>
</html>