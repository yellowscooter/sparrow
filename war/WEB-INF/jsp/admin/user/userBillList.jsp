<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<%@page import="com.sparrow.domain.User"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/sparrow.js"/>' ></script>
<script type="text/javascript">
function openCreatePaymentPopup(url, paymentMethod) {
  var url = url + '?paymentMethod=' + paymentMethod + '&userId=' + document.getElementById('userId').value;
  win = window.open(url, 'createBill','width=600,height=575,toolbar=no,menubar=no,scrollbars=yes');
}
</script>
<form name="billListForm" action="<c:url value="/privileged/admin/userbilllist.htm"/>">

<table>
  <tr>
    <td><h3><fmt:message key="user.bills.list" /></h3></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="2"><b>User Id: <c:out value="${user.userId}"/></b></td>
  </tr>
  <tr>
    <td colspan="2"><b>Username: <c:out value="${user.username}"/></b></td>
  </tr>
  <tr>
    <td colspan="2">
    	<grid:table name="requestScope.userBillList" requestURI="/privileged/admin/userbilllist.htm"
    				 						id="row" decorator="com.sparrow.web.user.UserBillListDecorator">

			<grid:column property="billId" title="Bill Id"/>
    		<grid:column property="user.username" title="User Name"/>
    		<grid:column property="subscriptionPlan.name" title="Subscription Plan"/>
    		<grid:column property="amount" title="Amount (Rs)"/>
    		<grid:column property="billDate" title="Bill Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
    		<grid:column property="amount" title="Amount"/>
    		<grid:column property="description" title="Description"/>
    		<grid:column property="status" title="Status"/>
    		<grid:column property="comment" title="Comment"/>
    		<grid:column property="paymentDetails" title="Payment Details"/>
    				
			<grid:setProperty name="paging.banner.placement" value="bottom" />
		</grid:table>
    </td>
    <td></td>
  </tr>
  <tr>
    <td>
      <% User user = (User)request.getAttribute("user"); 
		 if (user.getPendingBill() != null) {      
      %>
        <input type='button' value='Process Check Payment' onClick="javascript:openCreatePaymentPopup('<c:url value="/privileged/admin/userpayment.htm"/>', 'CK');">
        <input type='button' value='Process Cash Payment' onClick="javascript:openCreatePaymentPopup('<c:url value="/privileged/admin/userpayment.htm"/>', 'CA');">
      <% } else {%>
        <fmt:message key="no.pending.bills"></fmt:message>
      <% } %>
    </td>
    <td><input type="hidden" name="userId" id="userId" value="<c:out value="${user.userId}"/>"/></td>
  </tr>
  
</table>
</form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>