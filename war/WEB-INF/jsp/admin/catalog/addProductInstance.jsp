<%@ include file="/WEB-INF/jsp/admin/common/adminheaderpopup.jsp"%>
<form:form commandName="productInstance" enctype="multipart/form-data">
<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3><!-- Add new -->
						<fmt:message key="addProductInstance" />
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td>Product Id:</td>
					<td><c:out value="${refData.product.productId}"/></td>
				</tr>
				<tr>
					<td>Title:</td>
					<td><c:out value="${refData.product.name}"/></td>
				</tr>
				<tr>
					<td>Author:</td>
					<td><c:out value="${refData.product.author.fullName}"/></td>
				</tr>
				<%//commenting out as company is not used at this time %>
				<!-- <tr>
					<td>Company:</td>
					<td><c:out value="${refData.product.company.companyName}"/></td>
				</tr> -->
				<%//commenting out as user submitted books are not allowed at this time %>
				<!-- <tr>
					<td>Submitted by UserName:</td>
					<td><form:input path="submittedBy" /></td>
				</tr> -->
				<tr>
					<td></td>
					<td>
						<spring:bind path="productInstance.product">
						  <input type="hidden" name='<c:out value="${status.expression}" />' 
						  			value='<c:out value="${refData.product.productId}"/>' /> 
						</spring:bind>
						<input type="submit" value="Save" /></td>
				</tr>
			</table>
			</td>
			<td>
			</td>
		</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>