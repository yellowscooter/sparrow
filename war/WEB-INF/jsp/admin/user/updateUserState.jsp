<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@page import="com.sparrow.domain.User"%>

<%
  User user = (User)request.getAttribute("user");
  String action = (String)request.getAttribute("action");
%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sparrow.domain.Category"%>
<%@page import="com.sparrow.dao.user.UserStatusEnum"%>
<c:url var="actionUrl"  value="/privileged/admin/updateuserstate.htm"/>
<form:form action="${actionUrl}">
<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3>
					<%
							String label = null;
							if (action.equals(UserStatusEnum.INACTIVE.getValue())) {
							  label = "Mark user as Inactive";
							} else if (action.equals(UserStatusEnum.TERMINATED.getValue())) {
							  label = "Terminate User Account";
							} else if (action.equals(UserStatusEnum.SUSPENDED.getValue())) {
							  label = "Suspend User Account";
							} else if (action.equals(UserStatusEnum.ACTIVE.getValue())) {
							  label = "Mark User as Active";
							}
					%>
						<%=label%>
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td>
									    
				    	<b>UserId: </b><c:out value="${user.userId}"/><br>
				    	<b>Username: </b><c:out value="${user.username}"/><br>
				    	<b>Name: </b><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/><br>
					    										
					</td>
					<td></td>
				</tr>
			</table>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td align="center">
			  <input type="hidden" name="submitAction" value="<%=action%>" />
			  <input type="submit" value="<%=label%>"/>
			</td>
			<td></td>
		</tr>
</table>
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>