<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="com.sp.model.Customer" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<form action="addNew" method="get">
<input type="hidden" name="token" value="<%=request.getAttribute("token")%>">
<div style=" display:flex">
<input style="background-color:aqua" type="submit" value="AddNewUser">
<h1 style="align:center; margin-left:500px">CustomerList</h1>
</div>
</form>
<hr style="border: solid">
<form>

<table class="table" >
<tr class="thead-dark">
<th>uuid</th>
<th>FirstName</th>
<th>LastName</th>
<th>Street</th>
<th>Address</th>
<th>State</th>
<th>City</th>
<th>Phone</th>
<th>Action</th>
</tr>

<%ArrayList<Customer> customerlist=(ArrayList<Customer>)request.getAttribute("customer");


for(Customer cus:customerlist)
{%>
<tr class="thead-light">
<td><%=cus.getUuid() %></td>
<td><%=cus.getFirst_name()%></td>
<td><%=cus.getLast_name()%></td>
<td><%=cus.getStreet()%></td>
<td><%=cus.getAddress()%></td>
<td><%=cus.getState()%></td>
<td><%=cus.getCity()%></td>
<td><%=cus.getPhone()%></td>
<td><a href="<%=request.getContextPath()%>/delete?uuid=<%=cus.getUuid()%>&token=<%=request.getAttribute("token")%>">delete</a> <a href="<%=request.getContextPath()%>/edit?uuid=<%=cus.getUuid()%>&token=<%=request.getAttribute("token")%>">edit</a></td>
</tr>
<%} %>
</table>
<%customerlist.clear();%>
</form>
</body>
</html>