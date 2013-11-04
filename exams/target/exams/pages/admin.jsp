<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="../tags/ExamsTags.tld" prefix="examstag"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="compiler" scope="session"
	type="by.epam.buisnesslogicbean.AdminCompiler">
</jsp:useBean>
<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle="${loc}" key="adminTitle" /></title>
  <link href="bootstrap/css/mytableadmin.css" rel="stylesheet">
</head>
<body>
<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>
		<c:set var="count" value="${0}"/>
		
		<div align="center">
		<c:choose>
		<c:when test="${newsuccess eq 'yes'}">
		<h4><fmt:message bundle="${loc}" key="adminSuccess" /></h4>
		<br></c:when>
		<c:when test="${newsuccess eq 'no'}">
		<h4><fmt:message bundle="${loc}" key="adminNotsuccess" /></h4>
		<br></c:when>
		</c:choose>
<c:set var="newsuccess" value="" scope="session"/>
</div>
<table border="0" class="outtable">
	<c:forEach var="exam" items="${compiler.exams}">
	<c:set var="count" value="${count + 1 }"/>
	<tr>
	<c:if test="${count eq '1' }">
	<td width="80%">
		<table border="0" cellpadding="10" class="table table-condensed">
		<tbody>
		<tr class="info">
		<th width="40%"><fmt:message bundle="${loc}" key="adminExamname" />:</th>
		<th width="30%"><fmt:message bundle="${loc}" key="adminCurstatus" />:</th>
		<th width="30%"><fmt:message bundle="${loc}" key="adminMarkexam" />:</th>
		</tr>
		</tbody>
		</table>
		</td>
		<td width="20%"></td>
		</tr>
		<tr>
	</c:if>
	<td width="20%">
	<table border="0" cellpadding="10" class="table table-condensed">
	<tr>
	<td width="40%">
	<examstag:i18n intvalue="${exam.idexam}" key="ExamName"/>
	<fmt:message bundle="${loc}" key="${key}" />
	</td>
	<td width="30%">
	<c:choose>
	<c:when test="${exam.status eq 'true'}">
	<fmt:message bundle="${loc}" key="adminCheckedexam" />
	</c:when>
	<c:otherwise>
	<fmt:message bundle="${loc}" key="adminNotheckedexam" />
	</c:otherwise>
	</c:choose>	
	
	
	</td>
	<td width="30%">
		<c:out value="${exam.mark}" /></td></tr></table><td width="20%">
		<a
			href="MainServlet?command=check&idexam=<c:out value="${exam.idexam}"/><c:out value="&idstudent="/><c:out value="${exam.idstudent}"/>">
			<fmt:message bundle="${loc}" key="adminCheck" />
		</a>
		</td>
		</tr>
		
	</c:forEach>
	</table>
	<c:if test="${count < 1 }">
		<fmt:message bundle="${loc}" key="adminNothingtocheck" />
	</c:if>
	<br>
	<examstag:instanceof bean="compiler"
		classtype="by.epam.buisnesslogicbean.MainadminCompiler">
		<form action="MainServlet" method="POST">
			<input type="hidden" name="command" value="createNewExam">
			<div align="center">
				<button class="btn btn-success" type="submit">
					<fmt:message bundle="${loc}" key="adminCreate" />
				</button>
				<br>
				<br>
					<br>
				<br>
			</div>
		</form>
	</examstag:instanceof>

	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>