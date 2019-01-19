<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ taglib prefix="sft" uri="http://www.springframework.org/tags/form"%><%@ taglib prefix="sst" uri="http://www.springframework.org/tags"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><jsp:include page="header.jsp" /><body onload='document.userForm.firstName.focus();'>
<jsp:include page="menu.jsp" />

<p>user_new_guest.jsp</p>

	<sft:form method='POST' commandName='user' action='saveNew' name='userForm'>

	<div class="thmTitle">
		<div class="thmTitleButtons">
			<input value='<sst:message code='save.tag' />' class='btn' type='submit'><br>
		</div>
		<h2><sst:message code='profile.tag' /></h2>
	</div>	
		<br>
	<div class="thmContentLeft">
		<b><sst:message code='person.tag' /></b><br><p>&nbsp;</p>
			
		<sft:label path='firstName'><sst:message code='firstName.label' /></sft:label>
			<sft:input path='firstName' />
			<p><sft:errors path='firstName' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='lastName'><sst:message code='lastName.label' /></sft:label>
			<sft:input path='lastName' />
			<p><sft:errors path='lastName' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='prefix'><sst:message code='prefix.label' /></sft:label>
			<sft:input path='prefix' /><p>&nbsp;</p>
		<sft:label path='sex'><sst:message code='sex.label' /></sft:label>
			<sft:radiobutton path='sex' value='m' />&nbsp;man <sft:radiobutton path='sex' value='f' />&nbsp;vrouw
			<p><sft:errors path='sex' cssClass='alert alert-error' />&nbsp;</p><br>
		<sft:label path='dateBirth'><sst:message code='dateBirth.label' /></sft:label>
			<sft:input path='dateBirth' placeholder='Vorm: jjjj-mm-dd' />
			<p><sft:errors path='dateBirth' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='mobileNumber'><sst:message code='mobileNumber.label' /></sft:label>
			<sft:input path='mobileNumber' placeholder='Bijv.: 0612345678' />
			<p><sft:errors path='mobileNumber' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='phoneNumber'><sst:message code='phoneNumber.label' /></sft:label>
			<sft:input path='phoneNumber' placeholder='Bijv.: 0558765432' />
			<p><sft:errors path='phoneNumber' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='mailAddress'><sst:message code='mailAddress.label' /></sft:label>
			<sft:input path='mailAddress' placeholder='Bijv.: iemand@domein.nl' />
			<p><sft:errors path='mailAddress' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='alias'><sst:message code='alias.label' /></sft:label>
			<sft:input path='userName' disabled='true' placeholder='Automatisch vastgesteld' />
			<p><sft:errors path='userName' cssClass='alert alert-error' />&nbsp;</p>
	</div>

	<div class="thmContentRight">
		<b><sst:message code='address.tag' /></b><br><p>&nbsp;</p>
			
		<sft:label path='address.streetName'><sst:message code='streetName.label' /></sft:label>
			<sft:input path='address.streetName' />
			<p><sft:errors path='address.streetName' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='address.houseNumber'><sst:message code='houseNumber.label' /></sft:label>
			<sft:input path='address.houseNumber' />
			<p><sft:errors path='address.houseNumber' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='address.addressCode'><sst:message code='addressCode.label' /></sft:label>
			<sft:input path='address.addressCode' placeholder='Bijv.: 9999 XX' />
			<p><sft:errors path='address.addressCode' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='address.city'><sst:message code='city.label' /></sft:label>
			<sft:input path='address.city'  />
			<p><sft:errors path='address.city' cssClass='alert alert-error' />&nbsp;</p>
		<sft:label path='address.country'><sst:message code='country.label' /></sft:label>
			<sft:select path='address.country' id="land"><c:forEach items='${countries}' var='country'><c:choose><c:when test="${country.countryCode==user.address.country}">
				<option value='${user.address.country}' selected>${country.countryName}</option></c:when><c:otherwise>
				<option value='${country.countryCode}'>${country.countryName}</option></c:otherwise></c:choose></c:forEach></sft:select>
			<p><sft:errors path='address.country' cssClass='alert alert-error' />&nbsp;</p>
		<div class="thmContentRightUnder">
			<b><sst:message code='nofa.tag' /></b><br><br><br><br>
			
			<sft:label path='alias'><sst:message code='alias.label' /></sft:label>
				<sft:input path='alias' />
				<p><sft:errors path='alias' cssClass='alert alert-error' />&nbsp;</p>
			<sft:label path='memberCode'><sst:message code='memberCode.label' /></sft:label>
				<sft:input path='memberCode' disabled='true' placeholder='Automatisch vastgesteld' />
				<p><sft:errors path='memberCode' cssClass='alert alert-error' />&nbsp;</p>
			</div>	
		</div>

	</sft:form>
</body>
</html>