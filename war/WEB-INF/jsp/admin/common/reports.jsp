<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net" %>
<script language="javascript" type="text/javascript" src='<c:url value="/js/sparrow.js"/>' ></script>

<form name="searchCriteriaForm" action="<c:url value="/privileged/admin/userlist.htm"/>">
	<table>
	  
	  <% 
	    String reportCode = (String)request.getAttribute("reportCode");
	    if ("expiredAccounts".equals(reportCode)) { 	  
	  %>
		  <tr>
		    <td><h3>Users with Expired Accounts</h3></td>
		    <td></td>
		  </tr>
		  <tr>
		    <td colspan="2">List of all users who have been paying members but currently their account has expired.</td>
		  </tr>
		  <tr>
		    <td colspan="2">
		    	<grid:table name="requestScope.expiredAccountsUserList" requestURI="/privileged/admin/reports.htm"
		    				 decorator="com.sparrow.web.common.ReportsDecorator">
		    		<grid:column property="userId" title="UserId"/>		
					<grid:column property="username" title="Username"/>
					<grid:column property="firstname" title="First Name"/>
					<grid:column property="lastname" title="Last Name"/>
					<grid:column property="accountEnabled" title="Enabled"/>
					<grid:column property="accountCreateDate" title="Create Date" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
					<grid:column property="startDate" title="Mem. Start Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
					<grid:column property="expirationDate" title="Mem. Exp Date" decorator="com.sparrow.web.common.MediumDateFormatDecorator"/>
					<grid:column property="phone" title="Home Phone"/>
					<grid:column property="mobilePhone" title="Mobile Phone"/>
					<grid:column property="subscriptionPlan.name" title="Sub. Plan"/>
					<grid:column property="detailsLink" title="Details"/>
					
					<grid:setProperty name="paging.banner.placement" value="bottom" />
				</grid:table>
		    </td>
		    <td></td>
		  </tr>
	  <% } else if ("booksMostInDemand".equals(reportCode)) {%>
	  	  
	    <tr>
		    <td><h3>Book Demand List</h3></td>
		    <td></td>
		  </tr>
		  <tr>
		    <td colspan="2">List of books that users have added to their Bookshelves, sorted by count descending.</td>
		  </tr>
		  <tr>
		    <td colspan="2">
		    	<grid:table name="requestScope.booksMostInDemandList" requestURI="/privileged/admin/reports.htm"
		    				 decorator="com.sparrow.web.common.ReportsDecorator">
		    		<grid:column property="count" title="Count of Bookshelves with Book"/>		
					<grid:column property="productId" title="ID"/>
					<grid:column property="productName" title="Title"/>
					<grid:column property="product.author.fullName" title="Author"/>
					<grid:column property="productInstancesCount" title="Instance Count"/>
					<grid:column property="productInstancesCountRatio" title="Count of Bookshelves with Book/Instance Count"/>
					<grid:column property="productInstances" title="Product Instances"/>
					<grid:setProperty name="paging.banner.placement" value="bottom" />
				</grid:table>
		    </td>
		    <td></td>
		  </tr>
	  <% } else if ("userSearches".equals(reportCode)) {%>
	  	  
	    <tr>
		    <td><h3>User Searches List</h3></td>
		    <td></td>
		  </tr>
		  <tr>
		    <td colspan="2">List of user searches, sorted by date descending.</td>
		  </tr>
		  <tr>
		    <td colspan="2">
		    	<grid:table name="requestScope.userSearchesList" requestURI="/privileged/admin/reports.htm"
		    				 decorator="com.sparrow.web.common.ReportsDecorator">
		    		<grid:column property="search" title="Search Criteria"/>		
					<grid:column property="userName" title="userName"/>
					<grid:column property="firstName" title="firstName"/>
					<grid:column property="lastName" title="lastName"/>
					<grid:column property="searchDate" title="searchDate" decorator="com.sparrow.web.common.MediumDateTimeFormatDecorator"/>
					
					<grid:setProperty name="paging.banner.placement" value="bottom" />
				</grid:table>
		    </td>
		    <td></td>
		  </tr>
	  <% } else if ("userSearchesCount".equals(reportCode)) {%>
	  	  
	    <tr>
		    <td><h3>User Searches Count List</h3></td>
		    <td></td>
		  </tr>
		  <tr>
		    <td colspan="2">List of count users have searched for the same search criteria, sorted by count descending.</td>
		  </tr>
		  <tr>
		    <td colspan="2">
		    	<grid:table name="requestScope.userSearchesCountList" requestURI="/privileged/admin/reports.htm"
		    				 decorator="com.sparrow.web.common.ReportsDecorator">
		    		<grid:column property="search" title="Search Criteria"/>		
					<grid:column property="count" title="Count"/>
					
					<grid:setProperty name="paging.banner.placement" value="bottom" />
				</grid:table>
		    </td>
		    <td></td>
		  </tr>
	  <% } %>
	</table>
</form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>