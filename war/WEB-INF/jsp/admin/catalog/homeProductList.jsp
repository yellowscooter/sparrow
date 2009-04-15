<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<c:url var="actionUrl"  value="/privileged/admin/homeproductlist.htm"/>
<form:form action="${actionUrl}" >
	<table>
	  <tr>
	    <td><h3><fmt:message key="homeProductList" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Products displayed on this page are also displayed on Catalog Home Page.
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Enter Product Id to display product on catalog Homepage:&nbsp;<input type="text" name="productId" size="10" maxlength="10" />
	    					&nbsp;<input type="submit" value="Add" />
	    	<input type="hidden" name="action" value="addToHomeProductList"/>
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<grid:table id="row" name="requestScope.adminHomeProductList" pagesize="50" requestURI="homeProductList.htm"
	    						decorator="com.sparrow.web.catalog.HomeProductListDecorator">
	    		<!-- This object is made available by display tag -->
	    		<grid:column title="Priority" >
			      <c:out value="${row_rowNum}"/>
    			</grid:column>
				<grid:column property="productId" title="ID"/>
				<grid:column property="name" title="Name"/>
				<grid:column property="remove" title="Remove"/>
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
	  
	  
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>