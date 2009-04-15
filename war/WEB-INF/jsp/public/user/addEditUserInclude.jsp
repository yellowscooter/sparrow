				<tr>
					<td align="right">First Name*:</td>
					<td align="left"><form:input path="firstname" size="30" maxlength="30"/></td>
				</tr>
				<tr>
					<td align="right">Last Name*:</td>
					<td align="left"><form:input path="lastname" size="30" maxlength="30"/></td>
				</tr>
				<tr>
					<td align="right">Gender:</td>
					<td align="left">Male: <form:radiobutton path="gender" value="M"/>&nbsp;&nbsp;
        				Female: <form:radiobutton path="gender" value="F"/></td>
				</tr>
				<tr>
					<td align="right">Age Group:</td>
					<td align="left"><form:select path="ageGroup">
						<form:option value="" label="-----" />
						<form:option value="<18" label="<18" />
						<form:option value="18-25" label="18-25"/>
						<form:option value="26-35" label="26-35"/>
						<form:option value="36-50" label="36-50"/>
						<form:option value=">50" label=">50"/>
					</form:select></td>
				</tr>
				<tr>
					<td align="right">Phone No*:</td>
					<td align="left"><form:input path="phone" size="15" maxlength="12"/></td>
				</tr>
				<tr>
					<td align="right">Mobile Phone No:</td>
					<td align="left"><form:input path="mobilePhone" size="15" maxlength="12"/></td>
				</tr>
				<tr>
					<td align="right">Location*:</td>
					<td align="left">
						<form:select path="city">
						  <form:option value="" label="--- Select your location ---"/>
						  <form:options items="${cityList}" itemValue="cityId" itemLabel="city"/>
						</form:select>
					</td>
				</tr>
				
				<tr>
					<td><b>Shipping Address</b></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">Address Line1*:</td>
					<td align="left"><form:input path="shippingAddress.street1" size="50" maxlength="100"/></td>
				</tr>
				<tr>
					<td align="right">Address Line2:</td>
					<td align="left"><form:input path="shippingAddress.street2" size="50" maxlength="100"/></td>
				</tr>
				<tr>
					<td align="right">City*:</td>
					<td align="left"><form:input path="shippingAddress.city" size="30" maxlength="30"/></td>
				</tr>
				<tr>
					<td align="right">State*:</td>
					<td align="left"><form:input path="shippingAddress.state" size="30" maxlength="30"/></td>
				</tr>
				<tr>
					<td align="right">Postal Code*:</td>
					<td align="left"><form:input path="shippingAddress.postalCode" size="10" maxlength="6"/></td>
				</tr>
