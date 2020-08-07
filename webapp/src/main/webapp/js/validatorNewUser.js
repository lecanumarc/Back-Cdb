function validateNewUser() {
  var name = document.getElementsByName("username")[0].value;
  var password = document.getElementsByName("password")[0].value;
  var passwordConfirm = document.getElementsByName("passwordConfirm")[0].value;
  
  if (name == "") {
    alert("Name is mandatory");
    return false;
  }
  
  if (password == "") {
	    alert("Password is mandatory");
	    return false;
	  }
  else if (password.length<5) {
		 alert("Password should have minimum 5 characters");
		 return false;}
  else if (password!=passwordConfirm){
		  alert("Passwords are not the same");
		  return false;
	  }
  
} 