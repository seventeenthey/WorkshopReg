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

$(document).ready(function () {
    $('#registeredWorkshops').DataTable({
        paging: false,
        info: false,
        "columnDefs": [
            {"type": "date", "targets": 2},
            {"type": "date", "targets": 4}
        ]});

    $('#pastWorkshops').DataTable({
        paging: false,
        info: false,
        "columnDefs": [
            {"type": "date", "targets": 2},
            {"type": "date", "targets": 4}
        ]});

    $('#createdWorkshops').DataTable({
        paging: false,
        info: false,
        "columnDefs": [
            {"type": "date", "targets": 2},
            {"type": "date", "targets": 4}
        ]});
    $('.dataTables_length').addClass('bs-select');

    //find elements
    var rwTable, crTable, pstTable, rwMSG, crMSG, pstMSG, isRegisteredEmpty, isCreatedEmpty, isPastEmpty;
    rwTable = document.getElementById("registeredWorkshops");
    crTable = document.getElementById("createdWorkshops");
    pstTable = document.getElementById("pastWorkshops");
    rwMSG = document.getElementById("noRegWorkshopsMsg");
    crMSG = document.getElementById("noCreatedWorkshopsMsg");
    pstMSG = document.getElementById("noPastWorkshopsMsg");

    //if there are no workshops registered for, then show message
    isRegisteredEmpty = rwTable.getElementsByClassName("dataTables_empty").length != 0;
    if (isRegisteredEmpty) {
        rwTable.style.display = "none";
        document.getElementById("registeredWorkshops_filter").style.display = "none";
        rwMSG.style.display = "";
    } else {
        rwTable.style.display = "";
        rwMSG.style.display = "none";
    }

    //if there are no workshops created, then show message
    isCreatedEmpty = crTable.getElementsByClassName("dataTables_empty").length != 0;
    if (isCreatedEmpty) {
        crTable.style.display = "none";
        document.getElementById("createdWorkshops_filter").style.display = "none";
        crMSG.style.display = "";
    } else {
        crTable.style.display = "";
        crMSG.style.display = "none";
    }
    
    //if there are no past workshops attended, then show message
    isPastEmpty = pstTable.getElementsByClassName("dataTables_empty").length != 0;
    if (isPastEmpty) {
        pstTable.style.display = "none";
        document.getElementById("pastWorkshops_filter").style.display = "none";
        pstMSG.style.display = "";
    } else {
        pstTable.style.display = "";
        pstMSG.style.display = "none";
    }
});
