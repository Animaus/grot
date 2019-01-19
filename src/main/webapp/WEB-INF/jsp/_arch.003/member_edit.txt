<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="member" modelAttribute="member">
		<p><button type="submit" class="btn"><sst:message code='save.tag' /></button></p>
		<p><sft:label path='member.user.firstName'><sst:message code='firstName.label' /></sft:label><br /><sft:errors path='member.user.firstName' /></p>
		<p><sft:input path='member.user.firstName' /></p>
	</form>
	MESSAGE :<br>${message}
</div>
