<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<table border="0" style="width: 750px;" cellspacing="4" cellpadding="4">
		<tr>
			<td valign="top" style="width: 50%;">
				<table border="0" style="width: 100%;">
					<tr>
						<td valign="top" style="width: 35%;"></td>
						<td valign="top" style="width: 65%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;" colspan="2"><b><sst:message code='person.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='userName.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.userName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='firstName.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.firstName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='lastName.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.lastName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='prefix.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.prefix}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='sex.label' /></td>
						<td valign="top" style="height: 40px;"><c:choose><c:when test="${fixedUser.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='dateBirth.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.dateBirthDisplay}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='dateRegistered.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.dateRegisteredDisplay}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='password.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.password}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='enabled.label' /></td>
						<td valign="top" style="height: 40px;"><c:choose><c:when test="${fixedUser.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose></td>
					</tr><c:if test="${user.hasRole('ROLE_USER') == true}">
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='userRoles.label' /></td>
						<td valign="top" style="height: 40px;"><c:forEach items='${fixedUser.userRoles}' var='userRole'>${userRole.roleDesc}<br /></c:forEach>
					</tr></c:if>
				</table>
			</td>
			<td valign="top" style="width: 50%;">
				<table border="0" style="width: 100%;">
					<tr>
						<td valign="top" style="width: 35%;"></td>
						<td valign="top" style="width: 65%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='streetName.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.streetName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='streetNumber.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.streetNumber}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='zip.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.zip}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='city.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.city}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='phone1.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.phone1}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='phone2.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.phone2}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='email1.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.email1}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='email2.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.email2}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='country.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.address.countryName}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
