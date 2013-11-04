<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="../tags/ExamsTags.tld" prefix="examstag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="examToPass" scope="session"
	class="by.epam.entity.Exam">
</jsp:useBean>
<html>
<head>
<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle="${loc}" key="passTitle" /></title>
</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>
	<div align="center">
		<h1>
			<examstag:i18n intvalue="${examToPass.idexam}" key="ExamName" />
			<fmt:message bundle="${loc}" key="${key}" />
		</h1>
		<form action="MainServlet" method="POST">
			<input type="hidden" name="command" value="addContent">
			<fmt:message bundle="${loc}" key="passContent" />
			: <br>
			<c:out value="${examToPass.content }" />
			<br>
			<textarea rows="15" cols="80" name="content" class="span4"
				id="inIcon"
				placeholder="<fmt:message bundle="${loc}" key="passStudentcontent" />"></textarea>
			<br>
			<button class="btn btn-success" type="submit">
				<fmt:message bundle="${loc}" key="passPass" />
			</button>

		</form>
	</div>
	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>