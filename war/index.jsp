<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="com.sparrow.domain.User"%>
<%@page import="com.sparrow.service.util.GeneralUtils"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends of Books</title>
</head>
<%
  User currentUser = GeneralUtils.getCurrentLoggedInUserFromTLSWithoutReload();
%>
<% if (currentUser != null) {%>
<!--  redirect to homepage -->
<c:redirect url="/browseCatalog.htm"/>
<%} else {%>
<c:redirect url="/welcome.htm"/>
<% } %>
