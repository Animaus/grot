<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ taglib prefix="sft" uri="http://www.springframework.org/tags/form"%><%@ taglib prefix="sst" uri="http://www.springframework.org/tags"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="header.jsp" /><body>
<jsp:include page="users_menu.jsp" />

<p>user_read_guest.jsp

	<div class="thmContentLeft">
			<b><sst:message code='person.tag' /></b><br><p>&nbsp;</p>
			<sft:label path='member.firstName'><sst:message code='firstName.label' /></sft:label><span>${member.firstName}&nbsp;</span><p>&nbsp;</p>
			<sft:label path='member.lastName'><sst:message code='lastName.label' /></sft:label><span>${member.lastName}&nbsp;</span><p>&nbsp;</p>
			<sft:label path='member.prefix'><sst:message code='prefix.label' /></sft:label><span>${member.prefix}&nbsp;</span><p>&nbsp;</p>
	</div>
</body>
</html>