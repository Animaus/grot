<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/CustomTags" prefix="ct"%><%@taglib uri = "http://www.springframework.org/tags/form" prefix = "sft"%>
<jsp:include page="../header.jsp" />
<div align="center">
	<a href="/">${LNK_USR_HOME}</a><c:choose><c:when test='${user== null}'>&nbsp;|&nbsp;<a href="/user/login">${LNK_USR_LOGIN}</a></c:when><c:otherwise>&nbsp;|&nbsp;<a href="/user/logout">${LOGIN_OUT}</a></c:otherwise></c:choose>&nbsp;|&nbsp;<a href="/user">${LNK_USR_MEMBERS}</a>
</div>
<div align="center">${ct: today()}</div>
<div align="center"><c:choose><c:when test='${user== null}'>${LOGIN_NON}</c:when><c:otherwise>${LOGIN_YES} ${user.firstName} ${user.prefix} ${user.lastName}</c:otherwise></c:choose></div>

<p align="center">&nbsp;</p>

<p align="center">${DESCRIPTION}</p>

<div align="center">
	<table style="width: 500px;">
		<tr>
			<td>
				<p align="center">
					<a href="start">${VAL_START}</a><br />
					<a href="dao">${VAL_DAO}</a><br />
					<a href="properties">${VAL_PROPERTIES}</a><br />
					<a href="repository">${VAL_REPOSITORY}</a><br />
					<a href="saveUser">${VAL_SAVEUSER}</a><br />
					<a href="loginValid">${VAL_LOGIN_VALID}</a><br />
					<a href="loginInvalid">${VAL_LOGIN_INVALID}</a><br />
					<a href="denied">Access denied</a><br />
					<a href="granted">Access granted</a><br />
					<a href="student">Validation</a><br />
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<h4>${student.className}</h4>
				<sft:form method = "POST" action = "student" modelAttribute = "student">
				<sft:errors path = "*" class="alert alert-error" element = "div" />
					<table>
						<tr>
							<td><sft:label path = "name">Name</sft:label></td>
							<td><sft:input path = "name" /></td>
							<td><sft:errors path = "name" class="alert alert-error" /></td>
						</tr>
						<tr>
							<td><sft:label path = "age">Age</sft:label></td>
							<td><sft:input path = "age" /></td>
							<td><sft:errors path = "age" class="alert alert-error" /></td>
						</tr>
						<tr>
							<td><sft:label path = "id">id</sft:label></td>
							<td><sft:input path = "id" /></td>
						</tr>
						<tr>
							<td colspan = "2">
								<input type = "submit" value = "Submit"/>
							</td>
						</tr>
					</table>  
				</sft:form>
			</td>
		</tr>
	</table>
</div>

<jsp:include page="../footer.jsp" />