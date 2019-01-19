<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="ct" uri="/WEB-INF/CustomTags"%><%@ taglib prefix="sst" uri="http://www.springframework.org/tags"%><!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NOFAS</title>
<style type="text/css"><!-- @import url("<c:url value='/css/thm.css' />"); --></style>
</head>

<body onload='document.loginForm.username.focus();'>

<jsp:include page="header.jsp" />
	<div class="thmContent">
		<div class="thmLoginForm">
			<p><sst:message code='login.directive' /></p>
			<ct:login />
			<c:if test="${not empty error}">
				<div class="alert alert-error"><sst:message code='login.error' /></div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="alert alert-error">${msg}</div>
			</c:if>
		</div>
	</div>
</body>
</html>