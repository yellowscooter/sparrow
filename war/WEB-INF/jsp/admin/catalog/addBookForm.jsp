<%@ include file="/WEB-INF/jsp/admin/common/adminheader.jsp"%>
<form:form commandName="product" enctype="multipart/form-data">
	<table>
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<h3><!-- Add new -->
						<c:if test="${product.new}"><fmt:message key="addProduct" /></c:if>
						<!-- Update -->
						<c:if test="${!product.new}"><fmt:message key="updateProduct" /></c:if></h3>
					</td>
					<td></td>
				</tr>
				<%@ include file="/WEB-INF/jsp/common/validationErrors.jsp"%>
				<tr>
					<td>Title:*</td>
					<td><form:input path="name" size="50" maxlength="255" /></td>
				</tr>
				<tr>
					<td>Description:*</td>
					<td><form:textarea path="description" cols="50" rows="12" /></td>
				</tr>
				<tr>
					<td></td>
					<td><fmt:message key="add.edit.product.desc.instructions" /></td>
				</tr>
				<tr>
					<td>Author:*</td>
					<td>
						<form:select path="author">
							<form:option value="-1" label="--Please Select" />
							<form:options items="${refData.authorsList}" itemValue="authorId"
									itemLabel="fullName" />
						</form:select>
					</td>
				</tr>
				<!-- Removed company dropdown from here. Company not used currently -->
				<tr>
					<td>Length(in):</td>
					<td><form:input path="length" /></td>
				</tr>
				<tr>
					<td>Width(in):</td>
					<td><form:input path="width" /></td>
				</tr>
				<tr>
					<td>Height(in):</td>
					<td><form:input path="height" /></td>
				</tr>
				<tr>
					<td>ISBN:*</td>
					<td><form:input path="isbn" maxlength="13" /></td>
				</tr>
				<!--  Format not used currently -->
				<!-- <tr>
					<td>Format:*</td>
					<td><form:select path="format">
						<form:option value="PAPERBACK" />
						<form:option value="HARDCOVER" />
					</form:select></td>
				</tr> -->
				<tr>
					<td>NumOfPages:*</td>
					<td><form:input path="numOfPages" /></td>
				</tr>
				<tr>
					<td>Small image:</td>
					<td><c:if test="${!product.new}"><c:out value="${product.imageSmallName}"/></c:if>
						<input type="file" name="smallImage" /></td>
				</tr>
				<tr>
					<td>Large image:</td>
					<td><c:if test="${!product.new}"><c:out value="${product.imageLargeName}"/></c:if>
						<input type="file" name="largeImage" /></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" value="Save" /></td>
				</tr>
			</table>
			</td>
			<td>
			  <table>
			    <tr>
					<td>Primary Category/Categories:<br>
							<c:forEach items="${refData.categories}" var="category">
							  <form:radiobutton path="primaryCategoryId" value="${category.categoryId}" />
							  <form:checkbox path="categories" value="${category.categoryId}" />
							  <c:out value="${category.name}"/>-<c:out value="${category.categoryType}"/><br>
							</c:forEach>
					</td>
				</tr>
			  </table>
			</td>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/jsp/admin/common/adminfooter.jsp"%>
