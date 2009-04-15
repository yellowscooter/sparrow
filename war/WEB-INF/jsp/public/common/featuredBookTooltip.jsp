<%//reusable book tooltip component...uses tooltip.js to get the work done
  //featuredBook is the variable declared in the script that includes this script...see featuredBooksInclude.jsp
%>  
<script language="javascript" type="text/javascript" src='<c:url value="/js/tooltip.js"/>'></script>	
<div id='tooltip' style="padding: 10px;">
  <div style="border: 1px; border-color: #FF9900; border-style: solid; text-align: left; background-color: white; ">
 	<div style="background-color: #FF9900; padding: 5px;width: 250px; font-weight: bold;"><%=featuredBook.getName()%><br>by <i><%=featuredBook.getAuthor().getFullName()%></i></div>
 	<div style="padding: 5px; width: 250px;">
 	  <%=featuredBook.getDescription()%>
 	</div>
  </div>
</div>
<script type="text/javascript">
  var my_tooltip = new Tooltip('featuredBooks', 'tooltip')
</script>
