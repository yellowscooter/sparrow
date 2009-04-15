<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>

<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>

<tr>
	<td colspan="2" valign="top" align="left"><img
		src="<c:url value="/images/1pixel_white.gif"/>" height="5" width="1" ><br
		clear="all">

	<!--########## Main Content table Begin ##########-->
	<table width="731" align="center" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td valign="top" width="178" align="center">			
			<%@ include file="/WEB-INF/jsp/public/catalog/categoryInclude.jsp"%>
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
				<tr>
				  <td valign="top" align="left"><h1><fmt:message key="author.list" /></h1></td>
				</tr>
				<tr>
				  <td align="left" class="alphabetIndex">
				  <%
				    String aToZ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				    for (int i = 0; i < aToZ.length(); i++) {
				      String oneChar = aToZ.substring(i, i + 1);
				      pageContext.setAttribute("oneChar", oneChar);
				  %>
				      <c:url var="url" value="authorlist.htm">
						  <c:param name="criteria" value="${oneChar}" />
					  </c:url>
					  
				      <a href="<c:out value="${url}"/>" ><c:out value="${oneChar}"/>&nbsp;</a>
				      <%
				    }
				  
				  %>
				  </td>
				</tr>
				
				<tr>
					<td colspan="3" width="530" align="left">
						<grid:table
							name="sessionScope.authorList"
							pagesize="250"
							requestURI="authorlist.htm"
							decorator="com.sparrow.web.catalog.AuthorListDecorator">
							<grid:setProperty name="basic.show.header" value="false" />
							<grid:setProperty name="paging.banner.all_items_found" value="" />
				        	<grid:setProperty name="paging.banner.onepage" value="" />
				        	<grid:setProperty name="paging.banner.one_item_found" value="" />
							<grid:setProperty name="paging.banner.placement" value="bottom" />
							<grid:setProperty name="paging.banner.item_name" value="author" />
							<grid:setProperty name="paging.banner.items_name" value="authors" />
							
							<grid:column property="display" />
						</grid:table>
					</td>
				</tr>
				


			</table>
			<!--########## End Here ##########--></td>
		</tr>
	</table>
	<br clear="all">
	<!--########## Main Content table End ##########--> 
<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>