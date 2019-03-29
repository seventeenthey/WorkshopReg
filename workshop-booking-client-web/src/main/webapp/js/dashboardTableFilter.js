$(document).ready(function () {
$('#workshopData').DataTable({
        "columnDefs": [
    { "type": "date", "targets": 2},
    { "type": "date", "targets": 4}
        ]});
$('.dataTables_length').addClass('bs-select');
});