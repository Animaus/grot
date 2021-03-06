<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<sft:form method = "POST" action = "save" modelAttribute = "mutable">
	<table style="width: 750px;">
		<tr>
			<td valign="top" style="height: 40px; text-align: center;" colspan="2">
				<button type="submit" class="btn"><sst:message code='save.tag' /></button>
			</td>
		</tr>
		<tr>
			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="width: 35%;"></td>
						<td valign="top" style="width: 65%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;" colspan="2"><b><sst:message code='person.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='userName'><sst:message code='userName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">${fixedUser.userName}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px; text-align: right;" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='firstName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='firstName' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='lastName'><sst:message code='lastName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='lastName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='lastName' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='prefix'><sst:message code='prefix.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='prefix' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='prefix' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='sex'><sst:message code='sex.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:radiobutton path='sex' value='m' />&nbsp;man <sft:radiobutton path='sex' value='f' />&nbsp;vrouw</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='sex' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='dateBirth'><sst:message code='dateBirth.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='dateBirth' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='dateBirth' class='text-error' />					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sst:message code='dateRegistered.label' /></td>
						<td valign="top" style="height: 40px;">${fixedUser.dateRegisteredDisplay}</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px; text-align: center;" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='password'><sst:message code='password.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='password' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='password' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='enabled'><sst:message code='enabled.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><c:choose><c:when test="${user.hasRole('ROLE_ADMIN')==true}"><sft:checkbox path='enabled' value='${enabled}' />&nbsp;Actief</c:when><c:otherwise><c:choose><c:when test="${mutable.enabled==true}">Actief</c:when><c:otherwise>Inactief</c:otherwise></c:choose></c:otherwise></c:choose></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='enabled' class='text-error' /></td>
					</tr><c:if test="${user.hasRole('ROLE_USER') == true}">
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='userRoles'><sst:message code='userRoles.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><c:if test="${user.hasRole('ROLE_ADMIN') == false}">
							<c:forEach items='${fixedUser.userRoles}' var='userRole'>${userRole.roleDesc}<br /></c:forEach></c:if><c:forEach items='${roles.keySet()}' var='roleName'><c:set var="checked" value="false" /><c:forEach items="${mutable.userRoleNames}" var="userRoleName"><c:if test="${roleName==userRoleName}"><c:set var="checked" value="true" /></c:if></c:forEach><c:choose><c:when test="${checked==true}">
							<input name="roles" type="checkbox" value="${roleName}" checked />&nbsp;${roles.get(roleName)}<br /></c:when><c:otherwise>
							<input name="roles" type="checkbox" value="${roleName}" />&nbsp;${roles.get(roleName)}<br /></c:otherwise></c:choose></c:forEach>
						</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='userRoles' class='text-error' /></td>
					</tr></c:if>
				</table>
			</td>
			<td valign="top" style="width: 50%;">
				<table style="width: 100%;">
					<tr>
						<td valign="top" style="width: 35%;"></td>
						<td valign="top" style="width: 65%;"></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.streetName'><sst:message code='streetName.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.streetName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.streetName' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.streetNumber'><sst:message code='streetNumber.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.streetNumber' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.streetNumber' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.zip'><sst:message code='zip.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.zip' placeholder='Bijv.: 9999 XX' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.zip' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.city'><sst:message code='city.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.city' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.city' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.phone1'><sst:message code='phone1.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.phone1' placeholder='Bijv.: 0612345678' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.phone1' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.phone2'><sst:message code='phone2.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.phone2' placeholder='Bijv.: 0612345678' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.phone2' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.email1'><sst:message code='email1.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.email1' placeholder='Bijv.: iemand@domein.nl' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.email1' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.email2'><sst:message code='email2.label' /></sft:label></td>
						<td valign="top" style="height: 40px;"><sft:input path='address.email2' placeholder='Bijv.: iemand@domein.nl' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.email2' class='text-error' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"><sft:label path='address.country'><sst:message code='country.label' /></sft:label></td>
						<td valign="top" style="height: 40px;">
<sft:select path='address.country' style='width: 100%;'><c:forEach items='${countries}' var='country'><c:choose><c:when test="${country==mutable.address.country}"><option value='${country}' selected>${country.getName()}</option></c:when><c:otherwise><option value='${country}'>${country.getName()}</option></c:otherwise></c:choose></c:forEach></sft:select>
						</td>
					</tr>
					<tr>
						<td valign="top" style="height: 40px;"></td><td valign="top" style="height: 40px;"><sft:errors path='address.country' class='text-error' /></td>
					</tr>
				</table>
			</td>
		</tr><c:choose><c:when test="${message==null}"></c:when><c:otherwise>
		<tr>
			<td valign="top" colspan="2">${log}</td>
		</tr></c:otherwise></c:choose>
	</table>
	</sft:form>
</div>
