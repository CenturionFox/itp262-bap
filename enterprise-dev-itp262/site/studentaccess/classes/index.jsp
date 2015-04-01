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
<%@ page import="edu.pti.students.itp262.bap.data.*"%>
<%@ page import="edu.pti.students.itp262.bap.session.beans.*" %>
<html>

<head>
	<title>Student Registration</title>
	<script type="text/javascript" src="/studentaccess/src/script/registration/validate.js"></script>
	<script>
	function enableDisableCheckbox()
	{
		var classtype = document.getElementById("classType").value;
		var gened = "<%= CourseType.GEN_ED.toString() %>";
		
		document.getElementById("genEdSwitch").disabled = classtype == gened || classtype == "All";
		if(document.getElementById("genEdSwitch").disabled)
		{
			document.getElementById("genEdSwitch").checked = true;
		}
	}
	</script>
	<%@include file="/src/include/shared_metadata.html"%>
</head>

<body>

<div class="main">
	<div class="section">
		<div><h2>Class Signup</h2></div>
		<hr>
		<form action="/studentaccess/classes/search.do" method="post">
			<div>Search:
				<select id="classType" name="classType" onChange="enableDisableCheckbox()" onLoad="enableDisableCheckbox()">
					<%
					
					for(CourseType ct : CourseType.values())
					{
						out.println("<option value=\"" + ct.toString() + "\">" + ct.toString() + "</option>");
					}
					
					%>
					<option value="All">All Courses</option>
				</select>
				<input type="submit">
			</div>
			<div><input id="genEdSwitch" type="checkbox" value="Include Gen-Ed Classes" name="incGed" value="incGed" checked>Include GenEd Classes</input></div>
		</form>
	</div>
	<div class="section">
		<div><h2>Search Results</h2></div>
		<hr>
		<div>
			<i>No results.</i>
		</div>
	</div>
</div>

</body>

</html>