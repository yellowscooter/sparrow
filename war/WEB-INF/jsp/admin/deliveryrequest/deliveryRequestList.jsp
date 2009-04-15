<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
	<table>
	  <tr>
	    <td><h3><fmt:message key="deliveryRequestList" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<grid:table name="sessionScope.pendingDeliveryRequestList" pagesize="30" requestURI="/privileged/admin/deliveryrequestlist.htm"
	    				decorator="com.sparrow.web.deliveryrequest.DeliveryRequestListDecorator" >
				<grid:column property="deliveryRequestId" title="Id"/>
				<grid:column property="user.userId" title="userId" />
				<grid:column property="user.username" title="Username" />
				<grid:column property="user.firstname" title="First Name" />
				<grid:column property="user.lastname" title="Last Name" />
				<grid:column property="requestDate" title="Request Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
				<grid:column property="status" title="Status" />
				<grid:column property="requestIntransitDate" title="InTransit Date" />
				<grid:column property="requestCompleteDate" title="Complete Date" />
				<grid:column property="link"/>
				
				<grid:setProperty name="paging.banner.placement" value="bottom" />
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
	  
	  
	</table>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>