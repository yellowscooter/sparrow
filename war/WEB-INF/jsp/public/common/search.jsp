<!-- Getting message from resource bundle and setting to be used in js -->
<fmt:message key="blank.search.msg" scope="page" var="blankSearchMsg"/>
<script language="JavaScript" type="text/JavaScript">
<!--
//string Author, Title, Description is hardcoded and used in 2 palces in this file...
function resetSearch() { 
  if (document.getElementById("criteria").getAttribute("value") == '<%=(String)pageContext.getAttribute("blankSearchMsg")%>') {
    document.getElementById("criteria").setAttribute("value","");
  }
}


//-->
</script>

<% //create the context prefixed action url and set the pagesScope variable  %>
<c:url var="actionUrl"  value="/browseCatalog.htm"/>
<% //set the action as the variable set above. Note: using the jstl tag inside spring tag directly does not work.
//This variable is then used by the form tag in search include.
//The above step is needed to add query params to the action url's spring tag generates generates. 
//If query string is not added, it causes param value issue with displayTag/spring combination. %>

<form:form commandName="searchCriteria" action="${actionUrl}" onsubmit="javascript:return validateSearchCriteria();">
<tr>
<td colspan="2" valign="top" align="right">
	<!--########## Search Start ##########-->
	<table width="731" align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td valign="top" align="left"><img src="<c:url value="/images/left_curve_search.gif"/>"></td>
	<td width="720"  valign="middle" align="right" background="<c:url value="/images/shade_search.gif"/>"><img src="<c:url value="/images/shade_search.gif"/>" width="720" height="1" border="0"><form:input path="criteria" size="50" maxlength="100" cssStyle="max-height: 20px;font-size:11px;" onclick="javascript:resetSearch();"/>&nbsp;<input type="image" src="<c:url value="/images/search.gif" />" alt="Search for books" title="Search for books" align="absmiddle" /></td>
	<td valign="top" align="right"><img src="<c:url value="/images/right_curve_search.gif"/>"></td>
	</tr>
	</table>
	<!--########## Search End ##########-->
</td>
<td></td>
</tr>
</form:form>
<script language="JavaScript" type="text/JavaScript">
if (document.getElementById("criteria").getAttribute("value") == '') {
  document.getElementById("criteria").setAttribute("value","<%=(String)pageContext.getAttribute("blankSearchMsg")%>");
}
</script>