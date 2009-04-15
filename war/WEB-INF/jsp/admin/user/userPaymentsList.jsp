<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<script language="javascript" type="text/javascript" src='<c:url value="/js/sparrow.js"/>' ></script>
<script type="text/javascript">
function openUserPaymentPopup(url) {
  var value = getCheckedValue(document.searchCriteriaForm.selector);
  if(value != ''){
    var url = url + '?paymentId=' + value;
    win = window.open(url, 'userPayment','width=600,height=575,toolbar=no,menubar=no,scrollbars=yes');
  }
}
</script>

<form name="searchCriteriaForm" action="<c:url value="/privileged/admin/userpaymentslist.htm"/>">
	<table>
	  <tr>
	    <td><h3><fmt:message key="user.payments.list" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<grid:table name="requestScope.userPaymentsList" requestURI="/privileged/admin/userpaymentslist.htm"
	    				 						id="row" decorator="com.sparrow.web.user.UserPaymentListDecorator">
	    		<grid:column title="" property="radio"/>
	    		<grid:column property="user.username" title="User Name"/>
	    		<grid:column property="paymentDate" title="Payment Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
	    		<grid:column property="paymentMethod.value" title="Payment Method"/>
	    		<grid:column property="amount" title="Amount"/>
	    		<grid:column property="ccConfirmationId" title="CC Conf Num"/>
	    		<grid:column property="ccTxDate" title="CC Tran Date"/>
	    		<grid:column property="checkNumber" title="Check Number"/>
	    		<grid:column property="checkBankName" title="Check Bank"/>
	    		<grid:column property="draftNumber" title="Draft Number"/>
	    		<grid:column property="draftBankName" title="Draft Bank"/>
	    		<grid:column property="status" title="Status"/>
	    		<grid:column property="verification" title="Verified"/>
	    		<grid:column property="processedbyUser.username" title="Processed by User"/>
	    		<grid:column property="comment" title="Comment"/>
	    				
		
				<grid:setProperty name="paging.banner.placement" value="bottom" />
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><input type='button' value='Update' onClick="javascript:openUserPaymentPopup('<c:url value="/privileged/admin/userpayment.htm"/>');"></td>
	    <td></td>
	  </tr>
	  
	  
	</table>
</form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>