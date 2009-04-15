<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>

<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>

<%@page import="com.sparrow.web.WebConstants"%>
<tr>
<td colspan="2" valign="top">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
<td colspan="2" valign="top" align="left"><h1><fmt:message key="user.my.account" /></h1></td>
</tr>
<tr>
<td colspan="2" valign="top" align="left"><spring:message code="${message}" text=""/></td>
</tr>
<tr>
<td valign="top">
	<table width="300" align="left" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td colspan="3" valign="top" align="left"><h2>Membership Information</h2></td>
	</tr>
	<tr>
	<td><img src="<c:url value="/images/user_left1.gif"/>"></td>
	<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
	<td><img src="<c:url value="/images/user_right1.gif"/>"></td>
	</tr>
	<tr>
	<td class="TDroundTable">&nbsp;</td>
	<td class="TDroundTable">
	    <fmt:setTimeZone value="Asia/Calcutta" scope="page"/>
		<c:out value="${user.fullname}"/><br><br>
		<c:out value="${user.shippingAddress.street1}"/><br>
		<c:out value="${user.shippingAddress.street2}"/><br>
		<c:out value="${user.shippingAddress.city}"/><br>
		<c:out value="${user.shippingAddress.state}"/>-<c:out value="${user.shippingAddress.postalCode}"/><br><br>
		Gender:&nbsp;<c:out value="${user.genderDescription}"/><br>
		Age Group:&nbsp;<c:out value="${user.ageGroup}"/><br>
		Phone No.:&nbsp;<c:out value="${user.phone}"/><br>
		Mobile Phone No.:&nbsp;<c:out value="${user.mobilePhone}"/><br>
		Member Since:&nbsp;<fmt:formatDate value="${user.startDate}" dateStyle="medium"  /><br>
		Membership end date:&nbsp;<fmt:formatDate value="${user.expirationDate}" dateStyle="medium" />
	</td>
	<td class="TDroundTable">&nbsp;</td>
	</tr>
	<tr>
	<td ><img src="<c:url value="/images/user_left2.gif"/>"></td>
	<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
	<td ><img src="<c:url value="/images/user_right2.gif"/>"></td>
	</tr>
	</table><br clear="all"><br />	
</td>

<td valign="top">
	<table width="300" align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td colspan="3" valign="top" align="left"><h2>Edit Profile</h2></td>
	</tr>
	<tr>
	<td><img src="<c:url value="/images/user_left1.gif"/>"></td>
	<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
	<td><img src="<c:url value="/images/user_right1.gif"/>"></td>
	</tr>
	<tr>
	<td class="TDroundTable">&nbsp;</td>
	<td class="TDroundTable">
		<A href="<c:url value="/edituserprofile.htm?tab=4"/>"><fmt:message key="user.edit.profile" /></A><br>
		<A href="<c:url value="/changepassword.htm?tab=4"/>"><fmt:message key="user.change.password" /></A><br>
		<A href="<c:url value="/selectsubscriptionplan.htm?tab=4&action=update"/>"><fmt:message key="user.subscrition.plan" /></A><br>
		<A href="<c:url value="/paymentinfo.htm?tab=4&action=update"/>"><fmt:message key="user.payment.info" /></A>
	</td>
	<td class="TDroundTable">&nbsp;</td>
	</tr>
	<tr>
	<td ><img src="<c:url value="/images/user_left2.gif"/>"></td>
	<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
	<td ><img src="<c:url value="/images/user_right2.gif"/>"></td>
	</tr>
	</table><br clear="all"><br />	
</td>
</tr>
</table><br clear="all"><br />	
</td>
<td valign="top"><%//GoogleAdsenseHere%><br><br>
</td>
</tr>
<!--########## Main Content table End ##########-->

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>