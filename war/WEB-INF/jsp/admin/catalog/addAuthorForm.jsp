<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<form:form commandName="author" enctype="multipart/form-data">
<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3><!-- Add new -->
						<fmt:message key="add.author" />
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td>First Name:</td>
					<td><form:input path="firstName" maxlength="30" size="30"/></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<%//since full name db column is 60, making lastname 29...
					//so max(firstname) + " " + max(lastname) <=60 %>
					<td><form:input path="lastName" maxlength="29" size="30"/></td>
				</tr>

				<tr>
					<td></td>
					<td>
						<input type="submit" value="Save" />
					</td>
				</tr>
			</table>
			</td>
			<td>
			</td>
		</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>