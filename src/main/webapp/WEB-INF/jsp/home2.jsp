<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<div align="center">
	<b>${LNK_USR_HOME}</b><c:if test="${user.hasRole('admin') == true}">&nbsp;|&nbsp;<a href="admin">${LNK_ADM}</a>&nbsp;|&nbsp;<a href="text">${LNK_ADM_TEXT}</a></c:if><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="user/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="module">${LNK_USR_MODULE}</a></c:otherwise></c:choose>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_OFF}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.middleName} ${user.lastName}&nbsp;|&nbsp;<a href="logout">${LOGIN_OUT}</a></c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">&nbsp;</p>

<p align="center"><img src="img/caveman.gif"></p>

<p align="center">${message}</p>

<p align="center">
	<a href="/poc/start">Proofs of concepts</a><br />
</p>

<jsp:include page="footer.jsp" />