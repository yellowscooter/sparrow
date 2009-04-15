<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp" %>
<c:out value="${message}"></c:out>
<%//this javascript will refresh the parent window %>
<script type="text/javascript">
<!--
window.opener.location=window.opener.location;
//-->
</script>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp" %>