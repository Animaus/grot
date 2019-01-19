<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sft"%><%@ taglib uri="http://www.springframework.org/tags" prefix="sst"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
	<form class="form-horizontal" method="post" action="save">
		<p><button type="submit" class="btn"><sst:message code='save.tag' /></button></p>
		<p><sft:label path='member.firstName'><sst:message code='firstName.label' /></sft:label><br /><sft:errors path='member.firstName' /></p>
		<p><sft:input path='member.firstName' /></p>
	</form>
	MESSAGE :<br>${message}
</div>
