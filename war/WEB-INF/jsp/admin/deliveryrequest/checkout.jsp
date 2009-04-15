<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<c:url var="actionUrl"  value="/privileged/admin/checkout.htm"/>
<form:form commandName="checkoutInfo" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="checkout" /></h3></td>
	    <td></td>
	  </tr>
	  <%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
	  <tr>
	    <td>User Id:<c:out value="${user.userId}"/><br>
	    	Username:<c:out value="${user.username}"/><br>
	    	Delivery Request Date:<fmt:formatDate value="${deliveryRequest.requestDate}" dateStyle="medium" /><br>
	    	Status:<c:out value="${deliveryRequest.status}"/></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>    	
	    	Product Instancd Id: <form:input path="instanceId" maxlength="10" size="10" />
	    	<form:hidden path="deliveryRequestId" />
	    	<form:hidden path="productId" />
	       	<form:hidden path="priority" />
	    	
			<input type="submit" value="Checkout"/>
	    </td>
	    <td></td>
	  </tr>
	  
	  
	</table>
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>