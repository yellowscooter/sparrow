<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<%@page import="com.sparrow.web.WebConstants"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/sparrow.js"/>' ></script>
<script type="text/javascript">
function openPopup(url) {
  //var value = getCheckedValue(document.getElementById('selector'));does not work
  var value = getCheckedValue(document.searchCriteriaForm.selector);
  
  if(value != ''){
    var url = url + 'userId=' + value;
    win = window.open(url, 'popup1','width=900,height=575,toolbar=no,menubar=no,scrollbars=yes');
  }
}
</script>
<%
	String status = (String)request.getAttribute("status");
	String searchBy = (String)request.getAttribute("searchBy");
%>
<form name="searchCriteriaForm" action="<c:url value="/privileged/admin/userlist.htm"/>">
	<table>
	  <tr>
	    <td><h3><fmt:message key="userList" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><input type="radio" name="status" value="A" checked />Active
	        <input type="radio" name="status" value="I" <%=status.equals("I")?"checked":"" %>/>InActive
	        <input type="radio" name="status" value="S" <%=status.equals("S")?"checked":"" %>/>Suspended
	        <input type="radio" name="status" value="T" <%=status.equals("T")?"checked":"" %>/>Terminated
	        <b>Search by</b> 
	        <select name="searchBy">
			<option value="<%=WebConstants.SEARCH_BY_LASTNAME%>" <%=searchBy.equals(WebConstants.SEARCH_BY_LASTNAME)?"SELECTED":"" %> >Last Name</option>
			<option value="<%=WebConstants.SEARCH_BY_USERID%>" <%=searchBy.equals(WebConstants.SEARCH_BY_USERID)?"SELECTED":"" %>>User Id</option>
			<option value="<%=WebConstants.SEARCH_BY_USERNAME%>" <%=searchBy.equals(WebConstants.SEARCH_BY_USERNAME)?"SELECTED":"" %>>Username</option>
			<option value="<%=WebConstants.SEARCH_BY_PHONE%>" <%=searchBy.equals(WebConstants.SEARCH_BY_PHONE)?"SELECTED":"" %>>Phone</option>
			<option value="<%=WebConstants.SEARCH_BY_MOBILEPHONE%>" <%=searchBy.equals(WebConstants.SEARCH_BY_MOBILEPHONE)?"SELECTED":"" %>>Mobile Phone</option>
			</select>
	        
	        :&nbsp;<input type="text" name="searchCriteria" size="50" maxlength="50" 
	    											value="<c:out value="${searchCriteria}"/>"/>
	    					&nbsp;<input type="submit" value="Search" />
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<grid:table name="requestScope.userList" requestURI="/privileged/admin/userlist.htm"
	    				decorator="com.sparrow.web.user.UserListDecorator" id="row">
	    		<grid:column title=""><input type="radio" name="selector" id="selector" value="<c:out value="${row.userId}"/>" /></grid:column>
	    		<grid:column property="userId" title="UserId"/>		
				<grid:column property="firstname" title="First Name"/>
				<grid:column property="lastname" title="Last Name"/>
				<grid:column property="username" title="Username"/>
				<grid:column property="gender" title="M/F"/>
				<grid:column property="ageGroup" title="Age"/>
				<grid:column property="accountEnabled" title="Enabled"/>
				<grid:column property="accountCreateDate" title="Create Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
				<grid:column property="startDate" title="Mem. Start Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
				<grid:column property="expirationDate" title="Mem. Exp Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
				<grid:column property="phone" title="Home Phone"/>
				<grid:column property="mobilePhone" title="Mobile Phone"/>
				<grid:column property="address" title="Address"/>
				<grid:column property="city.city" title="Location"/>
				<grid:column property="subscriptionPlan.name" title="Sub. Plan"/>
				<grid:column property="paymentMethod" title="Payment Method"/>
				<grid:column property="detailsLink" title="Details"/>
				
				<grid:setProperty name="paging.banner.placement" value="bottom" />
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td><input type='button' value='User Bills' onClick="javascript:openPopup('<c:url value="/privileged/admin/userbilllist.htm?"/>');" />
	    	<input type='button' value='Create User Bill' onClick="javascript:openPopup('<c:url value="/privileged/admin/createuserbill.htm?"/>');" />
	    	<input type='button' value='Mark User Inactive' onClick="javascript:openPopup('<c:url value="/privileged/admin/updateuserstate.htm?action=I&"/>');" />
	    	<input type='button' value='Mark User Suspended' onClick="javascript:openPopup('<c:url value="/privileged/admin/updateuserstate.htm?action=S&"/>');" />
	    	<input type='button' value='Mark User Terminated' onClick="javascript:openPopup('<c:url value="/privileged/admin/updateuserstate.htm?action=T&"/>');" />
	    	<input type='button' value='Mark User Active' onClick="javascript:openPopup('<c:url value="/privileged/admin/updateuserstate.htm?action=A&"/>');" />
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">Note: Row in <span class="warning">RED</span> background indicates a NEW registration.<br>
	    				Row in <span class="expiredAccount">ORANGE</span> background indicates that an active user's membership is about to expire or has expired.<br>
	    				List is sorted by UserId</td>
	  </tr>
	  	  
	  
	</table>
</form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>