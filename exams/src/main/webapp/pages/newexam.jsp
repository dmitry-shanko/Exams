<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/ExamsTags.tld" prefix="examstag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><fmt:message bundle="${loc}" key="newexamTitle" /></title>
</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>
	<div align="center">
		<h4>
			<fmt:message bundle="${loc}" key="newexamMain" />
			:
		</h4>
		<br>
	</div>
	<form action="MainServlet" method="GET">
		<input type="hidden" name="command" value="createwrite">
		<table width=400 border=0>
			<tr>
				<td><fmt:message bundle="${loc}" key="newexamName" />:</td>
				<td><input name="examname" class="span4" id="inputIcon"
					type="text"
					placeholder="<fmt:message bundle="${loc}" key="newexamNamefield" />">
				</td>
			</tr>
			<tr>
				<td><fmt:message bundle="${loc}" key="newexamContent" />:</td>
				<td></td>
			</tr>
		</table>
		<div align="left">


			<textarea rows="15" cols="80" name="content" class="span4"
				id="inIcon"
				placeholder="<fmt:message bundle="${loc}" key="newexamContent" />"></textarea>

			<br>
			<c:forEach var="curfaculty" items="${faculties}">
				<input TYPE="checkbox" NAME="faculty" VALUE="${curfaculty.id}">
				<examstag:i18n intvalue="${curfaculty.id}" key="FacultyName" />
				<fmt:message bundle="${loc}" key="${key}" />
				<br>
			</c:forEach>
		</div>

		<br>
		<button class="btn btn-success" type="submit">
			<fmt:message bundle="${loc}" key="newexamSubmit" />
		</button>
	</form>

	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>
