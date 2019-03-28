/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

console.log("detailsManager");
var registeredStatus = document.getElementById("registeredStatus").innerText;

//TODO register/unregister btn
var registerBtn = document.getElementById("registerBtn");              
var unRegisterBtn = document.getElementById("unRegisterBtn");         
console.log(registerBtn.style);
console.log(unRegisterBtn);

if (registeredStatus == "Registered" || registeredStatus == "WaitListed"){
    registerBtn.style.display="none";
    unRegisterBtn.style.display="";
} else {
    registerBtn.style.display="";
    unRegisterBtn.style.display="none";
}

//TODO advanced options only shows if you are a facilitator OR a creator OF THIS WORKSHOP