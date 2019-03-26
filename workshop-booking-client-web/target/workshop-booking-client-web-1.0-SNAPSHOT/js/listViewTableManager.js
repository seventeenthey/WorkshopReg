/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
*
*function filterTable() {
    var input, filter, i;
    input = document.getElementById("searchKey");
    filter = input.value.toUpperCase();
    
    var events = $('.event')
    for (i = 0; i < events.length; i++) {
        if(events[i].innerText.toUpperCase().indexOf(filter) > -1)
            events[i].style.display = "";
        else
            events[i].style.display = "none";
    }
}
*/

//find elements
var rwTable, crTable, rwMSG, crMSG;
rwTable = document.getElementById("registeredWorkshops");
crTable = document.getElementById("createdWorkshops");
rwMSG = document.getElementById("noRegWorkshopsMsg");
crMSG = document.getElementById("noCreatedWorkshopsMsg");

var numRegistered = rwTable.getElementsByTagName("tr").length -1;   //the <thead> <tr> will count as 1
var numCreated = crTable.getElementsByTagName("tr").length -1;

//if there are no workshops registered for, then show message
if (numRegistered <= 0){
    rwTable.style.display="none";
    rwMSG.style.display="";
} else {
    rwTable.style.display="";
    rwMSG.style.display="none";
}

//if there are no workshops created, then show message
if (numCreated <= 0){
    crTable.style.display="none";
    crMSG.style.display="";
} else {
    crTable.style.display="";
    crMSG.style.display="none";
}