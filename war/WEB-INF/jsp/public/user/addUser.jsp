<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<script language="javascript" type="text/javascript" src='<c:url value="/js/divUtils.js"/>' ></script>
<script language="javascript" type="text/javascript" src='<c:url value="/js/prototype.js"/>'></script>


<form:form commandName="user">
<%@ include file="/WEB-INF/jsp/public/common/searchBarWithoutSearch.jsp"%>
<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="2" cellspacing="0" border="0">
<tr>
<td valign="top" colspan="2" align="left"><h1><fmt:message key="user.newUser" /></h1></td>
</tr>
<tr>
<td valign="top" colspan="2" align="left">
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
			
			<table width="500" align="center" cellpadding="3" cellspacing="0" border="0">
			<tr>
				<td width="120" align="right">Username*:</td>
				<td width="380" align="left"><form:input path="username" size="30" maxlength="50" />&nbsp;(email address)</td>
			</tr>
			<tr>
				<td align="right">Password*:</td>
				<td align="left"><form:password path="password"  size="30" maxlength="30" />&nbsp;<fmt:message key="user.password.minLength.info" /></td>
			</tr>
			<tr>
				<td align="right">Re-type Password*:</td>
				<td align="left"><form:password path="confirmPassword"  size="30" maxlength="30" /></td>
			</tr>
			<%@ include file="/WEB-INF/jsp/public/user/addEditUserInclude.jsp"%>
			<tr>
				<td></td>
				<td align="left"><A href="#" onclick="showTermsAndConditions('<c:url value="/html/termsAndConditions.html"/>');">Terms of Use</A><br>
					<form:checkbox path="acceptTerms" value="Y"/><fmt:message key="user.terms.acceptance" />
			    </td>
			</tr>
			<tr>
			<td colspan="2" align="center"><br /><input type="submit" name="submit" value="<fmt:message key="user.create.account" />" /></td>
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
 		  	      <td><a href="javascript:toggleLayer('h1');"><fmt:message key="help.registration.username.header" /></a><br>
 		  	          <div id='h1' class="helpContent"><fmt:message key="help.registration.username.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h1');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h2');"><fmt:message key="help.registration.phone.header" /></a><br>
 		  	          <div id='h2' class="helpContent"><fmt:message key="help.registration.phone.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h2');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h21');"><fmt:message key="help.registration.mobilephone.header" /></a><br>
 		  	          <div id='h21' class="helpContent"><fmt:message key="help.registration.mobilephone.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h21');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h3');"><fmt:message key="help.registration.location.header" /></a><br>
 		  	          <div id='h3' class="helpContent"><fmt:message key="help.registration.location.content" />
 		  	  		    <div class="helpClose"><a href="javascript:toggleLayer('h3');"><fmt:message key="help.close" /></a></div>
 		  	  		  </div>
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td><a href="javascript:toggleLayer('h4');"><fmt:message key="help.registration.shipping.header" /></a><br>
 		  	          <div id='h4' class="helpContent"><fmt:message key="help.registration.shipping.content" />
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
<script type="text/javascript">
  document.getElementById('username').focus();
</script>