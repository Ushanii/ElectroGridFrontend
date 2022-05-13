<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payments.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6">
<h1>Bill Payment</h1>
<form id="formPayment" name="formPayment">
 Account Number:
 <input id="accountNum" name="accountNum" type="text"
 class="form-control form-control-sm">
 <br> Payment Amount:
 <input id="amount" name="amount" type="text"
 class="form-control form-control-sm"> 
 <br> Payment Date:
 <input id="date" name="date" type="text"
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save"
 class="btn btn-primary">
 <input type="hidden" id="hidPaymentIDSave"
 name="hidPaymentIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divPaymentGrid">
 <%
 Payment paymentObj = new Payment();
 out.print(paymentObj.readPayment());
 %>
</div>
</div> </div> </div>
</body>
</html>