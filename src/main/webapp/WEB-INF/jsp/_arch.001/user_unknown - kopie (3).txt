<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%>
<jsp:include page="header.jsp" />
<jsp:include page="users_menu.jsp" />

<p align="center">TEST</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td style="width: 250px;"></td>
			<td style="width: 250px;"></td>
		</tr>
		<tr>
			<td colspan="2"><b><sst:message code='person.tag' /></b></td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.userName'><sst:message code='userName.label' /></sft:label>
			</td>
			<td>
				${member.userName}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.firstName'><sst:message code='firstName.label' /></sft:label>
			</td>
			<td>
				${member.firstName}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.lastName'><sst:message code='lastName.label' /></sft:label>
			</td>
			<td>
				${member.lastName}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.prefix'><sst:message code='prefix.label' /></sft:label>
			</td>
			<td>
				${member.prefix}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.sex'><sst:message code='sex.label' /></sft:label>
			</td>
			<td>
				<c:choose><c:when test="${member.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose>
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.dateBirth'><sst:message code='dateBirth.label' /></sft:label>
			</td>
			<td>
				${member.dateBirthDisplay}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.dateRegistered'><sst:message code='dateRegistered.label' /></sft:label>
			</td>
			<td>
				${member.dateRegisteredDisplay}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.password'><sst:message code='password.label' /></sft:label>
			</td>
			<td>
				${member.password}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.enabled'><sst:message code='enabled.label' /></sft:label>
			</td>
			<td>
				<c:choose><c:when test="${member.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose>
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='member.roles'><sst:message code='roles.label' /></sft:label>
			</td>
			<td>
				${member.roles}
			</td>
		</tr>

		<tr>
			<td colspan="2"><b><sst:message code='address.tag' /></b></td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.streetName'><sst:message code='streetName.label' /></sft:label>
			</td>
			<td>
				${address.streetName}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.streetNumber'><sst:message code='streetNumber.label' /></sft:label>
			</td>
			<td>
				${address.streetNumber}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.zip'><sst:message code='zip.label' /></sft:label>
			</td>
			<td>
				${address.zip}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.city'><sst:message code='city.label' /></sft:label>
			</td>
			<td>
				${address.city}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.city'><sst:message code='city.label' /></sft:label>
			</td>
			<td>
				${address.city}
			</td>
		</tr>
		<tr>
			<td>
				<sft:label path='address.country'><sst:message code='country.label' /></sft:label>
			</td>
			<td>
				${address.country}
			</td>
		</tr>

	</table>
</div>

<hr>
<p align="center"><i>user_unknown.jsp</i></p>
<hr>

<p align="center"><img src="/img/design/class_diagram_02_070.jpg"></p>
<hr>

<p align="center"><img src="/img/design/sql_datamodel_02_070.jpg"></p>
<hr>

<!-- TODO 14 - Show users -->
<jsp:include page="footer.jsp" />
