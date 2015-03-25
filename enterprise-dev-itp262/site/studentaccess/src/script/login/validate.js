function submitLogin()
{
	var failOutput = document.getElementById("validationFail");
	
	failOutput.innerHTML = "<div>";
	
	var usnValid = validateSuppliedID();
	var passwordExtant = document.getElementById("userName").value != "";
	
	var fail = !usnValid || !passwordExtant;
	
	if(!passwordExtant)
	{
		failOutput.innerHTML += "Please supply a password.<br>";
	}
	
	failOutput.innerHTML += "</div>";
	
	if(!fail)
	{
		document.getElementById("mainLoginForm").submit();
	}
}

function validateSuppliedID()
{
	var failOutput = document.getElementById("validationFail");
	var usnVal = document.getElementById("userName").value;
	var fail = false;
	
	if(usnVal == "")
	{
		fail = true;
		failOutput.innerHTML += "Please supply a user ID.<br>";
	}
	else if(!/\S+\d/g.test(usnVal))
	{
		fail = true;
		failOutput.innerHTML += "Invalid user ID supplied.<br>";
	}

	return !fail;
}