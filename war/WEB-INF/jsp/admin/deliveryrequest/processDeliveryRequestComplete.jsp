<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
	<table>
	  <tr>
	    <td><h3><fmt:message key="process.delivery.request" /> - <fmt:message key="process.delivery.request.complete" /></h3></td>
	    <td></td>
	  </tr>

	  <tr>
	    <td>
	      <table>
	      	<tr><td>User Id:</td><td><c:out value="${deliveryRequest.user.userId}"/></td></tr>
	      	<tr><td>Username:</td><td><c:out value="${user.username}"/></td></tr>
	      	<tr><td>Full Name:</td><td><c:out value="${user.firstname}"/>&nbsp;<c:out value="${user.lastname}"/></td></tr>
	      	<tr><td>Phone:</td><td><c:out value="${user.phone}"/></td></tr>
			<tr><td>Mobile Phone:</td><td><c:out value="${user.mobilePhone}"/></td></tr>
			<tr><td>Subscription Plan:</td><td><c:out value="${user.subscriptionPlan.name}"/></td></tr>
			<tr><td>Payment Method:</td><td><c:out value="${user.paymentMethod}"/></td></tr>
			<tr><td><b>Payment Due:</b></td><td><b><c:out value="${user.pendingBill.amount}"/></b></td></tr>
	      	<tr><td valign="top">Address:</td><td><c:out value="${user.shippingAddress.street1}"/><br>
	      							<c:out value="${user.shippingAddress.street2}"/><br>
	      							<c:out value="${user.shippingAddress.city}"/><br>
	      							<c:out value="${user.shippingAddress.state}"/><br>
	      							<c:out value="${user.shippingAddress.postalCode}"/></td></tr>
	      	<tr><td><b>Delivery Instructions:</b></td><td><b><c:out value="${user.deliveryInstructions}"/></b></td></tr>
	      	<tr><td></td><td><b><fmt:message key="delete.update.deliver.instruction" /></b></td></tr>
	      	<tr><td>Delivery Request Date:</td><td><fmt:formatDate value="${deliveryRequest.requestDate}" dateStyle="medium" /></td></tr>
	      	<tr><td>Status:</td><td><c:out value="${deliveryRequest.status}"/></td></tr>
	      </table>
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><fmt:message key="process.delivery.request.complete.instructions" /></td>
	    <td></td>
	  </tr>
	</table>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>