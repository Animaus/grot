<%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<jsp:include page="users_menu.jsp" />
<p align="center">&nbsp;</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<form class="form-horizontal" method="get" action="${member.userName}/edit">
					<button type="submit" class="btn"><sst:message code='edit.tag' /></button>
				</form>
			</td>
		</tr>
		<tr>
			<td>${message} - ${member.userName}</td>
		</tr>
	</table>
</div>
<p align="center">
	<i>user_verified.jsp</i>
</p>
<!-- TODO 14 - Show users -->
<jsp:include page="footer.jsp" />