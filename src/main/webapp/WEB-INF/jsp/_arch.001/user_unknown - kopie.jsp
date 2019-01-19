<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<jsp:include page="users_menu.jsp" />
<p align="center">TEST</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				${message} - ${member.userName}
			</td>
		</tr>

		<tr>
			<td>
			<b><sst:message code='person.tag' /></b><br><p>&nbsp;</p>
			
			<sft:label path='member.firstName'><sst:message code='firstName.label' /></sft:label><span>${member.firstName}&nbsp;</span><p>&nbsp;</p>
			<sft:label path='member.lastName'><sst:message code='lastName.label' /></sft:label><span>${member.lastName}&nbsp;</span><p>&nbsp;</p>
			<sft:label path='member.prefix'><sst:message code='prefix.label' /></sft:label><span>${member.prefix}&nbsp;</span><p>&nbsp;</p>
			<sft:label path='member.sex'><sst:message code='sex.label' /></sft:label><span><c:choose><c:when test="${member.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose>&nbsp;</span><p>&nbsp;</p>
			</td>
		</tr>

	</table>
</div>
<p align="center"><i>user_unknown.jsp</i></p>
<!-- TODO 14 - Show users -->
<jsp:include page="footer.jsp" />