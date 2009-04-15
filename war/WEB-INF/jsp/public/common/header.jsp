<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<%@page import="com.sparrow.domain.User"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<html>
<head>
<%//<META NAME="Description" CONTENT="<spring:message code="${metaDescription}" text="${dynamicMetaDescription} Friends Of Books is a book rental library that delivers books in Delhi NCR, India. 
//Rent books without going to a bookstore or a library and get them delivered to your home. ${dynamicMetaDescription1}"/>">
//<META NAME="keywords" CONTENT="<c:out value="${dynamicMetaKeywords} FriendsOfBooks, Friends, Books, Library, deliver, rental, Delhi, India"></c:out>">
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META NAME="Description" CONTENT="<spring:message code="${metaDescription}" text="${dynamicMetaDescription} Friends Of Books is a book rental library that delivers books in Delhi NCR, India. "/>">
<META NAME="keywords" CONTENT="<c:out value="${dynamicMetaKeywords}"></c:out>">


<link rel="StyleSheet" href="<c:url value="/css/main.css"/>" type"text/css">
<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
<script language="javascript" type="text/javascript" src='<c:url value="/js/sparrow.js"/>'></script>
<%
  User currentUser = GeneralUtils.getCurrentLoggedInUserFromTLSWithoutReload();
%>
<% //Since header is a common include, page title needs to be dynamic to give a unique title to each page
   //This will help search engines in picking up the page
   //pageTitle message key can be specified in the controller (See AboutController.jave)
   //if no key is specified, the default value in text is used
   //dynamicPageTitle can be used to pass dynamic data which is not possible with pageTitle since page title is a message resource.
   //dynamicPageTitle can be dynamic product name, author name etc%>
<title><spring:message code="${pageTitle}" text="${dynamicPageTitle} FriendsOfBooks - Library that delivers" /></title>
<SCRIPT language="JavaScript" type="text/javascript">
<!-- Yahoo!
var ysm_accountid  = "1P5CV8PHJJ4H1RRT6Q8DHNKFC38";
document.write("<SCR" + "IPT language='JavaScript' type='text/javascript' " 
+ "SRC=//" + "srv3.wa.marketingsolutions.yahoo.com" + "/script/ScriptServlet" + "?aid=" + ysm_accountid 
+ "></SCR" + "IPT>");
// -->
</SCRIPT>
</head>
<body>
<table width="731" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
<td colspan="2" valign="top" align="left" bgcolor="#2D8CBC"><img src="<c:url value="/images/spacer.gif"/>" width="731" height="5"></td>
<td></td>
</tr>
<tr>
<td colspan="2" valign="top" align="left" bgcolor="#FF9900"><img src="<c:url value="/images/spacer.gif"/>" width="731" height="3"></td>
<td></td>
</tr>
<tr>
<td colspan="2" valign="top" align="left" bgcolor="#FFCC00"><img src="<c:url value="/images/spacer.gif"/>" width="731" height="2"></td>
<td></td>
</tr>

<tr>
<td rowspan=3 valign="top" align="left"><a href="<c:url value="/"/>"><img src="<c:url value="/images/logo.gif"/>" width="317" height="90" border="0" alt="Friends of Books, Library that delivers." title="Friends of Books, Library that delivers."></a></td>
<td valign="top" align="right" ><img src="<c:url value="/images/1pixel_white.gif"/>" width="1" height="6" ><br clear="all">
<% if (currentUser != null) {%>
  <div class="loggedInUserName">Hi!&nbsp;<%=currentUser.getFirstname()%></div>|
<% } %>
<% if (currentUser == null) {%>
<a href="<c:url value="/adduser.htm"/>">New User</a>|
<% } %>
<a href="<c:url value="/about.htm"/>"><fmt:message key="link.about.us" /></a>|
<a target="_blank" href="http://blog.friendsofbooks.com/"><fmt:message key="link.blog" /></a>|
<a href="<c:url value="/contact.htm"/>"><fmt:message key="link.contact.us" /></a>|
<a href="<c:url value="/faq.htm"/>"><fmt:message key="link.faq" /></a>
<% if (currentUser != null) {%>
|<a href="<c:url value="/j_acegi_logout"/>">Log Out</a>
<% } else { %>
|<a href="<c:url value="/login.jsp"/>"><fmt:message key="member.sign.in" /></a>
<% } %>
</td>
<td></td>
</tr>
<tr><td align="right" ><img src="<c:url value="/images/1pixel_white.gif"/>" width="1" height="6" ><br clear="all">
</td>
<td></td></tr>
<%
  String strTab = request.getParameter("tab");
  //if no tab is specified, set to 0 so nothing is selected
  if (strTab == null) {
    strTab = "0";
  }
  int tab = Integer.parseInt(strTab);
