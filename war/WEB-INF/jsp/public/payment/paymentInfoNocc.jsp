<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@page import="com.sparrow.domain.User"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/divUtils.js"/>' ></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/prototype.js"/>'></script>
<script type="text/javascript">
  function showHideBillingAddress() {
    if (document.getElementById('useShippingAddressAsBillingAddress1').checked==true) {
      showHideLayer('billingInfo', false);
    } else {
      showHideLayer('billingInfo', true);
    }
  }
  
</script>

<form:form commandName="paymentInfo">
<%//set the tab you want to be selected after this page is submitted%>
<input type="hidden" name="tab" value="4"/>


<tr>
<td colspan="2" valign="top" align="right">
	<!--########## Search Start ##########-->
	<table width="731" align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td valign="top" align="left"><img src="<c:url value="/images/left_curve_search.gif"/>"></td>
	<td width="720" valign="middle" align="right"  class="text" background="<c:url value="/images/shade_search.gif"/>"><img src="<c:url value="/images/shade_search.gif"/>" width="720" height="1" border="0"><br clear="all"></td>
	<td valign="top" align="right"><img src="<c:url value="/images/right_curve_search.gif"/>"></td>
	</tr>
	</table>
	<!--########## Search End ##########-->
</td>
</tr>

<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="2" cellspacing="0" border="0">
<tr>
<td colspan="2" valign="top" align="left"><h1><fmt:message key="user.payment.info" /></h1></td>
</tr>
<tr>
<td colspan="2" valign="top" align="left">
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

			<table width="500" cellpadding="3" cellspacing="0" border="0">
				<tr>
					<td align="left">
					  <% User user = (User)request.getAttribute("user");
					  	 if (user.getPendingBill() != null) {
					  %>
						<table border="0">
						    <tr>
								<td align="right">Subscription Plan:</td>
								<td><c:out value="${user.pendingBill.subscriptionPlan.name}"/></td>
							</tr>
							<tr>
								<td align="right">Payment Due:</td>
								<td><fmt:message key="currency.symbol" />&nbsp;<c:out value="${user.pendingBill.amount}"/>&nbsp;(<c:out value="${user.pendingBill.description}"/>)</td>
							</tr>
							<tr>
								<td align="right"><b>Pay by:</b></td>
								<td></td>
							</tr>
							<tr>
								<td align="right">Credit Card/Debit Card/Internet Banking</td>
								<td><form:radiobutton path="paymentMethod" value="CC" /></td>
							</tr>

							<tr>
								<td align="right">Cheque:</td>
								<td><form:radiobutton path="paymentMethod" value="CK" /></td>
							</tr>
							<tr>
								<td align="right">Cash:</td>
								<td><form:radiobutton path="paymentMethod" value="CA" /></td>
							</tr>
							
						</table>
					
						<table>
						  	<tr>
								<td width="150"></td>
								<td>
								<% if ("update".equals(request.getAttribute("action"))) { %>
								  <input type="hidden" name="action" value="paymentInfoUpdate" />
								  <input type="submit" value="<fmt:message key="user.process.payment" />" class="button"/>
								  &nbsp;&nbsp;<input type="button" value="Cancel" class="button"
														onclick="javascript:history.back();"/>
								<% } else { %>
								  <input type="hidden" name="action" value="paymentInfoNew" />
								  <input type="submit" value="<fmt:message key="user.process.payment" />" class="button"/>
								  
								<% } %>						
								</td>
							</tr>		
						</table>
						<% } else { %>
						    <fmt:message key="no.pending.payment" />
						<% } %>
						
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
		
		<table border="0"><tr><td></td><td>&nbsp;</td></tr>
						  <tr><td></td><td>&nbsp;</td></tr>
						  <tr><td><img src="<c:url value="/images/ccAvenue_logo2.gif"/>" border=0></td>
								<td align="left"><fmt:message key="help.epayment.info" /></td></tr>
		</table>
		
	</td>
	<td valign="top"> 
	  <!-- Help box begin -->
	  <div>
		  <b class="help">
		  <b class="help1"><b></b></b>
		  <b class="help2"><b></b></b>
		  <b class="help3"></b>
		  <b class="help4"></b>
		  <b class="help5"></b></b>
		
		  <div class="helpfg">
		    <!-- Help Table -->
		    <table width="180">
 		  	  <tr><td class="helpHeader"><fmt:message key="help.header" /></td></tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h4');"><fmt:message key="help.payment.cc.header" /></a><br>
 		  	          <div id='h4' class="helpContent"><fmt:message key="help.payment.cc.content" /><fmt:message key="help.epayment.info" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h4');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h1');"><fmt:message key="help.payment.check.header" /></a><br>
 		  	          <div id='h1' class="helpContent"><fmt:message key="help.payment.check.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h1');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h3');"><fmt:message key="help.payment.cash.header" /></a><br>
 		  	          <div id='h3' class="helpContent"><fmt:message key="help.payment.cash.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h3');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  
 		  	  		
 		  	  
 		    </table>
 		    <!-- End help table -->
		  </div>
		
		  <b class="help">
		  <b class="help5"></b>
		  <b class="help4"></b>
		  <b class="help3"></b>
		  <b class="help2"><b></b></b>
		  <b class="help1"><b></b></b></b>
	  </div>
	  <!-- Help Box end -->
 		  
	</td>
</tr>

</table><br clear="all"><br />	
<!--########## Main Content table End ##########-->


</form:form>

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>