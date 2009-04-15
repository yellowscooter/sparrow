<%@ include file="/WEB-INF/jsp/public/common/header.jsp"%>
<%@ include file="/WEB-INF/jsp/public/common/search.jsp"%>

<%@ taglib prefix="grid" uri="http://displaytag.sf.net"%>
<!--  Putting these styles here since we do not want all grids to inherit them -->
<style>
<!--
table.plans thead tr th {
	background-color: #eee;
}

table.plans tr.even {
	background-color: #fff;
}

table.plans {
	border: 1px solid black;
	border-collapse: collapse;
	
}

table.plans td {
	border: 1px solid black;
	padding: 10px;
	font-family: Verdana, Helvetica, Arial, sans-serif;
	font-weight: bold;
	border-right: none;
	
}
table.plans th {
	border: 1px solid black;
	padding: 10px;
	font-family: Verdana, Helvetica, Arial, sans-serif;
	font-weight: bold;
	border-right: none;
	text-align: center;
}
-->
</style>

<tr>
<td colspan="2">

<!--########## Main Content table Begin ##########--><br />
<table width="680" align="center" valign="top" align="left" class=".text" border="0">
<tr>
<td><h1><fmt:message key="howitworks.title" /></h1></td>
</tr>
<tr>
<td class="readingText">Renting books was never easier. Whether you read a few books a month, or a book a day, FriendsOfBooks&#0153; has a plan that's right for you.</td>
</tr>

<tr>
<td style="font-family: Verdana, Helvetica, Arial, sans-serif;font-size: 14px;font-weight: bold"><fmt:message key="howitworks.plans.cost" /></td>
</tr>
<tr>
  <td align="left">
  	<grid:table name="requestScope.subscriptionPlanList" 
  			pagesize="100" class="plans" decorator="com.sparrow.web.common.FaqDecorator">
		<grid:setProperty name="paging.banner.all_items_found" value="" />
       	<grid:setProperty name="paging.banner.onepage" value="" />
       	<grid:setProperty name="paging.banner.one_item_found" value="" />
		<grid:setProperty name="paging.banner.placement" value="bottom" />
		<grid:column property="name" title="Plan" />
		<grid:column property="period" title="Subscription Period" style="text-align: center;" />
		<grid:column property="maxRentalsPerMonth" title="Books per month" style="text-align: center;"/>
		<grid:column property="fee" title="Subscription Fee(Rs)" style="text-align: center; width: 80px;"/>
		<grid:column property="deposit" title="Refundable Deposit(Rs)" style="text-align: center; width: 100px;"  />
		
		
	</grid:table>
  </td>
</tr>
<tr>
<td style="font-family: Verdana, Helvetica, Arial, sans-serif;">
	Prices are inclusive of all applicable taxes.
	<br>There is no registration fee or any hidden charges. There is no late fee or due dates as long as you are a subscribing member 
	(i.e. you pay your monthly/six month/annual membership dues on time).<br>We deliver 2 books at a time - Delivery is free both ways in Delhi/NCR.
	Members outside Delhi/NCR must check out our <a href="<c:url value="/faq.htm#b12"></c:url>">FAQ</a> section regarding return of Books.
	<br><br>Example: <fmt:message key="howitworks.plan.example" />
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td class="readingText">

<b>So how does it work? It is quite

simple- Really!</b><br id=jc0m>

<br id=xahz>

Browse our collection online. Once you are impressed with our wide and varied

collection (We aim to please!), sign up (name, email id, address - the usual).<br id=yakw>

<br id=a_ov>
Select your preferred subscription plan.<br id=a_ov0>

<br id=j.3t>

Choose your trusted method of payment.<br id=n16j>

<br id=g5kz>

Click and add books to your "Bookshelf".

Bookshelf is the list of books that you want to read.

Within the next two days, two books from your bookshelf will

arrive at your doorstep.<br id=s2:2>

<br id=r-sd>

When you are done with the books, click on the "Ready for Pickup" button in "My

Bookshelf" tab.<br id=k67p>

<br id=pgtl>

We will pick up the books you done with and give you two more from your

bookshelf.<br id=dgv4>

&nbsp;<br id=oc5v>

We told you it was simple!<br id=kt7t>

<br id=g8l6>

<br id=v:6v>

<u id=ups_1>The nitty-gritty</u><br id=jpxe>

<br id=pzwh>

<ul id=tdnf0> 

  <li id=tdnf1>

    You can keep the books for as long as you want. No late fee, totally tension

    free!

  </li>

  <li id=tdnf2>

    Do not dog ear, tear, chew, whack flies/mosquitoes with the books, use them

    as coasters or turn pages with haldi stained fingers. We trust you are a

    Friend of Books and these are unthinkable thoughts to you! ( Gosh! ). Your

    security deposit is refundable to you at the end of your subscription period

    unless we find that any of the above-mentioned abuse (or ones more

    imaginative than the ones listed above) has been inflicted on our books.

  </li>

  <li id=tdnf3>

    Do not hit "Ready for Pickup" until you are really REALLY ready to send two

    books back. We will not pick up singles (books that is).

  </li>

  <li id=tdnf4>

    Do not be upset if the book at the top of your bookshelf is not

    available. Trust us, we are doing our very best to get it to you ASAP. While

    we search high and low for a copy of that book you will not be left

    stranded- we will send you the next available book from your bookshelf.

  </li>

  <li id=tdnf5>

    We always love to hear from you - The good, the bad, and the ugly et al.

    Email us, call us or scribble us a note to send along when you return your

    next set of books.

  </li>

  <li id=tdnf6>

    That's about it for now but you will get emails from us every now and then.

    We suggest you read them and not hit delete on auto-pilot (It may contain book recommendations and precious information about your membership). 
    In the same spirit, we promise (hereby solemnly swear) that we shall not spam you.

  </li>

</ul>


<br id=z6ug>

Cheers to all Friends of Books!<br id=w66j>

<br id=hytz>

</td>
</tr>
</table>	
</td>
<td valign="top"><%//GoogleAdsenseHere%><br><br>
</td>
</tr>

<%@ include file="/WEB-INF/jsp/public/common/footer.jsp"%>