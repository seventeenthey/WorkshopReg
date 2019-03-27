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
if (role < 4)
    hideElements(superAdminElements,4);

if (role < 3)
    hideElements(departmentalAdminElements,3);

if (role < 2)
    hideElements(workshopCreatorElements,2);

if (role < 1)
    hideElements(facilitatorElements,1);

function hideElements(elements, minRole){
    if(role < minRole)
        for(var i = 0; i < elements.length; i++)
            elements[i].style.display = "none";
}