<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="com.sparrow.service.payment.PaymentMethodEnum"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<%@page import="com.sparrow.domain.User"%>
<tr>
<td colspan="2" valign="top" align="right">
	<!--########## Search Start ##########-->
	<table width="731" align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td valign="top" align="left"><img src="<c:url value="/images/left_curve_search.gif"/>"></td>
	<td width="720" valign="middle" align="right"  class=".text" background="<c:url value="/images/shade_search.gif"/>"><img src="<c:url value="/images/shade_search.gif"/>" width="720" height="1" border="0"><br clear="all"></td>
	<td valign="top" align="right"><img src="<c:url value="/images/right_curve_search.gif"/>"></td>
	</tr>
	</table>
	<!--########## Search End ##########-->
</td>
</tr>

<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" valign="top" align="left" border="0">
<tr>
<td><h1></h1></td>
</tr>
<% 
    User user = GeneralUtils.getCurrentUserFromTLS();
    if (user.getPendingBill() != null) {
%>
<tr>
	<td>Payment Due:<fmt:message key="currency.symbol" />&nbsp;<%=user.getPendingBill().getAmount()%></td>
</tr>
<% } %>
<tr>
<td>
	<c:if test="${paymentMethod=='CK'}" >
	<fmt:message key="check.payment.received.success"></fmt:message><br>
	<fmt:message key="user.registration.completion"></fmt:message>
	</c:if>
	<c:if test="${paymentMethod=='CA'}" >
	<fmt:message key="cash.payment.received.success"></fmt:message><br>
	<fmt:message key="user.registration.completion"></fmt:message>
	</c:if>
	<br><br>
</td>
</tr>
</table>	



<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>