%>
<tr>
<td align="left" valign="bottom" width="414px">
<%
  String styleClass = "tabspiffy";
  String styleClassContent = "tabContent";
  if (tab == 1) {
    styleClass = "tabSelected";
    styleClassContent = "tabContentSelected";
  }
%>
<div class="lefttab">
 <b class="<%=styleClass%>">
 <b class="<%=styleClass%>1"><b></b></b>
 <b class="<%=styleClass%>2"><b></b></b>
 <b class="<%=styleClass%>3"></b>
 <b class="<%=styleClass%>4"></b>
 <b class="<%=styleClass%>5"></b></b>
	<div class="<%=styleClassContent%>">
	<a href="<c:url value="/browseCatalog.htm?tab=1"/>" class='tabText'><fmt:message key="tab.home" /></a>
	</div>
</div>
<%
  styleClass = "tabspiffy";
  styleClassContent = "tabContent";
  if (tab == 2) {
    styleClass = "tabSelected";
    styleClassContent = "tabContentSelected";
  }
%>

<div class="tab">
 <b class="<%=styleClass%>">
 <b class="<%=styleClass%>1"><b></b></b>
 <b class="<%=styleClass%>2"><b></b></b>
 <b class="<%=styleClass%>3"></b>
 <b class="<%=styleClass%>4"></b>
 <b class="<%=styleClass%>5"></b></b>
	<div class="<%=styleClassContent%>">
	<a href="<c:url value="/queue.htm?tab=2"/>" class='tabText'><fmt:message key="tab.bookshelf" /></a>
	</div>
</div>
<%
  styleClass = "tabspiffy";
  styleClassContent = "tabContent";
  if (tab == 3) {
    styleClass = "tabSelected";
    styleClassContent = "tabContentSelected";
  }
%>

<div class="tabHowitWorks">
<b class="<%=styleClass%>">
 <b class="<%=styleClass%>1"><b></b></b>
 <b class="<%=styleClass%>2"><b></b></b>
 <b class="<%=styleClass%>3"></b>
 <b class="<%=styleClass%>4"></b>
 <b class="<%=styleClass%>5"></b></b>
	<div class="<%=styleClassContent%>">
	<a href="<c:url value="/howitworks.htm?tab=3"/>" class='tabText'><fmt:message key="tab.how.it.works" /></a>
	</div>
</div>
<%
  styleClass = "tabspiffy";
  styleClassContent = "tabContent";
  if (tab == 4) {
    styleClass = "tabSelected";
    styleClassContent = "tabContentSelected";
  }
%>

<div class="righttab">
<b class="<%=styleClass%>">
 <b class="<%=styleClass%>1"><b></b></b>
 <b class="<%=styleClass%>2"><b></b></b>
 <b class="<%=styleClass%>3"></b>
 <b class="<%=styleClass%>4"></b>
 <b class="<%=styleClass%>5"></b></b>
	<div class="<%=styleClassContent%>">
	<a href="<c:url value="/myaccount.htm?tab=4"/>" class='tabText'><fmt:message key="tab.my.account" /></a>
	</div>
</div>

</td>
<td></td>
</tr>
