<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@page import="com.sparrow.domain.User"%>

<%@page import="com.sparrow.service.common.DateUtils"%>
<c:url var="actionUrl"  value="/privileged/admin/createuserbill.htm"/>
<%
User user1 = (User)request.getAttribute("user");
%>
<form:form commandName="payment" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="create.user.bill" /></h3></td>
	    <td></td>
	  </tr>
	  <%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
	  <tr>
	    <td colspan="2"><b>User Id: <c:out value="${user.userId}"/></b></td>
	  </tr>
	  <tr>
	    <td colspan="2"><b>User Name: <c:out value="${user.username}"/></b></td>
	  </tr>
	  <tr>
	    <td colspan="2"><b>Account Expiration Date: <%=DateUtils.getMediumDateformatter().format(user1.getExpirationDate())%></b></td>
	  </tr>
	  <tr>
	    <td colspan="2"><b>Subscription plan: <c:out value="${user.subscriptionPlan.name}"/></b></td>
	  </tr>
	  <tr>
	    <td colspan="2"><b>Subscription Fee: <c:out value="${user.subscriptionPlan.fee}"/></b></td>
	  </tr>
	  
	  <tr>
	    <td>
		  <% User user = (User)request.getAttribute("user"); 
		     //if user has no pending bill, show submit button to create a new bill
			 if (user.getPendingBill() == null) {      
	      %>
	        <input type="submit" value="Create Bill" />
	      <% } else {%>
	        <fmt:message key="has.pending.bills"></fmt:message>
	      <% } %>
	    </td>
	    <td><input type='hidden' name="userId" id="userId" value="<c:out value="${user.userId}"/>" />
	    	<input type='hidden' name="submitAction" id=""submitAction"" value="createBill" /></td>
	    
	  </tr>

	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>