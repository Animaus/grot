<%@ taglib prefix="nct" uri="/WEB-INF/CustomTags"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="thmHeader">
	<div class="thmTop">
		<img src="<c:url value='/images/uil_klein.jpg' />" alt="Kleine uil">
	</div>
	<div class="thmTxtTop">
		<h1>NOFAS</h1>
		<h2>Foto-site</h2>
	</div>
</div>

<hr>

<div class="thmNav">
	<a href="<c:url value="/" />">Home</a>&nbsp;|&nbsp;<c:choose><c:when test="${pageContext.request.userPrincipal.name == null}"><a href="<c:url value="/login" />">Inloggen</a></c:when><c:otherwise><a href="<c:url value="/logout" />">Uitloggen</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="<c:url value="/user" />">Ledenpagina's</a>&nbsp;|&nbsp;<a href="<c:url value="/search" />">Zoeken</a><br/>
	<a href="<c:url value="/login" />">Inloggen</a><c:choose><c:when test="${pageContext.request.userPrincipal.name == null}">&nbsp;</c:when><c:otherwise><sec:authorize access="isAuthenticated()">&nbsp;|&nbsp;<a href="<c:url value='/user/profile/${pageContext.request.userPrincipal.name}' />">Mijn profiel</a></sec:authorize></c:otherwise></c:choose>
</div>
<!-- TODO 14 - Show users -->
<p>&nbsp;</p>