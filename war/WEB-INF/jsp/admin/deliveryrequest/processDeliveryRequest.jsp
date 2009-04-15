<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
	<%@page import="java.util.Date"%>
<%@page import="com.sparrow.domain.User"%>
<table>
	  <tr>
	    <td><h3><fmt:message key="process.delivery.request" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>
	      <table>
	        <fmt:setTimeZone value="Asia/Calcutta" scope="page"/>
	        <tr><td><b>Delivery Instructions:</b></td><td class="validationError"><b><c:out value="${user.deliveryInstructions}"/></b></td></tr>
	        <tr><td></td><td><b><fmt:message key="delete.update.deliver.instruction" /></b></td></tr>
	      	<tr><td>User Id:</td><td><c:out value="${deliveryRequest.user.userId}"/></td></tr>
	      	<tr><td>Username:</td><td><c:out value="${user.username}"/></td></tr>
	      	<tr><td>Name:</td><td><c:out value="${user.firstname}"/>&nbsp;<c:out value="${user.lastname}"/></td></tr>
	      	<tr><td>Membership Start date:</td><td><fmt:formatDate value="${user.startDate}" dateStyle="medium" /></td></tr>
	      	<tr><td>Delivery Request Date:</td><td><fmt:formatDate value="${deliveryRequest.requestDate}" dateStyle="medium" /></td></tr>
	      	<tr><td>Membership Expiration Date:</td><td><fmt:formatDate value="${user.expirationDate}" dateStyle="medium" /></td></tr>
	      	<%
	      	  Date membershipExpirationWarningCompareDate = (Date)request.getAttribute("membershipExpirationWarningCompareDate");
	      	  User user = (User)request.getAttribute("user");
	      	  if (user.getExpirationDate().before(membershipExpirationWarningCompareDate)) {
	      	%>
	      	  <tr><td></td><td class="validationError"><fmt:message key="membership.about.to.expire" /></td></tr>
	      	<%
	      	  }
	      	%>
	      	<%
	      	  //if user has equalled or exceeded the books/month limit, display a message to the admin
	      	  Boolean isUserInMonthlyDeliveryRequestLimitForPlan = (Boolean)request.getAttribute("isUserInMonthlyDeliveryRequestLimitForPlan");
	      	  if (!isUserInMonthlyDeliveryRequestLimitForPlan.booleanValue()) {
	      	%>
	      	  <tr><td></td><td class="validationError"><fmt:message key="membership.user.monthly.limit.reached" /></td></tr>
	      	<%
	      	  }
	      	%>
	      	
	      </table>
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><b><fmt:message key="queue" /></b></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    <%//do not want paging here...so putting pagesize to a large number%>
	    <grid:table name="requestScope.productRequestsList"
			pagesize="1000" decorator="com.sparrow.web.deliveryrequest.DeliveryRequestDecorator">
			<grid:column property="priority" title="Priority" />
			<grid:column property="product.productId" title="Product Id"/>
			<grid:column property="product.name" title="Name"/>
			<grid:column property="product.author.fullName" title="Author" />
			<grid:column property="availableProductInstances" title="Available Instances" />
			<grid:column property="link" />
			
			<grid:setProperty name="paging.banner.placement" value="bottom" />
			
		</grid:table>
	    
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><br><br><b><fmt:message key="admin.instructions" /></b>
	    	<br><fmt:message key="process.delivery.request.instuctions" /></td>
	    <td></td>
	  </tr>
	  
	</table>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>