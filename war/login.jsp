<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>


<%-- this form-login-page form is also used as the 
         form-error-page to ask for a login again.
         --%>
    <%String param = request.getParameter("login_error");
      String lastUser = (String)session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY);
      //If last username is null (first time login), set username to empty string. We do not want to
      //display a null in UI
      if (lastUser == null)
        lastUser = "";
      
    %>
<%@page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>

<%@ include file="/WEB-INF/jsp/public/common/searchBarWithoutSearch.jsp"%>

<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
<td colspan="2" valign="top" align="left"><h1><fmt:message key="member.sign.in" /></h1></td>
</tr>
<tr>
<td valign="top">
	<form id="loginForm" method="POST" action="j_acegi_security_check">
      <table width="300" cellpadding="0" cellspacing="0" border="0" >
		
		<tr>
		<td><img src="<c:url value="/images/user_left1.gif"/>"></td>
		<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
		<td><img src="<c:url value="/images/user_right1.gif"/>"></td>
		</tr>
		<tr>
		<td colspan="3" valign="top" align="left" class="TDroundTable">
		<%
	      if (param != null) {
	    %>
	      <font color="red">
	        &nbsp;&nbsp;<fmt:message key="account.login.failure" />
		  </font>
	    <% } %>
		</td>
		</tr>
		<tr>
		<td class="TDroundTable">&nbsp;</td>
		<td class="TDroundTable">
			<table>
		        <tr><td align="right"><fmt:message key="login.email.id" />:</td><td><input type='text' name='j_username' id='j_username' value="<%=lastUser%>" maxlength="50" size="30"/></td></tr>
		        <tr><td align="right"><fmt:message key="login.password" />:</td><td><input type='password' name='j_password' maxlength="30" size="30"/></td></tr>
		        <tr><td align="right"><input type="checkbox" checked="checked" name="_acegi_security_remember_me"></td><td><fmt:message key="remember.me.message" /></td></tr>
		
		        <tr><td></td><td><input name="submit" type="submit" value="Login" /></td></tr>
		        <tr><td></td><td><b><fmt:message key="not.a.member" /> <a href="<c:url value="/adduser.htm"/>">Click Here</a></b></td></tr>
		        
		    </table>
 		</td>
		<td class="TDroundTable">&nbsp;</td>
		</tr>
		<tr>
		<td ><img src="<c:url value="/images/user_left2.gif"/>"></td>
		<td class="TDroundTable"><img src="<c:url value="/images/1pixel_blue.gif"/>" width="300" height="1"></td>
		<td ><img src="<c:url value="/images/user_right2.gif"/>"></td>
		</tr>
	  </table><br clear="all"><br />	
 
    </form>
</td>

<td valign="top">
	
</td>
</tr>
</table><br clear="all"><br />	
<!--########## Main Content table End ##########-->

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>
<script type="text/javascript">
  document.getElementById('j_username').focus();
</script>