<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="../tags/ExamsTags.tld" prefix="examstag"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <jsp:useBean id="examToCheck" scope="session"
	class="by.epam.entity.Exam">
	</jsp:useBean>
<html>
<head>
<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle="${loc}" key="checkTitle" /></title>
</head>
<body>
<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>
<h1><examstag:i18n intvalue="${examToCheck.idexam}" key="ExamName"/>
	<fmt:message bundle="${loc}" key="${key}" />	</h1>
	<fmt:message bundle="${loc}" key="checkContent" />:
	<br>
<jsp:getProperty name="examToCheck" property="content"/>
<br>
<br>
	<fmt:message bundle="${loc}" key="checkStudentcontent" />:
	<br>
	
	<c:choose>
	<c:when  test="${ not empty examToCheck.studentcontent }">
	<jsp:getProperty name="examToCheck" property="studentcontent"/>
	</c:when>
	<c:otherwise>
	<fmt:message bundle="${loc}" key="checkNoanswer" />
	</c:otherwise>
	</c:choose>

<form action="MainServlet" method="POST">
		<input type="hidden" name="command" value="hasChecked">
			<input name="mark" type="text" placeholder="<fmt:message bundle="${loc}" key="checkMark" />">
			<button class="btn btn-success" type="submit"><fmt:message bundle="${loc}" key="checkCheck" /></button>
		</form>
<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>