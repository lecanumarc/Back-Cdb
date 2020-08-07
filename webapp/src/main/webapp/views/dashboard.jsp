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
				<a class="navbar-brand" href="ListComputer"> Application - Computer Database </a>
			</div>
			<div class="pull-right">
				<a class="navbar-brand" href="logout" >Logout</a> 
				<div>
					<a id="en" href="ListComputer?lang=en&linesNb=${linesNb}&pageNb=${currentPage}&search=${search}&order=${order}"><spring:message code="lang.en" /></a> 
			| 		<a id="fr" href="ListComputer?lang=fr&linesNb=${linesNb}&pageNb=${currentPage}&search=${search}&order=${order}"><spring:message code="lang.fr" /></a>	
				</div>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${totalComputers}" />
				<c:choose>
					<c:when test="${totalComputers <= 1 }">
						<spring:message code="label.numberComputer" />
					</c:when>
					<c:otherwise>
						<spring:message code="label.numberComputers" />
					</c:otherwise>
				</c:choose>
				
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" value="<c:out value="${param.search}"/>"
							placeholder="Search name" /> <input type="submit"
							id="searchsubmit" value=<spring:message code="label.search" /> class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer"><spring:message code="label.addComputer" /></a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="button.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="label.computer" /><a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=name ASC&search=${search}"><i
								class="glyphicon glyphicon-chevron-up"></i></a> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=name DESC&search=${search}"><i
								class="glyphicon glyphicon-chevron-down"></i></a>
						</th>
						<th><spring:message code="label.introduced" /><a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=introduced ASC&search=${search}"><i
								class="glyphicon glyphicon-chevron-up"></i></a> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=introduced DESC&search=${search}"><i
								class="glyphicon glyphicon-chevron-down"></i></a>
						</th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.discontinued" /> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=discontinued ASC&search=${search}"><i
								class="glyphicon glyphicon-chevron-up"></i></a> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=discontinued DESC&search=${search}"><i
								class="glyphicon glyphicon-chevron-down"></i></a>
						</th>
						<!-- Table header for Company -->
						<th><spring:message code="label.company" /> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=company_name ASC&search=${search}"><i
								class="glyphicon glyphicon-chevron-up"></i></a> <a
							href="ListComputer?pageNb=${currentPage}&linesNb=${linesNb}&order=company_name DESC&search=${search}"><i
								class="glyphicon glyphicon-chevron-down"></i></a>
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach items="${listComputers}" var="computer"
						varStatus="status">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.computerId}"></td>
							<td><a href="EditComputer?computerId=${computer.computerId}"
								onclick=""><c:out value="${computer.computerName}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.companyDTO.companyName}" /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">

				<%--For displaying Previous link except for the 1st page --%>
				<li><c:if test="${currentPage != 1}">
						<button
							onclick="window.location.href='ListComputer?linesNb=${linesNb}&pageNb=${currentPage - 1}&search=${search}&order=${order}'"
							aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</button>
					</c:if> <c:forEach begin="0" end="10" var="i">
						<c:if test="${currentPage+i<=totalPages}">
							<c:choose>
								<c:when test="${i==0}">
									<button type="button" class="btn active"
										onclick="window.location.href='ListComputer?linesNb=${linesNb}&pageNb=${currentPage}&search=${search}&order=${order}'">
										<c:out value="${currentPage}"></c:out>
									</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-default"
										onclick="window.location.href='ListComputer?linesNb=${linesNb}&pageNb=${currentPage+i}&search=${search}&order=${order}'">
										<c:out value="${currentPage+i}"></c:out>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach> <%--For displaying Next link except for the last page --%> <c:if
						test="${currentPage != totalPages}">
						<button
							onclick="window.location.href='ListComputer?linesNb=${linesNb}&pageNb=${currentPage + 1}&search=${search}&order=${order}'"
							aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</button>
					</c:if></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:choose>
					<c:when test="${ linesNb eq 10 }">
						<button type="button" class="btn active"
							onclick="window.location.href='ListComputer?linesNb=10&search=${search}&order=${order}'">10</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='ListComputer?linesNb=10&search=${search}&order=${order}'">10</button>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ linesNb eq 50 }">
						<button type="button" class="btn active"
							onclick="window.location.href='ListComputer?linesNb=50&search=${search}&order=${order}'">50</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='ListComputer?linesNb=50&search=${search}&order=${order}'">50</button>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ linesNb eq 100 }">
						<button type="button" class="btn active"
							onclick="window.location.href='ListComputer?linesNb=100&search=${search}&order=${order}'">100</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='ListComputer?linesNb=100&search=${search}&order=${order}'">100</button>
					</c:otherwise>
				</c:choose>
			</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>