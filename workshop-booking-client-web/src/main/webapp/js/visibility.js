/*
 * 0 - attendee
 * 1 - facilitator
 * 2 - workshop creator
 * 3 - departmental admin
 * 4 - super admin
 * 
 */

var role = document.getElementById("role").innerText;
console.log("role Id "+role);
if (document.getElementById("creatorAuth") !== null && document.getElementById("facilAuth")!== null){
    var creatorAuth = document.getElementById("creatorAuth").innerText;
    var facilAuth = document.getElementById("facilAuth").innerText;
    console.log("facilAuth "+facilAuth);
}

var superAdminElements = $('.superAdmin');
var departmentalAdminElements = $('.departmentalAdmin');
var workshopCreatorElements = $('.workshopCreator');
var facilitatorElements = $('.facilitator');
var creatorIdElements = $('.creatorIdCheck');
var facilIdElements = $('.facilIdCheck');

//if role is less than super admin, hide super admin elements
if (role < 5){
    hideElementsByRole(superAdminElements,5);
    }
    
if (role < 4){
    hideElementsByRole(departmentalAdminElements,4);
    }
    
if (role < 3){
    console.log("Hide Creator Element ");
    hideElementsByRole(workshopCreatorElements,3);
    }

if (role < 2){
    hideElementsByRole(facilitatorElements,2);
    }
    
function hideElementsByRole(elements, minRole){
    if(role < minRole)
        for(var i = 0; i < elements.length; i++)
            elements[i].style.display = "none";
}

/**
 * Add authantication check to NetId
 *  Users cannot access workshops not created by them / will not host by them
 */
if (creatorAuth<1){
    console.log("creator content hide");
    hideElements(creatorIdElements);
}

if (facilAuth<1){
    hideElements(facilIdElements);
    console.log("facil content hide");
}

function hideElements(elements){
    for(var i = 0; i < elements.length; i++)
        elements[i].style.display = "none";    
}