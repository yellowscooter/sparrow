<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<%@page import="com.sparrow.domain.User"%>

<%
  User user = (User)request.getAttribute("user");
%>
<c:url var="actionUrl"  value="/privileged/admin/userdetails.htm"/>
<fmt:setTimeZone value="Asia/Calcutta" scope="page"/>
<table border=0>
		<tr>
			<td>
			<table >
				<tr>
					<td>
					<h2>User Details</h2>
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td>
				    	<b>UserId: </b><c:out value="${user.userId}"/>&nbsp;
				    	<b>Username: </b><c:out value="${user.username}"/>&nbsp;
				    	<b>Name: </b><c:out value="${user.firstname}"/>&nbsp;<c:out value="${user.lastname}"/><br>
				    	<b>Membership Start Date: </b><fmt:formatDate value="${user.startDate}" dateStyle="medium" /><br>
				    	<b>Membership Expiration Date: </b><fmt:formatDate value="${user.expirationDate}" dateStyle="medium" /><br>
				    	<b>Current Membership Month StartDate: </b><fmt:formatDate value="${user.currentUserMembershipMonthStartDate}" dateStyle="medium" /><br>
				    	<b>Current Membership Month EndDate: </b><fmt:formatDate value="${user.currentUserMembershipMonthEndDate}" dateStyle="medium" />&nbsp;
					</td>
					<td></td>
				</tr>
			</table>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td colspan="2"><hr></td>
		</tr>
		<tr>
			<td>
				<h3>Notes</h3>
			</td>
			<td></td>
		</tr>
		
		<form:form action="${actionUrl}">
		<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
		<tr>
	    <td colspan="2">
	    	<grid:table name="requestScope.notesList" pagesize="100">
	    	  <grid:column property="noteDate" title="Date Added" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
	    	  <grid:column property="note" title="Note"/>   
	    	  <grid:column property="addedByUser.username" title="Added by User"/>
	    	  
	    	  <grid:setProperty name="paging.banner.placement" value="bottom" />		
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
		<tr>
			<td>
			  <textarea name="note" cols="80" rows="5" ></textarea>    
			</td>
			<td></td>
		</tr>
		<tr>
			<td align="center">
			  <input type="hidden" name="submitAction" value="addNote" />
			  <input type="submit" value="Add Note"/>
			</td>
			<td></td>
		</tr>
		
		</form:form>

				<tr>
			<td colspan="2"><hr></td>
		</tr>
		<tr>
			<td>
				<h3>Delivery Instructions</h3>
			</td>
			<td></td>
		</tr>
		
		<form:form action="${actionUrl}">
		<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
		<tr>
			<td>
			  <textarea name="deliveryInstructions" cols="80" rows="2" ><c:out value="${user.deliveryInstructions}"/></textarea>    
			</td>
			<td></td>
		</tr>
		<tr>
			<td align="center">
			  <input type="hidden" name="submitAction" value="addUpdateDelInstr"/>
			  <input type="submit" value="Add / Update Delivery Instruction" onclick="javascript:return confirm('Do you want to Add/Update Delivery Instructions?');"/>
			</td>
			<td></td>
		</tr>
		
		</form:form>
				
		<tr>
			<td colspan="2"><hr></td>
		</tr>
		<tr>
			<td>
				<h3><fmt:message key="at.home" /></h3>
			</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2">
				<grid:table name="requestScope.userProductsList"
							pagesize="100"
							cellpadding="2" cellspacing="1" >
							<grid:setProperty name="paging.banner.all_items_found" value="" />
				        	<grid:setProperty name="paging.banner.onepage" value="" />
				        	<grid:setProperty name="paging.banner.one_item_found" value="" />
							<grid:setProperty name="paging.banner.placement" value="bottom" />
							<grid:setProperty name="basic.msg.empty_list" value="There are no books in your At Home list currently." />
							<grid:column property="productInstance.product.name" title="Name" headerClass="queueHeader" style="width: 300px;"/>
							<grid:column property="productInstance.product.author.fullName" title="Author" headerClass="queueHeader" style="width: 150px;"/>
							<grid:column property="productInstance.productInstanceId" title="Instance Id" headerClass="queueHeader"/>
							<grid:column property="checkoutDate" title="Checkout Date" 
											decorator="com.sparrow.web.common.MediumDateFormatDecorator" headerClass="queueHeader"/>
				</grid:table>
			
			</td>
			
		</tr>
		<form:form action="${actionUrl}">
		<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
		<tr>
			<td>
			  <input type="hidden" name="submitAction" value="newDeliveryRequest" />
			  <input type="submit" value="New Delivery Request" onclick="javascript:return confirm('Do you want to send a Pickup Request for user?');"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="${message}" text=""/>
			</td>
			<td></td>
		</tr>
		
		</form:form>
		
		<tr>
			<td colspan="2"><hr></td>
		</tr>
		<tr>
			<td>
				<h3><fmt:message key="queue" /></h3>
			</td>
			<td></td>
		</tr>
		<tr>
		<%//do not want paging here...so putting pagesize to a large number%>
			<td colspan="2">
				<grid:table name="requestScope.productRequestsList"
					pagesize="1000" decorator="com.sparrow.web.deliveryrequest.DeliveryRequestDecorator">
					<grid:column property="priority" title="Priority" />
					<grid:column property="product.productId" title="Product Id"/>
					<grid:column property="product.name" title="Name"/>
					<grid:column property="product.author.fullName" title="Author" />
					<grid:column property="addDate" title="Date Added" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
					<grid:column property="availableProductInstances" title="Available Instances" />
					<grid:setProperty name="paging.banner.placement" value="bottom" />
				</grid:table>
			
			</td>
		</tr>
		<form:form action="${actionUrl}">
		<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
		<tr>
			<td>
			  To add a Book to User's Bookshelf, Enter Product Id of the Book: <input type="text" name="productId" size="5" maxlength="5"/>
			  <input type="hidden" name="submitAction" value="addToQueue" />
			  <input type="submit" value="Add to Queue"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="${messageAddToQ}" text=""/>
			</td>
			<td></td>
		</tr>
		
		</form:form>
		
		<form:form action="${actionUrl}">
		<input type="hidden" name="userId" value="<%=user.getUserId()%>"/>
		<tr>
			<td>
			  To remove a Book to User's Bookshelf, Enter Priority of the Book: <input type="text" name="priorityToRemove" size="5" maxlength="5"/>
			  <input type="hidden" name="submitAction" value="removeFromQueue" />
			  <input type="submit" value="Remove from Queue"/>
			</td>
			<td></td>
		</tr>
		
		</form:form>
		
		<tr>
			<td colspan="2"><hr></td>
		</tr>
		<tr>
			<td>
				<h3>User Delivery Requests List (last 20 shown)</h3>
			</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2">
				<grid:table name="requestScope.deliveryRequestsList"
					pagesize="100" >
					<grid:column property="deliveryRequestId" title="DeliveryRequestId" />
					<grid:column property="requestDate" title="Request Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
					<grid:column property="status" title="Status"/>
					<grid:column property="requestCompleteDate" title="Request Complete Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
					<grid:column property="requestAppliedDate" title="Request Applied Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
					
					<grid:setProperty name="paging.banner.placement" value="bottom" />
					
				</grid:table>
			
			</td>
			
		</tr>
		
	
</table>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>
