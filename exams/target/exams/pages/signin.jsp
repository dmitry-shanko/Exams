<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link href="bootstrap/css/logincss.css" rel="stylesheet">
<%@include file="../WEB-INF/jspf/formatter.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle="${loc}" key="signinTitle" /></title>

<script type="text/javascript" src="js/sha1.js"></script>
<script type="text/javascript" src="js/login.js"></script>

</head>
<body>
	<%@include file="../WEB-INF/jspf/encodingheader.jspf"%>

	<form class="form-signin" action="MainServlet" method="POST"
		name="loginform">

		<input type="hidden" name="command" value="login"> <input
			type="hidden" name="pass" value="">
		<h2 class="form-signin-heading">
			<fmt:message bundle="${loc}" key="signinSigninrequest" />
		</h2>

		<table cellpadding="10" class="table table-condensed">
			<tr>
				<td>
					<div class="controls">
						<label class="control-label" for="inputIcon" class="font-size"><fmt:message
								bundle="${loc}" key="signinLoginMessage" /></label>
					</div>
				</td>
				<td>
					<div class="input-prepend">
						<span class="add-on"><i class="icon-envelope"></i></span> <input
							name="email" class="span4" id="inputIcon" type="text"
							placeholder="<fmt:message bundle="${loc}" key="signinLogin" />">

					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="controls">
						<label class="control-label" for="inIcon" class="font-size"><fmt:message
								bundle="${loc}" key="signinPasswordMessage" /></label>
					</div>
				</td>
				<td>
					<div class="input-prepend">
						<span class="add-on"><i class="icon-th-large"></i></span> <input
							name="badpass" class="span4" id="inIcon" type="password"
							placeholder="<fmt:message bundle="${loc}" key="signinPassword" />">
					</div> <br> 
					<div align="center"><font color="RED">
						<span class="logerror-instruct" id="incorrectemail"> 
							<c:if test="${not empty error_key && error_key eq 'error_key' && empty page_error }">
								<fmt:message bundle="${loc}" key="signinErrormsg" />
								
							</c:if>
							 
							 </span>
							 <br>
							<span class="logerror-instruct" id="incorrectpassword"> </span>
				</font>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="sf-btnarea">
						<a href="javascript:doLogin();" class="buttonLinkText"
							title=" <fmt:message bundle="${loc}" key="signinSignin" />
"
							tabindex=4> <span class="blt-l"></span><span class="blt-txt">
								<fmt:message bundle="${loc}" key="signinSignin" />
						</span><span class="blt-r"></span>
						</a>
					</div>
				</td>
				<td></td>
			</tr>
		</table>
	</form>


	<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>

</html>