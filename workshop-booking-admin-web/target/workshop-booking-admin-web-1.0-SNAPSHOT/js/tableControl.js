/* 
 * Controls table and row addition
 */


$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
    var actions = $("table td:last-child").html();
    // Append table with add row form on add new button click
    $(".add-new").click(function () {
        $(this).attr("disabled", "disabled");
        var index = $("table tbody tr:last-child").index();
        var row = '<tr>' +
                '<td><input type="text" class="form-control" name="name" id="name"></td>' +
                '<td><input type="text" class="form-control" name="department" id="department"></td>' +
                '<td><input type="text" class="form-control" name="phone" id="phone"></td>' +
                '<td>' + actions + '</td>' +
                '</tr>';
        $("table").append(row);
        $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
        $('[data-toggle="tooltip"]').tooltip();
    });
    //TODO -- click to add new row AND new role
    $(".add").click(function () {});
    // Confirm edit row on confirm button click
    $(document).on("click", ".confirm", function () {
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function () {
            if (!$(this).val()) {
                $(this).addClass("error");
                empty = true;
            } else {
                $(this).removeClass("error");
            }
        });
        $(this).parents("tr").find(".error").first().focus();
        if (!empty) {
            input.each(function () {
                $(this).parent("td").html($(this).val());
            });
            $(this).parents("tr").find(".confirm, .edit").toggle();
            $(".add-new").removeAttr("disabled");
        }
    });
    // Edit row on edit button click
    $(document).on("click", ".edit", function () {
        //$(this).parent("tr").find("td:not(:last-child)")
        $(this).parents("tr").first().find("td:not(:last-child)").each(function () {
            $(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
        });
        $(this).parents("tr").find(".confirm, .edit").toggle();
        $(".add-new").attr("disabled", "disabled");
    });
    // Delete row on delete button click
    $(document).on("click", ".delete", function () {
        //$(this).parents("tr").first().remove();
        //$(".add-new").removeAttr("disabled");
    });


});

function filterTable() {
    var input, filter, table, tr, name, number, i, nameTxtValue, numberTxtValue, columns;
    input = document.getElementById("searchKey");
    filter = input.value.toUpperCase();
    table = document.getElementById("userTable");
    if (table == null)
        table = document.getElementById("workshopTable");

    tr = table.getElementsByTagName("tr");

    for (i = 1; i < tr.length; i++) {
        if (tr[i].id != "ignore") {
            columns = tr[i].getElementsByTagName("td");
            name = columns[0];
            number = columns[1];

            if (name || number) {
                nameTxtValue = name.textContent || name.innerText;
                numberTxtValue = number.textContent || number.innerText;

                if (nameTxtValue.toUpperCase().indexOf(filter) > -1 || numberTxtValue.indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
}
    