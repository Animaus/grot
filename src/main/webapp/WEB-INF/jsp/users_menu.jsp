<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%><div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user==null}'>&nbsp;|&nbsp;<a href="/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>