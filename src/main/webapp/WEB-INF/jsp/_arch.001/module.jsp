<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><jsp:include
	page="../header.jsp" />

<div align="center">
	<a href="/genial">${LNK_USR_HOME}</a><c:if test="${user.hasRole('admin') == true}">&nbsp;|&nbsp;<a href="admin">${LNK_ADM}</a>&nbsp;|&nbsp;<a href="text">${LNK_ADM_TEXT}</a></c:if><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<b>${LNK_USR_MODULE}</b></c:otherwise></c:choose>
</div>

<div align="center"><c:choose><c:when test='${user== null}'>${LOGGED_OFF}</c:when><c:otherwise>${LOGGED_ON} ${user.firstName} ${user.middleName} ${user.lastName}&nbsp;|&nbsp;<a href="logout">${LOGGED_OUT}</a></c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<div align="center">

	<table style="width: 500px;">
		<tr>
			<td>
				<form method="post" action="module"><c:choose><c:when test="${modules==null}"></c:when><c:when test="${modules.size()==0}"></c:when><c:when test="${module== null}">
					<p align="center">
						${ITEM_SELECT}
					</p>
					<p align="center">
						<select name="modcode" size="1" style="width: 165px;"><c:forEach items="${modules}" var="module">
							<option value="${module.moduleCode}">${module.moduleCode}</option></c:forEach>
						</select>
					</p>
					<p align="center">
						<input name="proceed" type="submit" value="${BTN_PROCEED}" class="btn">
					</p></c:when><c:otherwise>
					<p align="left">
						<b>${module.moduleCode}</b><br />
						<i>${module.moduleName}</i><br /><br />
						${module.instruction}<br />
						<input name="start" type="submit" value="${BTN_START}" class="btn">
					</p></c:otherwise></c:choose>
				</form>
			</td>
		</tr>
	</table>
	
</div>

<c:choose><c:when test="${not empty message}"><div class="alert alert-error" align="center">${message}</div></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose>

<jsp:include page="../footer.jsp" />
