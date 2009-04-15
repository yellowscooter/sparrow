<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<%@page import="com.sparrow.domain.Book"%>

<%
  Book book = (Book)request.getAttribute("product");
  String imageUrl = "/images/productImages/" + book.getImageSmallName();
  pageContext.setAttribute("imageUrl", imageUrl);
%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sparrow.domain.Category"%>
<c:url var="actionUrl"  value="/privileged/admin/activateproduct.htm"/>
<form:form action="${actionUrl}">
<input type="hidden" name="productId" value="<%=book.getProductId()%>"/>
<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3><!-- Add new -->
						<fmt:message key="activate.product" />
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td colspan="2">
						<table  border='0'><tr>
	    				<td valign="top" width="5" align="center" ></td>
	    				<td valign="top" width="60" align="center">
		 				<img src='<c:url value="${imageUrl}"  />' />  
	    				</td>
	    				<td valign="top" width="455" align="left">
	    
					    <b><c:out value="${product.name}"/>&nbsp;<i>by <c:out value="${product.author.fullName}"/></i></b><br/>
					    <!-- Usgin jstl to display description does not maintaing formatting -->
	                    <%=book.getDescription()%><br>
				    	<b>Pages: </b><c:out value="${product.numOfPages}"/><br>
					    <b>Categories</b><br>
					      <c:forEach var="d" items="${product.categories}">								
						    <c:out value="${d.name}"/><br>							
						  </c:forEach>										
					    </td>
		 			   </tr> 
	                   </table>
					</td>
					<td></td>
				</tr>
			</table>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td align="center">
			  <input type="hidden" name="submitAction" value="activate" />
			  <input type="submit" value="Activate"/>
			</td>
			<td></td>
		</tr>
</table>
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>