<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/ExamsTags.tld" prefix="examstag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle="${loc}" key="examsTitle" /></title>
</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>
	<div align="center">
		<fmt:message bundle="${loc}" key="examsChose" />
		<form action="MainServlet" method="POST">
			<input type="hidden" name="command" value="setFaculty">
			<c:forEach var="curfaculty" items="${faculties}">
				<examstag:i18n intvalue="${curfaculty.id}" key="FacultyName" />

				<input type="radio" name="faculty" value="${curfaculty.id }">
				<fmt:message bundle="${loc}" key="${key}" />
				<Br>
			</c:forEach>
			<br>

			<button class="btn btn-success" type="submit">
				<fmt:message bundle="${loc}" key="examsCommit" />
			</button>
		</form>
	</div>
	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>