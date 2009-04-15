<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<h3>Product</h3>
<ul>
  <authz:authorize ifAnyGranted="ROLE_CATALOG,ROLE_ADMIN">
    <li><A href="<c:url value="/privileged/admin/addauthor.htm"/>"><fmt:message key="add.author"/></A></li>
    <li><A href="<c:url value="/privileged/admin/addbook.htm"/>"><fmt:message key="addProduct"/></A></li>
    <li><A href="<c:url value="/privileged/admin/productlist.htm"/>"><fmt:message key="productList"/></A></li>
  </authz:authorize>
  <authz:authorize ifAllGranted="ROLE_ADMIN">
    <li><A href="<c:url value="/privileged/admin/homeproductlist.htm"/>"><fmt:message key="homeProductList"/></A></li>
    <li><A href="<c:url value="/privileged/admin/loadproducts.htm"/>"><fmt:message key="load.products"/></A></li>
  </authz:authorize>
</ul>
<authz:authorize ifAllGranted="ROLE_ADMIN">
<h3>User</h3>
<ul>
  <li><A href="<c:url value="/privileged/admin/userlist.htm"/>"><fmt:message key="userList"/></A></li>
  <li><A href="<c:url value="/privileged/admin/deliveryrequestlist.htm"/>"><fmt:message key="deliveryRequestList"/></A></li>
  <li><A href="<c:url value="/privileged/admin/return.htm"/>"><fmt:message key="return.product"/></A></li>
</ul>
<h3>Reports</h3>
<ul>
  <li><A href="<c:url value="/privileged/admin/reports.htm?reportCode=expiredAccounts"/>">Expired User Accounts</A></li>
  <li><A href="<c:url value="/privileged/admin/reports.htm?reportCode=booksMostInDemand"/>">Books Most in Demand</A></li>
  <li><A href="<c:url value="/privileged/admin/reports.htm?reportCode=userSearches"/>">User Searches</A></li>
  <li><A href="<c:url value="/privileged/admin/reports.htm?reportCode=userSearchesCount"/>">User Searches Count</A></li>
</ul>
</authz:authorize>

<authz:authorize ifAllGranted="ROLE_ADMIN">
<h3>Statistics</h3>
<%
	Runtime runtime = Runtime.getRuntime();
%>
<table>
  <tr>
    <td>Max Memory</td>
    <td><%=runtime.maxMemory()%> (the maximum amount of memory that the virtual machine will attempt to use, measured in bytes)</td>
  </tr>
  <tr>
    <td>Total Memory</td>
    <td><%=runtime.totalMemory()%> (the total amount of memory currently available for current and future objects, measured in bytes.)</td>
  </tr>
  <tr>
    <td>Free Memory</td>
    <td><%=runtime.freeMemory()%> (an approximation to the total amount of memory currently available for future allocated objects, measured in bytes.)</td>
  </tr>

</table>
</authz:authorize>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp" %>