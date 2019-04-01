var facilTable, facilMSG;
facilTable = document.getElementById("facilList");
facilMSG = document.getElementById("nofacilMsg");

//if there are no workshops registered for, then show message
isFacilitatorEmpty = facilTable.rows.length == 1;

if (isFacilitatorEmpty) {
    facilTable.style.display = "none";
    facilMSG.style.display = "";
} else {
    facilTable.style.display = "";
    facilMSG.style.display = "none";
}