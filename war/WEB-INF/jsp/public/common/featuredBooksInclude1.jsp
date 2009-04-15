<%@page import="com.sparrow.domain.Book"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/tooltip.js"/>'></script>

<script type="text/javascript">
// with valid DOM id
var my_tooltip = new Tooltip('id_of_trigger_element', 'id_of_tooltip_to_show_element')
// with text
var my_other_tooltip = new Tooltip('id_of_trigger_element', 'a nice description')
// create popups for each element with a title attribute
Event.observe(window,"load",function() {
$$("*").findAll(function(node){
return node.getAttribute('title');
}).each(function(node){
new Tooltip(node,node.title);
node.removeAttribute("title");
});
});
</script>

<%
	Book randomFeatureBook = (Book)request.getAttribute("randomFeaturedBook");
	String url = WebConstants.getProductDetailsPageURL(request, response, randomFeatureBook);
	StringBuffer display = new StringBuffer();
	display.append("<div id='featuredBooks'>");
	if (randomFeatureBook.getImageSmallName() != null) {
  		String altText = randomFeatureBook.getName() + " by " + randomFeatureBook.getAuthor().getFullName();
  		display.append("<a href=\"" + url + "\"><img alt='" + altText + "' " +
                                          " title='" + altText + "' " +
                                          " src='" + request.getContextPath() +  "/images/productImages/" + randomFeatureBook.getImageSmallName() + "' /></a>");  
	} else {
  		display.append("<img src='" + request.getContextPath() + "/images/productImages/" + WebConstants.NO_PRODUCT_IMAGE + "' />");
	}
	display.append("</div>");
  	String queueStatus = null;
  	if (currentUser != null && currentUser.isProductAlreadyInQueue(randomFeatureBook)) {
    	queueStatus = "<div style=\"padding-top: 5px;\"><img src=\"" + request.getContextPath() + "/images/inQ.gif\" alt=\"In your Bookshelf\" title=\"In your Bookshelf\" ></div>";
  	} else {
    	queueStatus = "<div class=\"addToQ\" style=\"padding-top: 5px;\"><img src=\"" + request.getContextPath()+ "/images/addToQ.gif\" alt=\"Add To Bookshelf\" title=\"Add To Bookshelf\" onclick=\"addToQ('100000','" + request.getContextPath() + "/ajaxaddtoqueue.htm?productId=" + randomFeatureBook.getProductId() + "');\"></div>";
  	}
  	display.append("<div class=\"buttonImage\" id='item100000'>" + queueStatus + "</div>");
%>

<%@page import="com.sparrow.web.WebConstants"%>
<div style="border: 1px; border-color: gray; border-style: solid; text-align: center; padding: 2px;width: 150px;">
  	<span style="font-size: 16px; font-weight: bold">Featured Books</span><br><br>

  	<%=display.toString()%>  
  	<div id='tooltip' style="display:none; margin: 5px; width: 150px;">
 	Detail infos on product 1....<br />
 	</div>
  	<script type="text/javascript">
 	  var my_tooltip = new Tooltip('featuredBooks', 'tooltip')
    </script>
  	
  	

  	<div style="text-align: right;">more>></div>
  </div>