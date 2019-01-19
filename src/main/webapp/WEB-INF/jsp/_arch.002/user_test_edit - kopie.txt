<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="user" modelAttribute="user">
		<p><button type="submit" class="btn"><sst:message code='save.tag' /></button></p>
		<p><sft:label path='user.firstName'><sst:message code='firstName.label' /></sft:label><br /><sft:errors path='user.firstName' /></p>
		<p><sft:input path='user.firstName' /></p>
	</form>
	<table border="1" style="width: 750px;" cellspacing="4" cellpadding="4">
		<tr>
			<td valign="top" colspan="4" style="text-align: center;">
				<button type="submit" class="btn"><sst:message code='save.tag' /></button>
			</td>
		</tr>
		<tr>

			<td valign="top" style="width: 50%;">
				<table border="1" style="width: 100%;">
					<tr>
						<td valign="top" style="width: 45%;"></td>
						<td valign="top" style="width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" colspan="2"><b><sst:message code='person.tag' /></b><br />&nbsp;</td>
					</tr>
					<tr>
						<td valign="top"><sft:label path='member.firstName'><sst:message code='firstName.label' /></sft:label><sft:errors path='firstName' /></td>
						<td valign="top" rowspan="2"><sft:input path='member.firstName' /></td>
					</tr>
					<tr>
						<td valign="top">&nbsp;</td>
					</tr>
				</table>
			</td>

			<td valign="top" style="width: 50%;">
				<table border="1" style="width: 100%;">
					<tr>
						<td valign="top" style="width: 45%;"></td>
						<td valign="top" style="width: 55%;"></td>
					</tr>
					<tr>
						<td valign="top" colspan="2"><b><sst:message code='address.tag' /></b><br />&nbsp;</td>
					</tr>
				</table>
			</td>

		</tr>
	</table>
</div>
