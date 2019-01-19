<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="user" modelAttribute="user">
				<button type="submit" class="btn"><sst:message code='save.tag' /></button>

	<table border="1" style="width: 750px;" cellspacing="4" cellpadding="4">
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
						<td valign="top" style="height: 30px; text-align: right;" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px;"><sft:label path='user.firstName'><sst:message code='firstName.label' /></sft:label></td>
						<td valign="top" style="height: 30px;"><sft:input path='user.firstName' /></td>
					</tr>
					<tr>
						<td valign="top" style="height: 30px; text-align: right;" colspan="2"><sft:errors path='user.firstName' class='text-error'/></td>
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
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
