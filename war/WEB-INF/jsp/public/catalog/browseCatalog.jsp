<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="com.sparrow.web.catalog.BrowseCatalogController"%>
<%@page import="com.sparrow.domain.Author"%>
<%@page import="com.sparrow.domain.User"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/prototype.js"/>'></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/ajaxLogin.js"/>'></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/divUtils.js"/>' ></script>

<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>

<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>
<%
  User user = GeneralUtils.getCurrentUserFromTLS();
  //set in request so it can be accessed in Decorators etc
  request.setAttribute("currentUser", user);
%>

<tr>
	<td colspan="2" valign="top" align="left"><img
		src="<c:url value="/images/1pixel_white.gif"/>" height="5" width="1" ><br
		clear="all">

	<!--########## Main Content table Begin ##########-->
	<table width="731" align="center" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td valign="top" width="178" align="center">
			<%@ include file="/WEB-INF/jsp/public/catalog/categoryInclude.jsp"%>
			<br><br>
			<img src="<c:url value="/images/ccAvenue.gif"/>" border=0>
			</td>
			<td valign="top" width="10" align="left"><img
				src="<c:url value="/images/1pixel_white.gif"/>" height="1' width="10"></td>
			<td valign="top" width="543" align="center">
			<!--########## Start Here ##########-->
			<table width="530" align="center" cellpadding="3" cellspacing="0"
				border="0">
				<tr>
					<td colspan="6" valign="top" align="left"><spring:message
						code="${message}" text="" /></td>
				</tr>
				<% 
					String currentCategoryName = (String)request.getAttribute("currentCategoryName");
					if (currentCategoryName != null) {		   
				%>
				<tr>
					<td colspan="6" valign="top" align="left" ><h1 style="border-bottom:none;">>><%=currentCategoryName%></h1></td>
				</tr>
				<% } %>
				<% 
					String displayRecentlyAddedProductList = (String)request.getAttribute("displayRecentlyAddedProductList");
			  
					if ("true".equals(displayRecentlyAddedProductList)) {		   
				%>
				<tr>
					<td colspan="6" valign="top" align="left"><h1 style="border-bottom:none;"><fmt:message key="recently.added"/></h1></td>
				</tr>
				<% } %>
				<% 
					String displayAuthorName = (String)request.getAttribute("displayAuthorName");
			  
					if ("true".equals(displayAuthorName)) {		   
					Author author = (Author)request.getAttribute("author");
				%>
				<tr>
					<td colspan="6" valign="top" align="left"><h1 style="border-bottom:none;"><%=author.getFullName()%>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- ADDTHIS BUTTON BEGIN -->
						<script type="text/javascript">
						addthis_pub             = 'friendsofbooks'; 
						addthis_brand           = 'FriendsOfBooks.com';
						</script>
						<a href="http://www.addthis.com/bookmark.php" 
						onmouseover="return addthis_open(this, '', '[URL]', '<c:out value="${author.fullName}"></c:out>')" 
						onmouseout="addthis_close()" 
						onclick="return addthis_sendto()">
						<img src="http://s7.addthis.com/button1-share.gif" width="125" height="16" border="0" alt="" /></a>
						<script type="text/javascript" src="http://s7.addthis.com/js/152/addthis_widget.js"></script>
						<!-- ADDTHIS BUTTON END -->
						</h1>
					
					</td>
				</tr>
				<% } %>
				<tr>
					<td colspan="3" width="530" align="left">
					<%
					  //since homeProductList is a collection, and search lists and category lists
					  //are pagedList, we need to display them in two different grid tags since 
					  //pagedList are request based, and collections are session based.
					  //using the same grid is displaying wrong pagin information in homeProductList
					  String displayHomeProductList = (String)request.getAttribute("displayHomeProductList");
					  
					  if (displayHomeProductList == null) {
					  //display the books by category
					  String requestURI="browseCatalog.htm";
					  //set excluded params not to be added to query string is paging urls
					  String excludedParams = "";
					  //if we are displaying author product list, then requestURI changes since aurhor page has a keywords based URL
					  if ("true".equals(displayAuthorName)) {		
					    requestURI = (String)request.getAttribute("requestURI");
					    //exclude author_id since it is not needed in query param after new keyword url
					    excludedParams="author_id";
					  }
					%>
						<grid:table
							name="requestScope.productList"
							requestURI="<%=requestURI%>"
							decorator="com.sparrow.web.catalog.CatalogBookListDecorator"
							excludedParams="<%=excludedParams%>">
							<grid:setProperty name="basic.show.header" value="false" />
							<grid:setProperty name="paging.banner.all_items_found" value="" />
							<grid:setProperty name="paging.banner.one_item_found" value="" />
							<grid:setProperty name="paging.banner.some_items_found" value="" />
							<grid:setProperty name="paging.banner.placement" value="bottom" />
							<grid:setProperty name="paging.banner.item_name" value="book" />
							<grid:setProperty name="paging.banner.items_name" value="books" />
							<grid:setProperty name="paging.banner.onepage" value="" />
							<grid:setProperty name="basic.msg.empty_list" value="No book matching your search is currently available in the catalogue." />
							<grid:column property="display" />
						</grid:table>
					<% } else if ("true".equals(displayHomeProductList)) { 
						//display home page or Just added books
					%>
						<grid:table
							name="sessionScope.homeProductList"
							pagesize="20"
							requestURI="browseCatalog.htm"
							decorator="com.sparrow.web.catalog.CatalogBookListDecorator">
							<grid:setProperty name="basic.show.header" value="false" />
							<grid:setProperty name="paging.banner.all_items_found" value="" />
				        	<grid:setProperty name="paging.banner.onepage" value="" />
				        	<grid:setProperty name="paging.banner.one_item_found" value="" />
							<grid:setProperty name="paging.banner.placement" value="bottom" />
							<grid:column property="display" />
						</grid:table>
					<% } %>					
					</td>
				</tr>
				<tr><td><input type="hidden" name="qid" id="qid"/>
						<input type="hidden" name="oldDivText" id="oldDivText"/></td></tr>


			</table>
			<!--########## End Here ##########--></td>
		</tr>
	</table>
	<br clear="all">
</td>
<td valign="top">  
  <%//add noticeBoardInclude after updating the notice in the include /WEB-INF/jsp/public/common/noticeBoardInclude.jsp%>
  <br><br><%@ include file="/WEB-INF/jsp/public/common/noticeBoardInclude.jsp"%>
  <br><br><%@ include file="/WEB-INF/jsp/public/common/featuredBooksInclude.jsp"%>
  
</td>
</tr>
	<!--########## Main Content table End ##########--> 
<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>