<!DOCTYPE html>

<!--/studentaccess/registration/index.jsp-->

<!--
	Project: College Student Scheduler
	
	Student Registration - Main Page
	
	Author:  Bridger Maskrey
	Version: 1.0.0
	Date:    2015-01-28
-->

<%@ page import="edu.pti.students.itp262.bap.session.*" %>
<%@ page import="edu.pti.students.itp262.bap.session.beans.*" %>
<html>

<head>
	<title>Student Registration</title>
	<script type="text/javascript" src="/studentaccess/src/script/registration/validate.js"></script>
	<%@include file="/src/include/shared_metadata.html"%>
</head>

<body>

<div class="main">
	<form action="/studentaccess/registration/submit.do" method="post" id="mainForm">
		<div class="section">
			<div><h2>Student Registration</h2></div>
			<hr>
			<div class="section">
				<div><h3>Basic Information</h3></div>
				<hr>
				<div>
					Name<br>
					<input type="text" name="firstName" id="firstName" size="20" maxlength="15" placeholder="First Name">*
					<input type="text" name="middleInitial" id="middleInitial" size="2" maxlength="1" placeholder="MI">
					<input type="text" name="lastName" id="lastName" size="25" maxlength="20" placeholder="Last Name">*<br>
					<br>
					Address<br>
					<input type="text" name="stAddress1" id="stAddress1" size="50" maxlength="100" placeholder="Street Address Line 1">*<br>
					<input type="text" name="stAddress2" id="stAddress2" size="50" maxlength="100" placeholder="Street Address Line 2"><br>
					<input type="text" name="city" id="city" size="20" maxlength="20" placeholder="City">*
					<%@include file="/src/include/input/state_select.html"%>*
					<input type="text" name="zip" id="zip" size="10" maxlength="5" placeholder="Zip Code">*
				</div>
			</div>
			<div class="section">
				<div><h3>Contact Information</h3></div>
				<hr>
				<div>
					Phone Number<br>
					<input type="text" name="phoneNumber" id="phoneNumber" size="20" maxlength="14" placeholder="Phone Number">*<br>
					<br>
				</div>
			</div>
			<div class="section">
				<div><h3>Account Security</h3></div>
				<hr>
				<div>
				Password<br>
				<input type="password" name="password" id="passwordInput" size="20" placeholder="Password">*<br>
				<input type="password" name="passwordVerify" id="passwordVerify" size="20" placeholder="Verify Password">*
				</div>
			</div>
			<div>
				<!-- TODO: Change onClick to submitRegistration before release-->
				<input type="button" value="Register" id="submission" onClick="submitRegistration()">
				<input type="reset" value="Clear">
			</div>
			<div>
				<div id="validationFail" class="fail"></div>
					<span class="fail"><%= session.getAttribute("logInError") != null ? session.getAttribute("logInError") : "" %></span>
			</div>
		</div>
	</form>
</div>

</body>

</html>