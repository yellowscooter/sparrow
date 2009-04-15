	<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="com.sparrow.domain.Product"%>
<%@page import="com.sparrow.domain.Book"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<%@page import="com.sparrow.domain.User"%>
<%@page import="com.sparrow.web.WebConstants"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/prototype.js"/>'></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/ajaxLogin.js"/>'></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/divUtils.js"/>' ></script>

<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>

<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>
<%
  Product book = (Book)request.getAttribute("product");
  User user = GeneralUtils.getCurrentUserFromTLS();
  String authorUrl = WebConstants.getAuthorPageURL(request, response, book.getAuthor());
%>
<fmt:setTimeZone value="Asia/Calcutta" scope="page"/>
<tr>
	<td colspan="2" valign="top" align="left"><img
		src="<c:url value="/images/1pixel_white.gif"/>" height="5" width="1" ><br
		clear="all">

	<!--########## Main Content table Begin ##########-->
	<table width="731" align="center" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td valign="top" width="178" align="center">
			<%@ include file="/WEB-INF/jsp/public/catalog/categoryInclude.jsp" %>
			<br><br>
			<img src="<c:url value="/images/ccAvenue.gif"/>" border=0>
			</td>
			<td valign="top" width="10" align="left"><img
				src="<c:url value="/images/1pixel_white.gif"/>" height="1' width="10"></td>
			<td valign="top" width="543" align="center">
			<!--########## Start Here ##########-->
			<div style="border: 1px; border-color: gray; border-style: solid; text-align: left; padding: 2px;">
			<table width="530" align="center" cellpadding="2" cellspacing="0" border="0">
				<tr>
					<td valign="top" align="left">
						  <c:if test="${product.imageSmallName != null}">
						    <img title='<c:out value="${product.name}"></c:out> by <c:out value="${product.author.fullName}"></c:out>' 
						    	 alt='<c:out value="${product.name}"></c:out> by <c:out value="${product.author.fullName}"></c:out>'
						    	 src='<c:url value="/images/productImages/${product.imageSmallName}"></c:url>' />
						  </c:if>
						  <c:if test="${product.imageSmallName == null}">
						    <img title="<c:out value="${product.name}"></c:out>" src="<c:url value="/images/productImages/noProductImage.gif"></c:url>" >
						  </c:if>
					</td>
					<td valign="top" align="left">
						<h1 style="border-bottom:none;width: 420px;"><c:out value="${product.name}"></c:out></h1> 
						<h2 style="border-bottom:none;padding:0px 0px 0px 0;" ><i>by&nbsp;<a href="<%=authorUrl%>"><c:out value="${product.author.fullName}"></c:out></a></i></h2>
						<b>Book Description</b>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- ADDTHIS BUTTON BEGIN -->
						<script type="text/javascript">
						addthis_pub             = 'friendsofbooks'; 
						addthis_brand           = 'FriendsOfBooks.com';
						</script>
						<a href="http://www.addthis.com/bookmark.php" 
						onmouseover="return addthis_open(this, '', '[URL]', '<c:out value="${product.name}"></c:out> by <c:out value="${product.author.fullName}"></c:out>')" 
						onmouseout="addthis_close()" 
						onclick="return addthis_sendto()">
						<img src="http://s7.addthis.com/button1-share.gif" width="125" height="16" border="0" alt="" /></a>
						<script type="text/javascript" src="http://s7.addthis.com/js/152/addthis_widget.js"></script>
						<!-- ADDTHIS BUTTON END -->
						<br><c:out value="${product.description}" escapeXml="false"></c:out>
						<br><br><b>Pages:</b> <c:out value="${product.numOfPages}"></c:out>
						<br><br><b>Accolades</b><br>
						  <c:forEach items="${awardCategories}" var="cat">
						      <c:out value="${cat.description}"/><br>
						  </c:forEach>
						<br>
						<b>Tags</b><br>
						  <c:forEach items="${tags}" var="tag">
						      <u><c:out value="${tag.description}"/></u>&nbsp;
						  </c:forEach>
						<br><br>
						<%//001 is just an arbit number to uniquely identify this div
						  //since this page will only display 1 product...so does not need to
						  //be driven by prod %>
						<div class="buttonImage" id='item001'>
						<%
						  if (user != null && user.isProductAlreadyInQueue(book)) {
						%>
					    <img src="<c:url value="/images/inQ.gif"></c:url>" alt="In your Bookshelf" title="In your Bookshelf" >
					    <% } else { %>
					      <div class="addToQ"><img src='<c:url value="/images/addToQ.gif"></c:url>' 
					      						alt="Add To Bookshelf" title="Add To Bookshelf" 
					      						onclick="addToQ('001', '<c:url value='/ajaxaddtoqueue.htm?productId=${product.productId}'></c:url>')"></div>
					    <% } %>
					    </div>
					    <input type="hidden" name="qid" id="qid"/>
					</td>
				
				</tr>
				<tr><td colspan="2" >
							<%
								Product product = (Product)request.getAttribute("product");
								if (product.getProductReviews().size() > 0) {
							%>
							<div style="border-top: 1px; border-top-color: gray; border-top-style: dashed; margin-top: 20px">
								<h2 style="border-bottom:none;margin-bottom: 0px; margin-top: 15px">Member reviews: &#145;<c:out value="${product.name}"></c:out>&#146;</h2> 
							</div>
							<% } %>
							<c:forEach items="${product.productReviews}" var="productReview">
							<div style="margin-bottom: 20px;">
							  		<span style="font-weight: bold; font-size: 13px"><c:out value="${productReview.reviewTitle}"/></span>, <fmt:formatDate value="${productReview.reviewDate}" dateStyle="medium" /> 
							  			<br>by <c:out value="${productReview.reviewerName}"/> 
							  		<div style="margin-top: 5px"><c:out value="${productReview.review}" escapeXml="false"/></div>
							</div>
							</c:forEach>
							<div style="border-top: 1px; border-top-color: gray; border-top-style: dashed; margin-top: 20px">
							<form  name="reviewForm" id="reviewForm" method="post" action="<c:url value="/addreview.htm"></c:url>">
								<h2 style="border-bottom:none;margin-bottom: 0px">Write a review of &#145;<c:out value="${product.name}"></c:out>&#146; by <c:out value="${product.author.fullName}"></c:out></h2> 
								<table border="0">
								  <tr>
								    <td align="right">Your Name:</td>
								    <td><input type="text" name="reviewerName" id="reviewerName" maxlength="255" size="65" onclick="javascript:populateReviewerName();"/></td>
								  </tr>
								  <tr>
								    <td align="right">Review Title:</td>
								    <td><input type="text" name="reviewTitle" maxlength="100" size="65"/></td>
								  </tr>
								</table>
								<div><textarea style="width: 500px; height: 120px;" name="review" cols="" rows=""></textarea></div>	
								<div style="text-align: center;"><input type="hidden" value="<c:out value="${product.productId}"></c:out>" name="productId">
									<% if (user != null) { %> 
									<input type="hidden" value="<%=user.getFullname()%>" name="username" id="username">
									<%} else {%>
									<input type="hidden" name="username" id="username">
									<% } %>
									<input type="submit" value="Save Review"/>
								</div>	
							</form>
							</div>
						
					
				</td></tr>
				
				

			</table>
			</div>
			<!--########## End Here ##########--></td>
		</tr>
	</table>
	<br clear="all">
</td>
<td valign="top">  
  <%//add noticeBoardInclude after updating the notice in the include /WEB-INF/jsp/public/common/noticeBoardInclude.jsp%>
  <br><%@ include file="/WEB-INF/jsp/public/common/alsoReadBooksInclude.jsp"%>
  
</td>
</tr>
	<!--########## Main Content table End ##########--> 
<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>