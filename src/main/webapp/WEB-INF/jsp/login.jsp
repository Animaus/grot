<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:if test="${user.hasRole('admin') == true}">&nbsp;|&nbsp;<a href="admin">${LNK_ADM}</a>&nbsp;|&nbsp;<a href="text">${LNK_ADM_TEXT}</a></c:if><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<b>${LNK_USR_LOGIN}</b></c:when><c:otherwise>&nbsp;|&nbsp;<a href="module">${LNK_USR_MODULE}</a></c:otherwise></c:choose>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_OFF}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.middleName} ${user.lastName}&nbsp;|&nbsp;<a href="logout">${LOGIN_OUT}</a></c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">${LOGIN_TXT}</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<form class="form-horizontal" method="post" action="check">
					<p align="center">
						<button type="submit" class="btn">${LOGIN_BTN}</button>
					</p>
					<p align="center">
						<i>${LOGIN_USR}:</i><br />
						<input type="text" name="username" placeholder="${LOGIN_USR}" size="50" maxlength="30">
					</p>
					<p align="center">
						<i>${LOGIN_PWD}:</i><br />
						<input type="password" name="password" placeholder="${LOGIN_PWD}" size="50" maxlength="30">
					</p>
				</form>
			</td>
		</tr>
	</table>
</div>

<jsp:include page="footer.jsp" />