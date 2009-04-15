	//functions to do ajax login. This file will be included
	//in any jsp that uses ajax submissions.
    function getHTML(url)
	{
		new Ajax.Request(
			url, 
			{
				method: 'get', 
				onComplete: showResponse
			});
	}
	
  	function ajaxLogin(url) { 
    var opt = { 
        method: 'post', 
        postBody: Form.serialize($('loginForm')) + '&ajax=true', 
        onSuccess: function(response) { 
            var msg = response.responseText; 
            if ("error:" == msg.substr(0, 6)) { 
                getHTML('ajaxAcegilogin.jsp?login_error=1');
            } else if ("url:" == msg.substr(0, 4)) {
                getHTML(msg.substring(4, msg.length));
            }
        } 
    } 
    new Ajax.Request(url, opt); 
}
