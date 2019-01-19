<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<b>${LNK_USR_LOGIN}</b></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/user/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">${LOGIN_MSG}</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<form class="form-horizontal" method="post" action="login">
					<p align="center">
						<button type="submit" class="btn">${LOGIN_ONN}</button>
					</p>
					<p align="center">
						<i>${LOGIN_USR}:</i><br />
						<input type="text" name="username" value="${username}" placeholder="${LOGIN_USR}" size="50" maxlength="30">
					</p>
					<p align="center">
						<i>${LOGIN_PWD}:</i><br />
						<input type="password" name="password" placeholder="${LOGIN_PWD}" size="50" maxlength="30">
					</p>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<p align="center">
					<form class="form-horizontal" method="post" action="login">
						<button type="submit" class="btn">Gerard</button>
						<input type="hidden" name="username" value="zoetg00">
						<input type="hidden" name="password" value="123456">
					</form>
					<form class="form-horizontal" method="post" action="login">
						<button type="submit" class="btn">Thierry</button>
						<input type="hidden" name="username" value="front00">
						<input type="hidden" name="password" value="123456">
					</form>
					<form class="form-horizontal" method="post" action="login">
						<button type="submit" class="btn">Jeanne</button>
						<input type="hidden" name="username" value="arc0j00">
						<input type="hidden" name="password" value="123456">
					</form>
				</p>
			</td>
		</tr>
	</table>
</div>

<c:if test="${not empty error}">
	<div class="alert alert-error" align="center">${error}</div>
</c:if>

<jsp:include page="footer.jsp" />