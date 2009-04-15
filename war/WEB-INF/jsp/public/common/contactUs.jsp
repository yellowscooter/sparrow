<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>

<%@ include file="/WEB-INF/jsp/public/common/searchBarWithoutSearch.jsp"%>
<c:url var="actionUrl"  value="/contact.htm"/>
<form:form commandName="email" action="${actionUrl}" >
<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" valign="top" align="left" border="0">
  <tr><td colspan="2"><h1><fmt:message key="contact.title" /></h1></td></tr>
  <tr>
	<td class="readingText" colspan="2">
	  Intrigued? Ask questions, share your thoughts, request a book, donate a book or just drop in a line to let us know how we can make FriendsOfBooks&#0153; better. 
	  You can fill the form below, email us at <b>cs@friendsofbooks.com</b> or pick up the phone and call us. 
	  We are always glad to hear from you!
	  
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
				<td></td>
				<td align="left">
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				</td>
		  	</tr>
		  	<c:if test="${message != null}" >
			  <tr>
			  	<td></td>
				<td align="left">
					<spring:message code="${message}" />
				</td>
			  </tr>
			</c:if>
			<tr>
				<td width="120" align="right">From*:</td>
				<td width="380" align="left"><form:input path="fromEmailId" size="30" maxlength="50" />&nbsp;(your email address)</td>
			</tr>
			<tr>
				<td align="right">Subject:</td>
				<td align="left"><form:input path="subject"  size="50" maxlength="100" /></td>
			</tr>
			<tr>
				<td align="right">Message*:</td>
				<td align="left"><form:textarea path="message" rows="10" cols="50" /></td>
			</tr>
			<tr>
			<td colspan="2" align="center"><br /><input type="submit" name="submit" value="Send email" /></td>
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
 		  	  <tr><td class="helpHeader">Contact Info</td></tr>
 		  	  <tr>
 		  	      <td><b>Customer Service Number:</b> 
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td>+91 93106 06880 
 		  	      </td>
 		  	  </tr>
 		  	  <tr>
 		  	      <td></td>
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

</table>	
</form:form>
<br><br>
</td>
<td valign="top"><%//GoogleAdsenseHere%><br><br>
</td>
</tr>
<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>