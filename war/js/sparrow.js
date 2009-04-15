//file for some common javascript util functions

// return the value of the radio button that is checked
// return an empty string if none are checked, or
// there are no radio buttons
function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}


function trim (str) {
	str = str.replace(/^\s+/, '');
	for (var i = str.length - 1; i >= 0; i--) {
		if (/\S/.test(str.charAt(i))) {
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;
}

//open terms and conditions window
function showTermsAndConditions(strLink){
    window.open(strLink, 'termsAndConditions', 'toolbars=0,location=0,scrollbars=1,statusbars=0,menubars=0,resizable=0,width=550,height=400');
}


//validate a search criteria...this functions performs the same validations as SearchCriteriaValidator
//The idea is to catch an invalid search criterial at the browser itself
function validateSearchCriteria() {
  var strCriteria = document.getElementById("criteria").value;
  //alert('1' + strCriteria + '1');
  strCriteria = trim(strCriteria);
  //alert('2' + strCriteria + '2');
  
  if (strCriteria == '') {
  	return false;
  }
  
  if (strCriteria.length < 4) {
  	alert('Search criteria must have at least four characters.');
    return false;
  }
  //same string value as blank.search.msg of messages.properties 
  if (strCriteria == 'Search by Title, Author, Description') {
    return false;
  }
  
  return true;
}


function populateReviewerName() {
	if (document.getElementById('reviewerName').value == '') {
	  document.getElementById('reviewerName').value = document.getElementById('username').value;
	}
}


function addToQ(divOutput, url)
{
	document.getElementById('qid').value='item' + divOutput;
	$(document.getElementById('qid').value).update('<b>Adding to Bookshelf...</b>');
	
	new Ajax.Request(
		url, 
		{
			method: 'get', 
			onComplete: showResponse
			
		});
}

function showResponse(originalRequest)
{
	$(document.getElementById('qid').value).update(originalRequest.responseText);
}


