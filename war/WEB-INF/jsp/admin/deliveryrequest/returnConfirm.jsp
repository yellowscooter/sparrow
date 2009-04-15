<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<c:url var="actionUrl"  value="/privileged/admin/return.htm"/>
<form:form commandName="returnInfo" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="return.product.confirm" /></h3></td>
	    <td></td>
	  </tr>
	  <%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
	  <tr>
	    <td>Product Name: </td>
	    <td><c:out value="${product.name}"></c:out></td>
	  </tr>
	  <tr>
	    <td>Author: </td>
	    <td><c:out value="${product.author.fullName}"></c:out></td>
	  </tr>
	  <tr>
	    <td>Product Instance Id: </td>
	    <td><c:out value="${returnInfo.productInstanceId}"></c:out></td>
	  </tr>
	  <!-- <tr>
	    <td>User Returning Book:</td>
	    <td><c:out value="${user.firstname}" />&nbsp;<c:out value="${user.lastname}" />,&nbsp;<c:out value="${user.username}" /></td>
	  </tr> -->
	  <tr>
	    <td>&nbsp;</td>
	    <td></td>
	  </tr>
	  <tr>
	    <td></td>
	    <td><input type="submit" value="Return" name="_finish"/>&nbsp;&nbsp;
			&nbsp;&nbsp;<input type="submit" value="Cancel" name="_cancel"/>
	    </td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td></td>
	  </tr>
	  
	</table>
	<b><fmt:message key="admin.instructions" /></b>
	    	<br><fmt:message key="return.confirm.instuctions" />
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>