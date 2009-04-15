<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%
  //putting the enum directly in jstl tag is not working...so putting the value in 
  //value to compare against in page context
  pageContext.setAttribute("check", com.sparrow.service.payment.PaymentMethodEnum.CHECK.getValue());
  pageContext.setAttribute("draft", com.sparrow.service.payment.PaymentMethodEnum.BANK_DRAFT.getValue());
  pageContext.setAttribute("cash", com.sparrow.service.payment.PaymentMethodEnum.CASH.getValue());
  pageContext.setAttribute("creditCard", com.sparrow.service.payment.PaymentMethodEnum.E_PAYMENT.getValue());
%>
<script type="text/javascript">
function submitForm() {
  var paymentForm = getCheckedValue(document.getElementById('payment'));
  window.close();
}
</script>

<c:url var="actionUrl"  value="/privileged/admin/userpayment.htm"/>
<form:form commandName="payment" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="user.payment" /></h3></td>
	    <td></td>
	  </tr>
	  <%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
	  <tr>
	    <td colspan="2"><b>User Id: <c:out value="${user.userId}"/></b></td>
	  </tr>
	  <tr>
	    <td>Payment Method</td>
	    <td><c:out value="${payment.paymentMethod.value}"/></td>
	  </tr>
	  <tr>
	    <td>Amount</td>
	    <td><c:out value="${user.pendingBill.amount}"/></td>
	  </tr>
	  <c:if test="${payment.paymentMethod.value == check}">
	    <tr>
	      <td>Check Number</td>
	      <td><form:input path="checkNumber" size="20" maxlength="20" /></td>
	    </tr>
	    <tr>
	      <td>Bank Name</td>
	      <td><form:input path="checkBankName" size="30" maxlength="30" /></td>
	    </tr>
	  </c:if>
	  
	  <c:if test="${payment.paymentMethod.value == draft}">
	    <tr>
	      <td>Draft Number</td>
	      <td><form:input path="draftNumber" size="20" maxlength="20" /></td>
	    </tr>
	    <tr>
	      <td>Bank Name</td>
	      <td><form:input path="draftBankName" size="30" maxlength="30" /></td>
	    </tr>
	  </c:if>
	  
	  <%//cc information is system generated, so readonly  %>
	  <c:if test="${payment.paymentMethod.value == creditCard}">
	    <tr>
	      <td>cc Confirmation Id</td>
	      <td><c:out value="${payment.ccConfirmationId}"/></td>
	    </tr>
	    <tr>
	      <td>cc Tx Date</td>
	      <td><c:out value="${payment.ccTxDate}"/></td>
	    </tr>
	  </c:if>
	  
	  <tr>
	    <td>Comment</td>
	    <td><form:textarea path="comment" cols="30" rows="10" /></td>
	  </tr>
	 
	  <tr>
	    <td><input type='hidden' name="userId" id="userId" value="<c:out value="${user.userId}"/>" />
	    	<input type='hidden' name="status" id="status" value="Y"/> </td>
	    <td><input type="submit" value="Add Payment" onclick="javascript:submitForm();"/></td>
	  </tr>

	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>