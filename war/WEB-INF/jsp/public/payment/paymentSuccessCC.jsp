<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="com.sparrow.service.payment.PaymentMethodEnum"%>
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
<tr>
<td>
	<spring:message code="${status}" text=""/><br><br>
	<spring:message code="${message}" text=""/>
</td>
</tr>
<tr>
  <td>&nbsp;</td>
</tr>
</table>	



<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>