<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<c:url var="actionUrl"  value="/privileged/admin/productlist.htm"/>
<form:form commandName="searchCriteria" action="${actionUrl}">
	<table>
	  <tr>
	    <td><h3><fmt:message key="productList" /></h3></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Search:&nbsp;<form:input path="criteria" size="50" maxlength="100" />
	    					&nbsp;<input type="submit" value="Search" />
	    </td>
	    <td></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<grid:table name="requestScope.adminProductList" pagesize="10" requestURI="productlist.htm"
	    				decorator="com.sparrow.web.catalog.ProductListDecorator">
				<grid:column property="productId" title="ID"/>
				<grid:column property="name" title="Name"/>
				<grid:column property="author.fullName" title="Author"/>
				<grid:column property="isbn" title="ISBN"/>
				<grid:column property="numInStock" title="NumInStock"/>
				<grid:column property="availableInStock" title="AvailableInStock"/>
				<grid:column property="productInstances" title="Instances" />
				<grid:column property="link1" title=""/>
				<grid:setProperty name="paging.banner.placement" value="bottom" />
			</grid:table>
	    </td>
	    <td></td>
	  </tr>
	  
	  
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>