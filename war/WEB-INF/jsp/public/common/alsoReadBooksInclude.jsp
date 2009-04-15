<%@page import="com.sparrow.domain.Book"%>
<%@page import="com.sparrow.web.WebConstants"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/tooltip.js"/>'></script>


<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<script type="text/javascript">
</script>
<%
	List alsoReadList = (List)request.getAttribute("alsoReadList");
	
	StringBuffer display = new StringBuffer();
	
	Iterator itr = alsoReadList.iterator();
	int i = 0;
	while (itr.hasNext()) {
	  	i++;
	  	Book alsoReadBook = (Book)itr.next();
	  	String id_of_trigger_element = "featuredBooks" + i;
	  	String id_of_tooltip_to_show_element = "tooltip" + i;
	  	display.append("<div style='padding-top: 8px;'  id='" + id_of_trigger_element + "' >");
	  	String url = WebConstants.getProductDetailsPageURL(request, response, alsoReadBook);
		if (alsoReadBook.getImageSmallName() != null) {
	  		//String altText = randomFeatureBook.getName() + " by " + randomFeatureBook.getAuthor().getFullName();
	  		display.append("<a href=\"" + url + "\"><img src='" + request.getContextPath() +  "/images/productImages/" + alsoReadBook.getImageSmallName() + "' /></a>");  
		} else {
	  		display.append("<img src='" + request.getContextPath() + "/images/productImages/" + WebConstants.NO_PRODUCT_IMAGE + "' />");
		}
		display.append("</div>");
	  	String queueStatus = null;
	  	if (user != null && user.isProductAlreadyInQueue(alsoReadBook)) {
	    	queueStatus = "<div style=\"padding-top: 5px; padding-bottom: 5px; \"><img src=\"" + request.getContextPath() + "/images/inQ.gif\" alt=\"In your Bookshelf\" title=\"In your Bookshelf\" ></div>";
	  	} else {
	    	queueStatus = "<div class=\"addToQ\" style=\"padding-top: 5px; padding-bottom: 5px;\"><img src=\"" + request.getContextPath()+ "/images/addToQ.gif\" alt=\"Add To Bookshelf\" title=\"Add To Bookshelf\" onclick=\"addToQ('" + i + "','" + request.getContextPath() + "/ajaxaddtoqueue.htm?productId=" + alsoReadBook.getProductId() + "');\"></div>";
	  	}
	  	display.append("<div class=\"buttonImage\" id='item" + i + "'>" + queueStatus + "</div>");
	  	display.append(WebConstants.getBookTooltipHTML(alsoReadBook, id_of_trigger_element, id_of_tooltip_to_show_element));
	}
	
%>

<% if (alsoReadList.size() > 0) { %>
<div style="border: 1px; border-color: gray; border-style: solid; text-align: center; padding: 2px;width: 150px; ">
<div style="font-size: 16px; font-weight: bold; padding-top: 5px; padding-bottom: 5px;">You May Also Like</div>
	Members who read this book also read
	<%=display.toString()%>  
</div>
<% } %>