<%@page import="com.sparrow.domain.Book"%>
<%@page import="com.sparrow.web.WebConstants"%>


<script type="text/javascript">
</script>
<%
	Book featuredBook = (Book)request.getAttribute("randomFeaturedBook");
	String url = WebConstants.getProductDetailsPageURL(request, response, featuredBook);
	StringBuffer display = new StringBuffer();
	display.append("<div id='featuredBooks'>");
	if (featuredBook.getImageSmallName() != null) {
  		//String altText = randomFeatureBook.getName() + " by " + randomFeatureBook.getAuthor().getFullName();
  		display.append("<a href=\"" + url + "\"><img src='" + request.getContextPath() +  "/images/productImages/" + featuredBook.getImageSmallName() + "' /></a>");  
	} else {
  		display.append("<img src='" + request.getContextPath() + "/images/productImages/" + WebConstants.NO_PRODUCT_IMAGE + "' />");
	}
	display.append("</div>");
  	String queueStatus = null;
  	if (currentUser != null && currentUser.isProductAlreadyInQueue(featuredBook)) {
    	queueStatus = "<div style=\"padding-top: 5px;\"><img src=\"" + request.getContextPath() + "/images/inQ.gif\" alt=\"In your Bookshelf\" title=\"In your Bookshelf\" ></div>";
  	} else {
    	queueStatus = "<div class=\"addToQ\" style=\"padding-top: 5px; padding-bottom: 5px;\"><img src=\"" + request.getContextPath()+ "/images/addToQ.gif\" alt=\"Add To Bookshelf\" title=\"Add To Bookshelf\" onclick=\"addToQ('100000','" + request.getContextPath() + "/ajaxaddtoqueue.htm?productId=" + featuredBook.getProductId() + "');\"></div>";
  	}
  	display.append("<div class=\"buttonImage\" id='item100000'>" + queueStatus + "</div>");
%>

<div style="border: 1px; border-color: gray; border-style: solid; text-align: center; padding: 2px;width: 150px; ">
<div style="font-size: 16px; font-weight: bold; padding-top: 5px; padding-bottom: 5px;">Serendipity Corner</div>
	<%=display.toString()%>  
	<%@ include file="/WEB-INF/jsp/public/common/featuredBookTooltip.jsp"%>
</div>