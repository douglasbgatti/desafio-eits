<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspl/taglibs.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
	<fieldset>
		<c:url value="/auth/login" var="loginUrl" />
		<form action="${loginUrl}" method="post">

			<c:if test="${error != null}">
				<p class="error">${error}</p>
			</c:if>
			<c:if test="${logout != null}">
				<p class="error">${logout}</p>
			</c:if>
			<p>
				<label for="username">Username</label> <input type="text"
					id="username" name="j_username" />
			</p>
			<p>
				<label for="password">Password</label> <input type="password"
					id="password" name="j_password" />
			</p>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn">Log in</button>
		</form>
	</fieldset>

</body>
</html>