<!DOCTYPE html>
<!--/studentaccess/index.jsp-->

<!--
	Project:  College Student Scheduler
	
	Main Page
	
	Author:  Bridger Maskrey
	Version: 1.0.0
	Date:    2015-01-28
-->

<%@ page import="edu.pti.students.itp262.bap.session.*" %>
<%@ page import="edu.pti.students.itp262.bap.session.beans.*" %>
<html>

<head>
	<title>Temp Main Page</title>
	
	<script>
		function regButtonClick()
		{
			window.location = "./registration/";
		}
		function signupButtonClick()
		{
			window.location = "./classes/";
		}
		function mgmtButtonClick()
		{
			window.location = "./profile/";
		}
	</script>
	<script type="text/javascript" src="/studentaccess/src/script/login/validate.js"></script>
	
	<%@include file="/src/include/shared_metadata.html"%>
</head>

<body>
<div class="main">
<%
	String userName = "";
	String id = "";
	Object sessionVar = session.getAttribute("cert");
	Certificate cert = null;
	if(sessionVar != null && sessionVar instanceof Certificate)
	{
		cert = (Certificate)sessionVar;
		userName = cert.getName();
		id = cert.getId();
	}
%>

<div class="section">
	<div><h2><%= cert != null ? "Welcome, " + userName + "!" : "You are not logged in." %></h2></div><br>
	<hr>
	<div>
		<%= cert != null ? "Your ID is " + id + ".<br><br>" : "" %>
		<form method="post" action="/studentaccess/login.do" id="mainLoginForm">

		<%

		if(cert == null)
		{
			out.println("\tStudent ID: <input type=\"text\" id=\"userName\" maxlength=\"5\" name=\"userName\"><br>");
			out.println("\tPassword: <input type=\"password\" id=\"passwordInput\" maxlength=\"20\" name=\"passwordInput\"><br>");
			out.println("\t<input type=\"button\" value=\"Log In\" onClick=\"submitLogin()\">");
			out.println("\t<input type=\"button\" value=\"Register...\" onClick=\"regButtonClick()\">");
		}
		else
		{
			out.println("<input type=\"button\" value=\"Student Profile\" onClick=\"mgmtButtonClick()\">");
			out.println("<input type=\"button\" value=\"Class Signup\" onClick=\"signupButtonClick()\">");
			out.println("<input type=\"submit\" value=\"Log Out\">");
		}
		%>

		</form>
	</div>
</div>

<div>
	<div id="validationFail" class="fail"></div>
	<span class="fail"><%= session.getAttribute("logInError") != null ? session.getAttribute("logInError") : "" %></span>
</div>	
	
</div>
</body>

</html>