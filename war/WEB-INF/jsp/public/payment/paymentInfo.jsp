<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
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
						<table>
							<tr>
								<td align="right" width="150">Check:</td>
								<td><form:radiobutton path="paymentMethod" value="CK" 
									onclick="javascript:showHideLayer('creditCardInfo', false);showHideLayer('billingInfo', false);"/><br>
								</td>
							</tr>
							<tr>
								<td align="right">Demand Draft:</td>
								<td><form:radiobutton path="paymentMethod" value="DD" 
									onclick="javascript:showHideLayer('creditCardInfo', false);showHideLayer('billingInfo', false);"/><br>
								</td>
							</tr>
							<tr>
								<td align="right">Cash:</td>
								<td><form:radiobutton path="paymentMethod" value="CA" 
									onclick="javascript:showHideLayer('creditCardInfo', false);showHideLayer('billingInfo', false);"/><br>
								</td>
							</tr>
							<tr>
								<td align="right">Credit Card:</td>
								<td><form:radiobutton path="paymentMethod" value="CC" 
									onclick="javascript:showHideLayer('creditCardInfo', true);showHideLayer('billingInfo', true);"/><br>
								</td>
							</tr>	
						</table>
					
						<c:if test="${paymentInfo.paymentMethod=='CC'}" >
							<div id="creditCardInfo" class="formFields" style="display:block;">
						</c:if>
						<c:if test="${paymentInfo.paymentMethod!='CC'}" >
							<div id="creditCardInfo" class="formFields" style="display:none;">
						</c:if>
			
						  <table>
							<tr>
								<td align="right" width="150">Card Type:</td>
								<td><form:select path="creditCardInfo.creditCardType">
									<form:option value="" label="-----" />
									<form:option value="visa" label="VISA" />
									<form:option value="mastercard" label="MasterCard"/>
								</form:select><br>
								</td>
							</tr>	
							<tr>
								<td align="right">Name:</td>
								<td><form:input path="creditCardInfo.nameOnCreditCard" size="20" maxlength="30"/><br>
								</td>
							</tr>	
							<tr>
								<td align="right">Card Number:</td>
								<td><form:input path="creditCardInfo.creditCardNumber" size="20" maxlength="16"/><br>
								</td>
							</tr>	
							<tr>
								<td align="right">Expiration Date:</td>
								<td><form:select path="creditCardInfo.expirationMonth">
									<form:option value="1" label="Jan(1)" />
									<form:option value="2" label="Feb(2)" />
									<form:option value="3" label="Mar(3)" />
									<form:option value="4" label="Apr(4)" />
									<form:option value="5" label="May(5)" />
									<form:option value="6" label="Jun(6)" />
									<form:option value="7" label="Jul(7)" />
									<form:option value="8" label="Sep(9)" />
									<form:option value="10" label="Oct(10)" />
									<form:option value="11" label="Nov(11)" />
									<form:option value="12" label="Dec(12)" />
								</form:select>
								<form:select path="creditCardInfo.expirationYear">
									<form:option value="2007" label="2007" />
									<form:option value="2008" label="2008" />
									<form:option value="2009" label="2009" />
									<form:option value="2010" label="2010" />
									<form:option value="2011" label="2011" />
									<form:option value="2012" label="2012" />
									<form:option value="2013" label="2013" />
									<form:option value="2014" label="2014" />
									<form:option value="2015" label="2015" />
									<form:option value="2016" label="2016" />
								</form:select>
								</td>
							</tr>	
							<tr>
								<td></td>
								<td><form:checkbox path="useShippingAddressAsBillingAddress" onclick="javascript:showHideBillingAddress();"/>
								Use shipping address as billing address
								</td>
							</tr>	
							
						  </table>
						</div>
						<c:if test="${paymentInfo.paymentMethod=='CC' && !paymentInfo.useShippingAddressAsBillingAddress}" >
							<div id="billingInfo" class="formFields" style="display:block;">
						</c:if>
						<c:if test="${paymentInfo.paymentMethod=='CC' && paymentInfo.useShippingAddressAsBillingAddress}" >
							<div id="billingInfo" class="formFields" style="display:none;">
						</c:if>
						<c:if test="${paymentInfo.paymentMethod!='CC'}" >
							<div id="billingInfo" class="formFields" style="display:none;">
						</c:if>
						
						
							<table>
								<tr>
									<td align="right" width="150"><b>Billing Address</b></td>
									<td></td>
								</tr>
								<tr>
									<td align="right">Address Line1:</td>
									<td><form:input path="creditCardInfo.billingAddress.street1" size="50" maxlength="100"/></td>
								</tr>
								<tr>
									<td align="right">Address Line2:</td>
									<td><form:input path="creditCardInfo.billingAddress.street2" size="50" maxlength="100"/></td>
								</tr>
								<tr>
									<td align="right">City:</td>
									<td><form:input path="creditCardInfo.billingAddress.city" size="30" maxlength="30"/></td>
								</tr>
								<tr>
									<td align="right">State:</td>
									<td><form:input path="creditCardInfo.billingAddress.state" size="30" maxlength="30"/></td>
								</tr>
								<tr>
									<td align="right">Postal Code:</td>
									<td><form:input path="creditCardInfo.billingAddress.postalCode" size="10" maxlength="6"/></td>
								</tr>
							</table>
						</div>
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
 		  	      <td><a href="javascript:toggleLayer('h1');"><fmt:message key="help.payment.check.header" /></a><br>
 		  	          <div id='h1' class="helpContent"><fmt:message key="help.payment.check.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h1');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h2');"><fmt:message key="help.payment.draft.header" /></a><br>
 		  	          <div id='h2' class="helpContent"><fmt:message key="help.payment.draft.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h2');"><fmt:message key="help.close" /></a></div>
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
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h4');"><fmt:message key="help.payment.cc.header" /></a><br>
 		  	          <div id='h4' class="helpContent"><fmt:message key="help.payment.cc.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h4');"><fmt:message key="help.close" /></a></div>
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