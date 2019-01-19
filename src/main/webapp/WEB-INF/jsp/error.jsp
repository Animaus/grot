<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="/user/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/user/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<h2 align="center">ERROR</h2>

<div align="center">
	<table style="width: 95%;" cellpadding="4" cellspacing="4" border="0">
        <tr>
            <td valign="top"><b>Date</b></td>
            <td valign="top">${timestamp}</td>
        </tr>
        <tr>
            <td valign="top"><b>Status</b></td>
            <td valign="top">${status}</td>
        </tr>
        <tr>
            <td valign="top"><b>Message</b></td>
            <td valign="top">${message}</td>
        </tr>
		<tr>
            <td valign="top"><b>URI</b></td>
            <td valign="top">${requestUri}</td>
        </tr>
        <tr>
            <td valign="top"><b>Referrer</b></td>
            <td valign="top"><a href="${referrer}">${referrer}</a></td>
        </tr>
        <tr>
            <td valign="top"><b>Exception</b></td>
            <td valign="top">${exception}</td>
        </tr>
        <tr>
            <td valign="top"><b>Trace</b></td>
            <td valign="top">${trace}</td>
        </tr>
	</table>
</div>

<jsp:include page="footer.jsp" />