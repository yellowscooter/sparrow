<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<c:url var="actionUrl"  value="/privileged/admin/return.htm"/>
<form:form commandName="returnInfo" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="return.product" /></h3></td>
	    <td></td>
	  </tr>
	  <%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
	  <tr>
	    <td>Product Instance Id*:</td>
	    <td><form:input path="productInstanceId" maxlength="10" size="10" /></td>
	  </tr>
	  <tr>
	    <td>Comment:</td>
	    <td><form:textarea path="comment" rows="5" cols="30"/></td>
	  </tr>
	  <tr>
	    <td></td>
	    <td><input type="submit" value="Return"/></td>
	  </tr>
	  
	</table>
	<br><b><fmt:message key="admin.instructions" /></b>
	    	<br><fmt:message key="return.instuctions" />
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>