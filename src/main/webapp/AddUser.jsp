<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.sp.model.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.body
{
margin:0;
padding-top:0;
}
.boxes
{
height:25px;
width:300px;
margin-top:30px;
border-radius:2px;
}
.button
{
background-color:aqua;
margin-top:10px;
}
</style>
</head>
<body>
<h2 style=" margin-left:650px">CustomerForm</h2>
<hr style="border:solid">
<%Customer c=(Customer)request.getAttribute("customer"); 


%>

<%if(c==null) {%>
<form action="add">
<input type="hidden" name="token" value="<%=request.getAttribute("token") %>">

<div style="width:500px;margin-left:570px;margin-top:100px">

<input type="text" class="boxes" name="FirstName" placeholder="firstname">
<br>
<input type="text" class="boxes"name="LastName" placeholder="latstname">
<br>
<input type="text" class="boxes"name="Street" placeholder="Street">
<br>
<input type="text"class="boxes" name="Address" placeholder="address">
<br>
<input type="text"class="boxes" name="City" placeholder="city">
<br>
<input type="text"class="boxes" name="State" placeholder="state">
<br>
<input type="email" class="boxes"name="Email" placeholder="email">
<br>
<input type="text" class="boxes"name="Phone" placeholder="phone">
<br>
<input type="submit" class="button" value="Adduser">

</div>
</form>
<%}

else
{%>
<form action="update">
	<div style="width:500px;margin-left:570px;margin-top:100px">

	<input type="text" class="boxes" name="FirstName" value=<%=c.getFirst_name() %> >
	<br>
	<input type="text" class="boxes"name="LastName" value=<%=c.getLast_name() %>>
	<br>
	<input type="text" class="boxes"name="Street" value=<%=c.getStreet() %>>
	<br>
	<input type="text"class="boxes" name="Address" value=<%=c.getAddress() %>>
	<br>
	<input type="text"class="boxes" name="City" value=<%=c.getCity() %>>
	<br>
	<input type="text"class="boxes" name="State"value=<%=c.getState() %>>
	<br>
	<input type="email" class="boxes"name="Email" value=<%=c.getEmail() %>>
	<br>
	<input type="text" class="boxes"name="Phone" value=<%=c.getPhone() %>>
	<br>
	
<input type="hidden" name="uuid" value="<%=c.getUuid() %>">
<input type="hidden" name="token" value="<%=request.getAttribute("token") %>">
<input type="submit" class="button" value="updateuser">
	</div>
	</form>
<%} %>

</body>
</html>