<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/prototype.js"/>'></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/ajaxLogin.js"/>' ></script>
<script type="text/javascript">
	function pickup()
	{
		url='ajaxnewdeliveryrequest.htm';

		$('pickup_msg').update('<b>Sending pickup request...</b>');
		
		new Ajax.Request(
			url, 
			{
				method: 'get', 
				onComplete: showResponse
				
			});
	}
	
	function showResponse(originalRequest)
	{
		$('pickup_msg').update(originalRequest.responseText);
	}
	
  	
  	
</script>

<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>
<!--  Putting these styles here since we do not want all grids to inherit them -->
<style>
<!--
.odd {
	background-color: #CCCCCC;
}
.even {
	background-color: #A6A6FF;
}
-->
</style>

<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>

<tr>
<td colspan="2">


<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="5" cellspacing="1" border="0">
<tr>
<td valign="top" align="left" class="text"><h1><fmt:message key="at.home" /></h1></td>
</tr>
<tr>
<td valign="top" align="left">
<%//do not want paging here...so putting pagesize to a large number%>
<grid:table name="requestScope.userProductsList"
			pagesize="1000"
			cellpadding="2" cellspacing="1" >
			<grid:setProperty name="paging.banner.all_items_found" value="" />
        	<grid:setProperty name="paging.banner.onepage" value="" />
        	<grid:setProperty name="paging.banner.one_item_found" value="" />
			<grid:setProperty name="paging.banner.placement" value="bottom" />
			<grid:setProperty name="basic.msg.empty_list" value="There are no books in your At Home list currently." />
			<grid:column property="productInstance.product.name" title="Name" headerClass="queueHeader" style="width: 300px;"/>
			<grid:column property="productInstance.product.author.fullName" title="Author" headerClass="queueHeader" style="width: 150px;"/>
			<grid:column property="checkoutDate" title="Checkout Date" 
							decorator="com.sparrow.web.common.MediumDateFormatDecorator" headerClass="queueHeader"/>
</grid:table>
</td>
</tr>
<tr>
<td><h1></h1></td>
</tr>

<%
  Set userProductsList = (Set)request.getAttribute("userProductsList");
  if (userProductsList.size() > 0) {
%>
<tr>
  <td><fmt:message key="queue.return.instruction" /></td>
</tr>
<% } else { %>
<tr>
  <td><fmt:message key="queue.return.instruction.noathome" /></td>
</tr>
<% } %>
<tr>
<td valign="top" align="left"><input type="button" name="pickup_btn" value="Ready for Pickup" onclick="javascript:pickup();" /></td>
</tr>
<tr>
<td valign="top" align="left"><table><tr><td><div id='pickup_msg' ></div></td></tr></table></td>
</tr>

<tr>
<td valign="top" align="left" class="text"><h1><fmt:message key="queue" /></h1></td>
</tr>
<tr>
<td valign="top" align="left">
<%//do not want paging here...so putting pagesize to a large number%>
<grid:table name="requestScope.productRequestsList"
			pagesize="1000" decorator="com.sparrow.web.queue.QueueDecorator"
			cellpadding="2" cellspacing="1" >
			<grid:setProperty name="paging.banner.all_items_found" value="" />
        	<grid:setProperty name="paging.banner.onepage" value="" />
        	<grid:setProperty name="paging.banner.one_item_found" value="" />
			<grid:setProperty name="paging.banner.placement" value="bottom" />
			<grid:column property="priority" title="Priority" headerClass="queueHeader" style="text-align:center;"/>
			<grid:column property="product.name" title="Name" headerClass="queueHeader" style="width: 300px;"/>
			<grid:column property="product.author.fullName" title="Author" headerClass="queueHeader" style="width: 150px;"/>
			<grid:column property="addDate" title="Date Added" headerClass="queueHeader" style="width: 80px;" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
			<grid:column property="actions" title="" headerClass="queueHeader"/>
			<grid:setProperty name="basic.msg.empty_list" value="There are no books in your Bookshelf. Please browse the catalogue and add books you want to read to your Bookshelf." />
			
			
</grid:table>
</td>
</tr>
</table><br clear="all"><br />	
<!--########## Main Content table End ##########-->



<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>
