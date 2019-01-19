<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%><%@taglib uri="http://www.springframework.org/tags/form" prefix = "sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%>
<jsp:include page="header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="/user/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/user/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">${DESCRIPTION}</p>

<p align="center">
	<a href="start">Proofs of concepts</a>
</p>

<div align="center">
	<sft:form method = "GET" action = "user" modelAttribute = "mutable">
	<button type="submit" class="btn"><sst:message code='edit.tag' /></button>

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
						<td valign="top" style="height: 40px;"><sft:label path='firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.firstName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='lastName'><sst:message code='lastName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.lastName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='prefix'><sst:message code='prefix.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.prefix}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='sex'><sst:message code='sex.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><c:choose><c:when test="${mutable.sex=='m'}">man</c:when><c:otherwise>vrouw</c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='dateBirth'><sst:message code='dateBirth.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.dateBirthDisplay}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='dateRegistered'><sst:message code='dateRegistered.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.dateRegisteredDisplay}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='password'><sst:message code='password.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.password}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='enabled'><sst:message code='enabled.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><c:choose><c:when test="${mutable.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose></td>
					</tr><c:if test="${user.hasRole('ROLE_USER') == true}">
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='userRoles'><sst:message code='userRoles.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><c:forEach items='${mutable.userRoles}' var='userRole'>${userRole.roleDesc}<br /></c:forEach>
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
						<td valign="top" style="height: 40px;"><sft:label path='address.streetName'><sst:message code='streetName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.streetName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.streetNumber'><sst:message code='streetNumber.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.streetNumber}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.zip'><sst:message code='zip.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.zip}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.city'><sst:message code='city.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.city}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.phone1'><sst:message code='phone1.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.phone1}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.phone2'><sst:message code='phone2.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.phone2}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.email1'><sst:message code='email1.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.email1}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.email2'><sst:message code='email2.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.email2}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.country'><sst:message code='country.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${mutable.address.countryName}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</sft:form>
</div>

<jsp:include page="footer.jsp" />