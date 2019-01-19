<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="user" modelAttribute="user">

	<table border="1" style="width: 750px;" cellspacing="4" cellpadding="4">
		<tr>
			<td valign="top" style="height: 30px; text-align: center;" colspan="4">
				<button type="submit" class="btn"><sst:message code='save.tag' /></button>
			</td>
		</tr>
		<tr>
			<td valign="top" style="height: 30px; width: 50%;">
				<table border="1" style="width: 100%;">
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
						<td valign="top" style="height: 30px;">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:input path='user.firstName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.firstName' class='text-error'/></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.lastName'><sst:message code='lastName.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:input path='user.lastName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.lastName' class='text-error'/></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.prefix'><sst:message code='prefix.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:input path='user.prefix' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.prefix' class='text-error'/></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.sex'><sst:message code='sex.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:radiobutton path='user.sex' value='m' />&nbsp;man <sft:radiobutton path='user.sex' value='f' />&nbsp;vrouw</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.sex' class='text-error'/></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.password'><sst:message code='password.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:input path='user.password' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.password' class='text-error'/></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.enabled'><sst:message code='enabled.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><c:if test="${user.hasRole('ROLE_ADMIN') == true}"><sft:checkbox path='user.enabled' value='${enabled}' />&nbsp;</c:if>Actief</td>
					</tr>
					<tr>
						<td valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.userRoles'><sst:message code='userRoles.label' /></sft:label></td>
						<td valign="top" style="height: 30px;">
							<c:if test="${user.hasRole('ROLE_ADMIN') == false}">${fixedUser.userRoleNames}</c:if><c:forEach items='${roles}' var='role'><c:set var="checked" value="false" /><c:forEach items="${user.userRoles}" var="userRole"><c:if test="${role.roleName==userRole.roleName}"><c:set var="checked" value="true" /></c:if></c:forEach><c:choose><c:when test="${checked==true}"><input name="roles" type="checkbox" value="${role.roleName}" checked />&nbsp;${role.roleDesc}<br />
							</c:when><c:otherwise><input name="roles" type="checkbox" value="${role.roleName}" />&nbsp;${role.roleDesc}<br /></c:otherwise></c:choose></c:forEach>
						</td>
					</tr>
				</table>
			</td>

			<td valign="top" style="height: 30px; width: 50%;">
				<table border="1" style="width: 100%;">
					<tr>
						<td valign="top" style="height: 30px; width: 45%;"></td>
						<td valign="top" style="height: 30px; width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;">&nbsp;</td>
						<td valign="top" style="height: 30px;">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
