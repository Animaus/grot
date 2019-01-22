<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center">
	<a href="${pageContext.request.contextPath}/welcome">Welcome</a>&nbsp;|&nbsp;
	<a href="${pageContext.request.contextPath}/addNewUser">Add User</a>&nbsp;|&nbsp;
	<a href="${pageContext.request.contextPath}/getUsers">Show Users</a>&nbsp;|&nbsp;
	<a onclick="document.forms['logoutForm'].submit()">Logout</a>
    <form id="logoutForm" method="POST" action="${contextPath}/logout"></form>
</div>