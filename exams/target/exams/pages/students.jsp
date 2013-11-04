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
<title><fmt:message bundle="${loc}" key="studentsTitle" /></title>
  <link href="bootstrap/css/mytable.css" rel="stylesheet">
</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/header.jspf"%>

	<div align="center">
		<c:if test="${catchException == null}">
			<c:forEach var="faculty" items="${facultiesCompiler.faculties }">
					<examstag:i18n intvalue="${faculty.id}" key="FacultyName" />
<fmt:message bundle="${loc}" key="${key}" />:

							<table border="0" cellpadding="10" class="table table-condensed">
							<tbody>
								<tr class="info">
									<th><fmt:message bundle="${loc}" key="studentsName" /></th>
									<th><fmt:message bundle="${loc}" key="studentsSurname" /></th>
									<th><fmt:message bundle="${loc}" key="studentsMark" /></th>
								</tr></tbody>
								<c:set var="key" value="${faculty.id}" />
								<c:set var="i" value="${0}" scope="page"/>
								<c:forEach var="student"
									items="${facultiesCompiler.students[key] }">
								<c:if test="${i < faculty.maxstudents }" var="testif">
									<tbody>
								<tr class="info">
										<c:choose>
											<c:when
												test="${student.user.idusers eq facultiesCompiler.user.idusers}">
												<td><font color="#008000"><c:out
															value="${student.user.name }" /></font></td>
												<td><font color="#008000"><c:out
															value="${student.user.surname }" /></font></td>
												<td><font color="#008000"> <fmt:formatNumber
															maxFractionDigits="2">
															<c:out value="${student.mark }" />
														</fmt:formatNumber></font></td>
											</c:when>
											<c:otherwise>
												<td><c:out value="${student.user.name }" /></td>
												<td><c:out value="${student.user.surname }" /></td>
												<td><fmt:formatNumber maxFractionDigits="2">
														<c:out value="${student.mark }" />
													</fmt:formatNumber></td>
											</c:otherwise>
										</c:choose>
									</tr></tbody>
								</c:if>
								<c:set var="i" value="${i + 1}" scope="page"/>
								</c:forEach>
								<tr class="info">
								</tr>
							</table>
				<br>
			</c:forEach>


		</c:if>
	</div>

	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>