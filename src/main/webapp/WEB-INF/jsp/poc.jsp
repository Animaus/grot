<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="/user/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/user/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">${DESCRIPTION}</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<p align="center">
					<a href="start">${VAL_START}</a><br />
					<a href="dao">${VAL_DAO}</a><br />
					<a href="properties">${VAL_PROPERTIES}</a><br />
					<a href="repository">${VAL_REPOSITORY}</a><br />
					<a href="saveUser">${VAL_SAVEUSER}</a><br />
					<a href="loginValid">${VAL_LOGIN_VALID}</a><br />
					<a href="loginInvalid">${VAL_LOGIN_INVALID}</a><br />
					<a href="denied">Access denied</a><br />
					<a href="granted">Access granted</a><br />
					<a href="user">Validation 2</a><br />
				</p>
				${message}
			</td>
		</tr>
	</table>
</div>

<jsp:include page="footer.jsp" />