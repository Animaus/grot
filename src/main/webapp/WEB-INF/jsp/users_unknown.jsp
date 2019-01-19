<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<jsp:include page="users_menu.jsp" />
<p align="center">&nbsp;</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<c:forEach items='${profiles}' var='profile'>
					<img src="user/${profile.userName}/avatar.png" width="24" border="0" onError="this.src='<sst:url value="/img" />/user.png';">&nbsp;<a href="user/${profile.userName}">${profile.firstName} ${profile.prefix} ${profile.lastName}</a><br/>
				</c:forEach>
			</td>
		</tr>
	</table>
</div>
<p align="center"><i>users_unknown.jsp</i></p>

<jsp:include page="design.jsp" />
<jsp:include page="footer.jsp" />