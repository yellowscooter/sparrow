<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<form:form commandName="user">
<tr>
<td colspan="2" valign="top" align="right">
	<!--########## Search Start ##########-->
	<table width="731" align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td valign="top" align="left"><img src="<c:url value="/images/left_curve_search.gif"/>"></td>
	<td width="720" valign="middle" align="right"  class="text" background="<c:url value="/images/shade_search.gif"/>"><img src="<c:url value="/images/shade_search.gif"/>" width="720" height="1" border="0"><br clear="all"></td>
	<td valign="top" align="right"><img src="<c:url value="/images/right_curve_search.gif"/>"></td>
	</tr>
	</table>
	<!--########## Search End ##########-->
</td>
</tr>

<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
<td colspan="3" valign="top" align="left"><h1><fmt:message key="user.edit.profile" /></h1></td>
</tr>
<tr>
<td colspan="3" valign="top" align="left">
<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
</td>
</tr>
<tr>
<td><img src="<c:url value="/images/user_left1.gif"/>"></td>
<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="680" height="1"></td>
<td><img src="<c:url value="/images/user_right1.gif"/>"></td>
</tr>
<tr>
<td class="TDroundTable">&nbsp;</td>
<td class="TDroundTable">

<table width="600" align="center" cellpadding="3" cellspacing="0" border="0">
<%@ include file="/WEB-INF/jsp/public/user/addEditUserInclude.jsp"%>
<tr>
<td colspan="2" align="center"><input type="submit" value="Update" class="button"/>
					&nbsp;&nbsp;<input type="button" value="Cancel" class="button"
											onclick="javascript:history.back();"/></td>
</tr>
</table><br clear="all">

</td>
<td class="TDroundTable">&nbsp;</td>
</tr>
<tr>
<td ><img src="<c:url value="/images/user_left2.gif"/>"></td>
<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="680" height="1"></td>
<td ><img src="<c:url value="/images/user_right2.gif"/>"></td>
</tr>
</table><br clear="all"><br />	
<!--########## Main Content table End ##########-->

</form:form>

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>