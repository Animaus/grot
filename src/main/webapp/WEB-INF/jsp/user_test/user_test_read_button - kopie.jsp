<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="get" action="user">
	<table style="width: 750px;" cellspacing="4" cellpadding="4">
		<tr>
			<td valign="top" colspan="4" style="text-align: center;">
				<button type="submit" class="btn"><sst:message code='edit.tag' /></button>
			</td>
		</tr>
		<tr>
			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="width: 45%;"></td>
						<td valign="top" style="width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" colspan="2"><b><sst:message code='person.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.userName'><sst:message code='userName.label' /></sft:label></td>
						<td valign="top">${user.userName}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top">${user.firstName}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.lastName'><sst:message code='lastName.label' /></sft:label></td>
						<td valign="top">${user.lastName}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.prefix'><sst:message code='prefix.label' /></sft:label></td>
						<td valign="top">${user.prefix}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.sex'><sst:message code='sex.label' /></sft:label></td>
						<td valign="top"><c:choose><c:when test="${user.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.dateBirth'><sst:message code='dateBirth.label' /></sft:label></td>
						<td valign="top">${user.dateBirthDisplay}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.dateRegistered'><sst:message code='dateRegistered.label' /></sft:label></td>
						<td valign="top">${user.dateRegisteredDisplay}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.password'><sst:message code='password.label' /></sft:label></td>
						<td valign="top">${user.password}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.enabled'><sst:message code='enabled.label' /></sft:label></td>
						<td valign="top"><c:choose><c:when test="${user.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.userRoles'><sst:message code='userRoles.label' /></sft:label></td>
						<td valign="top">${user.userRoleNames}</td>
					</tr>
				</table>
			</td>

			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="width: 45%;"></td>
						<td valign="top" style="width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.streetName'><sst:message code='streetName.label' /></sft:label></td>
						<td valign="top">${user.streetName}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.streetNumber'><sst:message code='streetNumber.label' /></sft:label></td>
						<td valign="top">${user.streetNumber}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.zip'><sst:message code='zip.label' /></sft:label></td>
						<td valign="top">${user.zip}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.city'><sst:message code='city.label' /></sft:label></td>
						<td valign="top">${user.city}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.phone1'><sst:message code='phone1.label' /></sft:label></td>
						<td valign="top">${user.phone1}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.phone2'><sst:message code='phone2.label' /></sft:label></td>
						<td valign="top">${user.phone2}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.email1'><sst:message code='email1.label' /></sft:label></td>
						<td valign="top">${user.email1}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.email2'><sst:message code='email2.label' /></sft:label></td>
						<td valign="top">${user.email2}</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='user.country'><sst:message code='country.label' /></sft:label></td>
						<td valign="top">${user.countryName}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
