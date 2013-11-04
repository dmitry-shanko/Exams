<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body >
<c:choose>
<c:when test="${success eq 'yes' }">
<c:set scope="session" var="newsuccess" value="yes"/>
</c:when>
<c:when test="${success eq 'no' }">
<c:set scope="session" var="newsuccess" value="no"/>
</c:when>
</c:choose>

<c:redirect url="MainServlet?command=adminpage"/>
</body>
</html>