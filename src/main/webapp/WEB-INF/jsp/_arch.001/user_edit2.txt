<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib
	uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="save">
		<table style="width: 750px;" cellspacing="4" cellpadding="4">
			<tr>
				<td valign="top" colspan="4" style="text-align: center;">
					<button type="submit" class="btn">
						<sst:message code='save.tag' />
					</button>
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
							<td valign="top" colspan="2"><b><sst:message
										code='person.tag' /></b><br />&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.userName'>
									<sst:message code='userName.label' />
								</sft:label></td>
							<td valign="top" rowspan="2">${member.userName}</td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.firstName'>
									<sst:message code='firstName.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.firstName' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.lastName'>
									<sst:message code='lastName.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.lastName' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.prefix'>
									<sst:message code='prefix.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.prefix' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.sex'>
									<sst:message code='sex.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:radiobutton
									path='member.sex' value='m' />&nbsp;man <sft:radiobutton
									path='member.sex' value='f' />&nbsp;vrouw</td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.dateBirth'>
									<sst:message code='dateBirth.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.dateBirthDisplay' placeholder='Vorm: jjjj-mm-dd' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.dateRegistered'>
									<sst:message code='dateRegistered.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.dateRegisteredDisplay'
									placeholder='Vorm: jjjj-mm-dd' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.password'>
									<sst:message code='password.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.password' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.enabled'>
									<sst:message code='enabled.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:checkbox
									path='member.enabled' value='${enabled}' /> Actief</td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.userRoles'>
									<sst:message code='userRoles.label' />
								</sft:label></td>
							<td valign="top">
								<!-- TODO 14 - Show users, rename roles TO userRoles --> <!-- TODO 14 - Show users, SAVE MEMBER FROM INTERFACE -->
								<c:forEach items='${roles}' var='role'>
									<c:set var="checked" value="false" />
									<c:forEach items="${member.userRoles}" var="userRole">
										<c:if test="${role.roleName==userRole}">
											<c:set var="checked" value="true" />
										</c:if>
									</c:forEach>
									<c:choose>
										<c:when test="${checked==true}">
											<input name="roles" type="checkbox" value="${role.roleName}"
												checked />&nbsp;${role.roleDesc}<br />
										</c:when>
										<c:otherwise>
											<input name="roles" type="checkbox" value="${role.roleName}" />&nbsp;${role.roleDesc}<br />
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</td>
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
							<td valign="top" colspan="2"><b><sst:message
										code='address.tag' /></b><br />&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.streetName'>
									<sst:message code='streetName.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.streetName' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.streetNumber'>
									<sst:message code='streetNumber.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.streetNumber' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.zip'>
									<sst:message code='zip.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.zip'
									placeholder='Bijv.: 9999 XX' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.city'>
									<sst:message code='city.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.city' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.phone1'>
									<sst:message code='phone1.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.phone1'
									placeholder='Bijv.: 0612345678' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.phone2'>
									<sst:message code='phone2.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.phone2'
									placeholder='Bijv.: 0612345678' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.email1'>
									<sst:message code='email1.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.email1'
									placeholder='Bijv.: iemand@domein.nl' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.email2'>
									<sst:message code='email2.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input path='member.email2'
									placeholder='Bijv.: iemand@domein.nl' /></td>
						</tr>
						<tr>
							<td valign="top">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top"><sft:label path='member.country'>
									<sst:message code='country.label' />
								</sft:label></td>
							<td valign="top" rowspan="2"><sft:input
									path='member.countryName' /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
