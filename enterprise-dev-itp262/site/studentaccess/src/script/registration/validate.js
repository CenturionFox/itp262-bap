function submitRegistration()
{
	var failOutput = document.getElementById("validationFail");
	
	failOutput.innerHTML = "<div>";
	
	var fieldsPresent = validateAllRequiredFields()
	var passwordValid = validatePassword();
	var comboValid = validateComboBoxes();
	var phoneValid = validatePhoneInput();
	
	failOutput.innerHTML += "</div>";
	
	if(passwordValid && comboValid && fieldsPresent && phoneValid)
	{
		document.getElementById("mainForm").submit();
	}
}

function validateAllRequiredFields()
{
	var failOutput = document.getElementById("validationFail");

	var fail = false;
	
	var fName = document.getElementById("firstName");
	var lName = document.getElementById("lastName");
	
	if(fName.value == "" || lName.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must enter a first and last name.<br>";
	}
	else
	{
		if(/\s+/.test(fName.value) || /\s+/.test(lName.value))
		{
			fail = true;
			failOutput.innerHTML += "Your name cannot contain whitespace characters.<br>";
		}
		
		if(/\d+/.test(fName.value) || /\d+/.test(lName.value))
		{
			fail = true;
			failOutput.innerHTML += "Your name cannot contain numbers.<br>";
		}
	}
	
	var addressLine1 = document.getElementById("stAddress1");
	
	if(stAddress1.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must supply an address.<br>";
	}
	else if(!/\d+\s[A-Za-z]+\s[A-Za-z]+/.test(stAddress1.value))
	{
		fail = true;
		failOutput.innerHTML += "The address supplied is invalid.<br>";
	}
	
	var city = document.getElementById("city");
	
	if(city.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must supply a city.<br>";
	}
	
	var zip = document.getElementById("zip");
	
	if(zip.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must supply a zip code.<br>";
	}
	else if(!/\d{5}/.test(zip.value))
	{
		fail = true;
		failOutput.innerHTML += "The supplied zip code is invalid.<br>";
	}
	
	var phoneNum = document.getElementById("phoneNumber");
	
	if(phoneNum.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must supply a phone number.<br>";
	}
	
	var passwordInput = document.getElementById("passwordInput");
	
	if(passwordInput.value == "")
	{
		fail = true;
		failOutput.innerHTML += "You must supply a password.<br>";
	}
	
	return !fail;
}

function validatePassword()
{
	var passInput = document.getElementById("passwordInput");
	var passValid = document.getElementById("passwordVerify");
	
	if(passInput.value == passValid.value)
	{
		return true;
	}
	
	document.getElementById("validationFail").innerHTML += "The password fields do not match.<br>";
	
	return false;
}

function validateComboBoxes()
{
	var state = document.getElementById("state");
	
	var fail = false;
	
	if(state.selectedIndex == 0)
	{
		fail = true;
		document.getElementById("validationFail").innerHTML += "You must select a state.<br>";
	}

	return !fail;
}

function validatePhoneInput()
{
	var phoneNum = document.getElementById("phoneNumber");
	var value = phoneNum.value.replace(/[-() ]+/g, "");
	var fail = false;
	
	console.log(value);
	
	if(!/\d{10}/.test(value))
	{
		fail = true;
		document.getElementById("validationFail").innerHTML += "Your phone number is invalid.";
	}
	
	return !fail;
}