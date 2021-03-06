<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<jsp:include page="users_menu.jsp" />
<p align="center">&nbsp;</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td style="width: 30px;"></td>
			<td style="width: 250px;"></td>
			<td></td>
		<tr>
		<c:forEach items='${profiles}' var='profile'><tr>
			<td>
				<img src="user/${profile.userName}/avatar.png" width="24" onError="this.src='<sst:url value="/img" />/user.png';">
			</td>
			<td>
				<a href="user/${profile.userName}">${profile.firstName} ${profile.prefix} ${profile.lastName}</a>
			</td>
			<td>
				<a href="user/${profile.userName}/edit"><sst:message code='edit.tag' /></a>
			</td>
		</tr></c:forEach>
	</table>
</div>

<jsp:include page="design.jsp" />
<jsp:include page="footer.jsp" />