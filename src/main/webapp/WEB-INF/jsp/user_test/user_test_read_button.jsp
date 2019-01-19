<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="get" action="user" modelAttribute="user">
	<table style="width: 750px;" cellspacing="4" cellpadding="4">
		<tr>
			<td valign="top" style="height: 30px;" colspan="4" style="text-align: center;">
				<button type="submit" class="btn"><sst:message code='edit.tag' /></button>
			</td>
		</tr>
		<tr>
			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="height: 30px; width: 45%;"></td>
						<td valign="top" style="height: 30px; width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;" colspan="2"><b><sst:message code='person.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sst:message code='userName.label' /></td>
						<td valign="top" style="height: 30px;">${fixedUser.userName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">${user.firstName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.lastName'><sst:message code='lastName.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">${user.lastName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.prefix'><sst:message code='prefix.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">${user.prefix}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.sex'><sst:message code='sex.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><c:choose><c:when test="${user.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.password'><sst:message code='password.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">${user.password}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.enabled'><sst:message code='enabled.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><c:choose><c:when test="${user.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.userRoles'><sst:message code='userRoles.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">${user.userRoleNames}</td>
					</tr>
				</table>
			</td>

			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="height: 30px; width: 45%;"></td>
						<td valign="top" style="height: 30px; width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;" colspan="2">${user.address}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
