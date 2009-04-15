<!-- Categories table begin -->
			<table width="178" cellpadding="0" cellspacing="0" border="0">
				<tr>
				    <!-- Rounded corners table, thanks to a great solution by http://www.spiffycorners.com -->
					<td valign="top" align="center">
						<div>
						  <b class="spiffy">
						  <b class="spiffy1"><b></b></b>
						  <b class="spiffy2"><b></b></b>
						  <b class="spiffy3"></b>
						  <b class="spiffy4"></b>
						  <b class="spiffy5"></b></b>
							<div class="spiffyfg">
							    <table cellpadding="0" cellspacing="0" border="0"><tr><td align="left">
							    <div style="height: 20px;"><b><a href="<c:url value="/authorlist.htm"/>"><fmt:message key="browse.by.author"/></a></b></div>
							    <div style="height: 20px;"><b><a href="<c:url value="/browseCatalog.htm?recentlyAdded=Y"/>"><fmt:message key="recently.added"/></a></b></div>
								<div style="height: 20px;"><b><fmt:message key="category.list"/></b></div>
								<grid:table name="sessionScope.categoryList"
												pagesize="50" requestURI="browseCatalog.htm"
												decorator="com.sparrow.web.catalog.CatalogCategoryListDecorator"
												cellpadding="0" cellspacing="0">
												<grid:setProperty name="basic.show.header" value="false" />
												<grid:setProperty name="paging.banner.all_items_found" value="" />
												<grid:setProperty name="paging.banner.onepage" value="" />
												<grid:setProperty name="paging.banner.one_item_found" value="" />
												<grid:column property="name" />
											</grid:table>
									</td></tr>
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
				</tr>
			</table>
			<!-- Categories table end -->