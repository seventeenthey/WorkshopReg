/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var input = document.getElementById("searchKey");

input.addEventListener("keyup", function(event){
    
    if(event.keyCode === 13){   //if "enter" is pressed
        window.location = "/apps/workshopbooking/client/dashboard?searchKey=" + input.value;
    }
});