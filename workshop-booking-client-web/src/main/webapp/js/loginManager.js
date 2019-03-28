/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var loggedIn = document.getElementById('loggedIn');
var loggedInElements = $('.loggedIn');  //elements that require being logged in

if (loggedIn != null){
    var loggedInValue = loggedIn.innerText;
    var loginBtn = document.getElementById('loginBtn');
    
    if (loggedInValue == "true"){
        for(var i = 0; i < loggedInElements.length; i++)
            loggedInElements[i].style.display = "";
        loginBtn.innerText = "Logout";
    }
     else {
        for(var i = 0; i < loggedInElements.length; i++)
            loggedInElements[i].style.display = "none";
        loginBtn.innerText = "Login";
    }
}

