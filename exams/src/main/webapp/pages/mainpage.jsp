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
<title><fmt:message bundle="${loc}" key="mainpageTitle" /></title>
</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>
	<%@include file="../WEB-INF/jspf/mainpageheader.jspf"%>

	
	<c:catch var="catchException">
	<jsp:useBean id="compiler" scope="session"
				type="by.epam.buisnesslogicbean.StudentCompiler">
			</jsp:useBean>
	</c:catch>
	
	<div align="center">
		<c:if test="${catchException == null}">	
		<c:forEach var="exam" items="${compiler.passedExams }">
		<examstag:i18n intvalue="${exam.idexam}" key="ExamName"/>
	<fmt:message bundle="${loc}" key="${key}" />	
	<c:out value=" "/>
	<fmt:message bundle="${loc}" key="mainpageMark" />
	<c:out value=": "/>
	<c:out value="${exam.mark }"/>
	<br>
		</c:forEach>	
		<c:forEach var="exam" items="${compiler.notPassedExams}">
		<examstag:i18n intvalue="${exam.idexam}" key="ExamName"/>
	<fmt:message bundle="${loc}" key="${key}" />	
		<a
			href="MainServlet?command=pass&idexam=<c:out value="${exam.idexam}"/><c:out value="&idstudent="/><c:out value="${exam.idstudent}"/>">
			<fmt:message bundle="${loc}" key="mainpagePass" />
		</a>
		<br>
		</c:forEach>
			<c:if test="${not compiler.registred }" var="testif">
				<form action="MainServlet" method="POST">
					<input type="hidden" name="command" value="registerExams">
					<br>
					<br>
					<br>
					<br>
					<button class="btn btn-success" type="submit">
						<fmt:message bundle="${loc}" key="mainpageRegister" />
					</button>
				</form>
			</c:if>
		</c:if>
	</div>
	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>