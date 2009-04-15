<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>

<%@ include file="/WEB-INF/jsp/public/common/searchBarWithoutSearch.jsp"%>
<%@page import="com.sparrow.domain.User"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<script type="text/javascript">
	function validate()
	{
		for (i=0;i<document.forms[0].subscriptionPlanId.length;i++) {
			if (document.forms[0].subscriptionPlanId[i].checked) {
				return true;
			}
		}
		return false;
	}
	
</script>
<tr>
<td colspan="2">
<%
  User user = GeneralUtils.getCurrentUserFromTLS();
  //set in request so it can be accessed in Decorators etc
  request.setAttribute("currentUser", user);
%>

<!--########## Main Content table Begin ##########--><br />
<form id="subscriptionPlanForm" method="POST" action="selectsubscriptionplan.htm">
<%//set the tab you want to be selected after this page is submitted%>
<input type="hidden" name="tab" value="4"/>

<table width="680" align="center" cellpadding="2" cellspacing="0" border="0">
<tr>
<td valign="top" align="left"><h1><fmt:message key="user.subscriptionPlan" /></h1></td>
</tr>
<tr>
<td valign="top" align="left">
<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
</td>
</tr>


<tr>
    <!-- Rounded corners table, thanks to a great solution by http://www.spiffycorners.com -->
	<td valign="top" align="center" >
		<div>
		  <b class="spiffy">
		  <b class="spiffy1"><b></b></b>
		  <b class="spiffy2"><b></b></b>
		  <b class="spiffy3"></b>
		  <b class="spiffy4"></b>
		  <b class="spiffy5"></b></b>
 		  <div class="spiffyfg">
			
			<table width="600" align="center" cellpadding="3" cellspacing="0" border="0">
			
			<%  //if new user, display message
				if (!"update".equals(request.getAttribute("action"))) { %>
			<tr>
			  <td>
			    <fmt:message key="user.subscriptionPlan.info" />
			  </td>
			</tr>
			<% } %>
			
			
			<%  //if update and no plan selected, display message
				//User user = GeneralUtils.getCurrentUserFromTLS();
				if ("update".equals(request.getAttribute("action")) && user.getSubscriptionPlan() == null) { %>
			<tr>
			  <td>
			    <fmt:message key="user.subscriptionPlan.not.selected" />
			  </td>
			</tr>
			<% } %>
			
			<%  //if update and plan has already been selected, display message regarding plan updates
				if ("update".equals(request.getAttribute("action")) && user.getSubscriptionPlan() != null) { %>
			<tr>
			  <td align="left">
			    <fmt:message key="user.subscriptionPlan.update" />
			  </td>
			</tr>
			<% } %>
			
			
			<tr>
			  <td align="left">
			  	<grid:table name="requestScope.subscriptionPlanList" 
			  			decorator="com.sparrow.web.user.SelectSubscriptionPlanDecorator"
			  			pagesize="100"	cellpadding="2" cellspacing="1" >
					<grid:setProperty name="paging.banner.all_items_found" value="" />
		        	<grid:setProperty name="paging.banner.onepage" value="" />
		        	<grid:setProperty name="paging.banner.one_item_found" value="" />
					<grid:setProperty name="paging.banner.placement" value="bottom" />
					<grid:column property="selectPlan" title="" style="vertical-align:top;"/>
					<grid:column property="planDetails" title=""/>
				</grid:table>
			  </td>
			</tr>

			<tr>
				<td align="center">
				<!-- This jsp is called while updating the plan, and also while new user creation -->
				<% if ("update".equals(request.getAttribute("action"))) { %>
					<input type="hidden" name="action" value="subscriptionPlanUpdate" />
					<input type="submit" name="submit" value="Update" onclick="javascript:return validate();"/>
					&nbsp;&nbsp;<input type="button" value="Cancel" class="button"
								onclick="javascript:history.back();"/>
			    <% } else { %>
			    	<input type="hidden" name="action" value="subscriptionPlanSelect" />
			    	<input type="submit" name="submit" value="<fmt:message key="user.subscrition.plan.save" />" onclick="javascript:return validate();"/>
			    <%  }%>
			    </td>
			</tr>
			</table>

		  </div>
		  <b class="spiffy">
		  <b class="spiffy5"></b>
		  <b class="spiffy4"></b>
		  <b class="spiffy3"></b>
		  <b class="spiffy2"><b></b></b>
		  <b class="spiffy1"><b></b></b></b>
		</div>
	</td>
</tr>




</table><br clear="all"><br />	
</form>
<!--########## Main Content table End ##########-->
	

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>