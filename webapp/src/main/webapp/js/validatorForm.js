function validateForm() {
  var name = document.getElementsByName("computerName")[0].value;
  var introduced = document.getElementsByName("introduced")[0].value;
  var discontinued = document.getElementsByName("discontinued")[0].value;
  
  if (name == "") {
    alert("Name is mandatory");
    return false;
  }
  
  if(introduced != "" & discontinued != ""){
	 introducedDate= new Date(introduced);
	 discontinuedDate= new Date(discontinued);
	  if(discontinuedDate<introducedDate){
		  alert("Introduced date must be before discontinued date");
		  return false;
	  }
  }
  
} 