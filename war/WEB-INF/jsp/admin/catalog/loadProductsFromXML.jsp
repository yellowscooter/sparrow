<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3><fmt:message key="load.products"/></h3>
					</td>
					<td></td>
				</tr>
				<tr>
					<td><A href="<c:url value="/privileged/admin/loadproducts.htm?upload=true"/>"><fmt:message key="load.products"/></A></td>
					<td></td>
				</tr>

			</table>
			</td>
			<td>
			</td>
		</tr>
</table>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>