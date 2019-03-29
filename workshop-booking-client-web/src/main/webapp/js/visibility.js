/*
 * 0 - attendee
 * 1 - facilitator
 * 2 - workshop creator
 * 3 - departmental admin
 * 4 - super admin
 * 
 */

var role = document.getElementById("role").innerText;


var superAdminElements = $('.superAdmin');
var departmentalAdminElements = $('.departmentalAdmin');
var workshopCreatorElements = $('.workshopCreator');
var facilitatorElements = $('.facilitator');
        
//if role is less than super admin, hide super admin elements
if (role < 5)
    hideElements(superAdminElements,5);

if (role < 4)
    hideElements(departmentalAdminElements,4);

if (role < 3)
    hideElements(workshopCreatorElements,3);

if (role < 2)
    hideElements(facilitatorElements,2);

function hideElements(elements, minRole){
    if(role < minRole)
        for(var i = 0; i < elements.length; i++)
            elements[i].style.display = "none";
}