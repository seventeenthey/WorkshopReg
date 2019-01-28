$(document).ready(function () {
        
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    addHandlers();

    if (($("#dateError").val() !== "noneInstr") && ($("#dateError").val() !== "")) {
        postValidate();
    }
    //checks if we are review a request
    //if we are, we need to turn off the client side validation on the inputs
    if (typeof ($("#courseSelect").attr('readonly')) !== 'undefined') {
        $("#courseSelect").off();
        $("#instructorSelect").off();
    }

    $(".date-error").on("blur", checkDuplicateDates);

    $("form :input").on('keyup keypress', function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });
        
    
    $("#requestSubmit").one("click", function(){
        fillDates();
        $(requestForm).submit();
    });
    
});

function checkDuplicateDates() {
    //all the dates currently in inputs
    var values = [];
    $(".date-input").each(function () {
        values.push($(this).val());
    });


    $('.date-error').each(function ( ) {
        var targetID = $(this).attr("id");
        //for each date with an error
        x = $(this).val();
        //count how many times the date's value occurs
        numOccurences = $.grep(values, function (elem) {
            return elem === x;
        }).length; // Returns 2 if there is a duplicate

        var dateRegEx = new RegExp('^[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}$');

        if (dateRegEx.test($(this).val())) {
            if ((numOccurences === 1)) {
                $("#" + targetID + "-formgroup").removeClass("has-danger");
                $("#" + targetID + "-formgroup").addClass("has-success");

                //set success on the actual input : form-control-success
                $(this).removeClass("form-control-danger");
                $(this).addClass("form-control-success");
                $(this).removeClass("date-error");

                $(this).removeAttr("aria-invalid");

                //add error
                $("#" + targetID + "-feedback").html("");
            }
            //invalid date - not unique
            else {
                $("#" + targetID + "-formgroup").removeClass("has-success");
                $("#" + targetID + "-formgroup").addClass("has-danger");

                //set danger on the actual input : form-control-success
                $(this).removeClass("form-control-success");
                $(this).addClass("form-control-danger");
                $(this).addClass("date-error");

                $(this).attr("aria-invalid", "true");

                //add error
                $("#" + targetID + "-feedback").html("Date occurs more than once - please choose unique dates");
            }
        } else {
            $("#" + targetID + "-formgroup").removeClass("has-success");
            $("#" + targetID + "-formgroup").addClass("has-danger");

            //set danger on the actual input : form-control-success
            $(this).removeClass("form-control-success");
            $(this).addClass("form-control-danger");
            $(this).addClass("date-error");

            $(this).attr("aria-invalid", "true");

            //add error
            $("#" + targetID + "-feedback").html("Date is in incorrect format or does not exist, must be YYYY-MM-DD");
        }

    });
}

function checkSelectedTests(id) {
    if ($("[type=checkbox]:checked").length === 0) {
        id.checked = true;
        alert("You must select at least one test date");
    }
}

function focusDate(i) {
    i += 1;
    $("#date" + i).focus();
}

function addHandlers() {
    //prevent user from adding a new date if they have a date with an error
    $("#addDate").on("input", function () {
        if ($("#date1").val() === "") {
            $("#addDate-formgroup").addClass("has-danger");
            $("#addDate").addClass("form-control-danger");
            $("#addDate-feedback").html("Must enter a date before you can add another one");
        } else if ($(".date-error").length === 0) {
            $("#addDate").off("input");
            createNewDate();
        } else {
            $("#addDate-formgroup").addClass("has-danger");
            $("#addDate").addClass("form-control-danger");
            $("#addDate-feedback").html("Must fix incorrect dates before you can add another");
        }

        if ($(".date-input").length === 15) {
            $("#addDate-formgroup").addClass("has-danger");
            $("#addDate").addClass("form-control-danger");
            $("#addDate-feedback").html("You have reached the maximum number of test dates");
        }

    });

    $("#courseSelect option[value='-1']").attr('disabled', 'disabled');
    $("#instructorSelect option[value='-1']").attr('disabled', 'disabled');

    $("#removedate1").on("click", removeDate);

    $("#courseSelect").on("change", checkCourseSelect);
    //$("#courseSelect").on("blur", checkCourseSelect);

    $("#instructorSelect").on("change", checkInstructorSelect);
    //$("#instructorSelect").on("blur", checkInstructorSelect);

    $("#otherCourse").on("blur", checkOtherCourse);

    $("#otherInstructor").on("blur", checkOtherInstructor);

    $("#courseSelect").on("change", displayOtherCourse);

    $("#instructorSelect").on("input", displayOtherInstructor);

    $(".date-input").on("blur", checkDate);
}

//Checks the results of the server side validation and applies the proper styles,
//error messages, and shows/hides what should be visible/hidden
function postValidate() {

    //if there are errors on the page, set focus to the error message
    //to inform screen reader users of the errors that require attention
    if ($(".errorMessage").length) {
        $(".errorMessage").focus();
    }

    if ($("#courseSelect").val() === "Other") {
        $("#otherCourse-formgroup").removeAttr("hidden");
    }
    if ($("#instructorSelect").val() === "Other") {
        $("#otherInstructor-formgroup").removeAttr("hidden");
    }

    //if server side validation found an error for the course-section 
    if ($("#courseError").val() === "course error") {
        $("#course-formgroup").removeClass("has-success");
        $("#course-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#courseSelect").removeClass("form-control-success");
        $("#courseSelect").addClass("form-control-danger");

        $("#courseSelect").attr("aria-invalid", "true");

        //add error
        $("#course-feedback").html("Please select a course-section");
    } else if ($("#courseError").val() === "valid") {
        onCourseChange();
        $("#course-formgroup").removeClass("has-danger");
        $("#course-formgroup").addClass("has-success");

        //set success on the actual input : form-control-success
        $("#courseSelect").removeClass("form-control-danger");
        $("#courseSelect").addClass("form-control-success");

        $("#courseSelect").removeAttr("aria-invalid");

        //add error
        $("#course-feedback").html("");
    }
    //if server side validation found an error for the 'other course' 
    if ($("#otherCourseError").val() === "other error") {
        $("#courseSelect").val("Other");
        $("#otherCourse-formgroup").removeAttr("hidden");

        $("#otherCourse-formgroup").removeClass("has-success");
        $("#otherCourse-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#otherCourse").removeClass("form-control-success");
        $("#otherCourse").addClass("form-control-danger");

        $("#otherCourse").attr("aria-invalid", "true");

        //add error
        $("#otherCourse-feedback").html("Please specify a course-section");
    } else if ($("#otherCourseError").val() === "other format error") {
        $("#courseSelect").val("Other");
        $("#otherCourse-formgroup").removeAttr("hidden");

        $("#otherCourse-formgroup").removeClass("has-success");
        $("#otherCourse-formgroup").addClass("has-danger");

        $("#otherCourse").removeClass("form-control-success");
        $("#otherCourse").addClass("form-control-danger");

        $("#otherCourse").attr("aria-invalid", "true");

        //add error
        $("#otherCourse-feedback").html("Invalid course-section format (Expected format is MUSC123-001 or MUSC123A-001)");
    } else if ($("#otherCourseError").val() === "valid") {
        $("#otherCourse-formgroup").removeClass("has-danger");
        $("#otherCourse-formgroup").addClass("has-success");

        $("#otherCourse").removeClass("form-control-danger");
        $("#otherCourse").addClass("form-control-success");

        $("#otherCourse").removeAttr("aria-invalid");

        //add error
        $("#otherCourse-feedback").html("");
    }

    //if server side validation found an error for the 'other course' 
    if ($("#instructorError").val() === "instructor error") {

        $("#instructor-formgroup").removeClass("has-success");
        $("#instructor-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#instructorSelect").removeClass("form-control-success");
        $("#instructorSelect").addClass("form-control-danger");

        $("#instructorSelect").attr("aria-invalid", "true");

        //add error
        $("#instructor-feedback").html("Please select an instructor");
    } else if ($("#instructorError").val() === "valid") {
        $("#instructor-formgroup").removeClass("has-daber");
        $("#instructor-formgroup").addClass("has-success");

        //set success on the actual input : form-control-success
        $("#instructorSelect").removeClass("form-control-danger");
        $("#instructorSelect").addClass("form-control-success");

        $("#instructorSelect").removeAttr("aria-invalid");

        //add error
        $("#instructor-feedback").html("");
    }
    //if server side validation found an error for the 'other instructor' 
    if ($("#otherInstructorError").val() === "other error") {
        $("#instructorSelect").val("Other");
        $("#otherInstructor-formgroup").removeAttr("hidden");

        $("#otherInstructor-formgroup").removeClass("has-success");
        $("#otherInstructor-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#otherInstructor").removeClass("form-control-success");
        $("#otherInstructor").addClass("form-control-danger");

        $("#otherInstructor").attr("aria-invalid", "true");

        //add error
        $("#otherInstructor-feedback").html("Please specify an instructor");
        //server side validation found illegal characters 
    } else if ($("#otherInstructorError").val() === "other illegal") {
        $("#instructorSelect").val("Other");
        $("#otherInstructor-formgroup").removeAttr("hidden");

        $("#otherInstructor-formgroup").removeClass("has-success");
        $("#otherInstructor-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#otherInstructor").removeClass("form-control-success");
        $("#otherInstructor").addClass("form-control-danger");

        $("#otherInstructor").attr("aria-invalid", "true");

        //add error
        $("#otherInstructor-feedback").html("Please specify an instructor without using invalid characters (~, #, @, *, +, %, {, }, <, >, [, ], |, “, ”, \, _, ^)");
    } else if ($("#otherInstructorError").val() === "valid") {
        $("#otherInstructor-formgroup").removeClass("has-danger");
        $("#otherInstructor-formgroup").addClass("has-success");

        //set success on the actual input : form-control-success
        $("#otherInstructor").removeClass("form-control-danger");
        $("#otherInstructor").addClass("form-control-success");

        $("#otherInstructor").removeAttr("aria-invalid");

        //add error
        $("#otherInstructor-feedback").html("");
    }

    // have to recreate the date fields that the user added before submitting the form
    // var dates = $('#dates').val().split(";");
    var obj = JSON.parse($('#dates').val());

    var dates = [];

    var j = 0;
    for (x in obj) {
        dates[j] = obj[x];
        j++;
    }
    var dateErrors = $('#dateError').val().split(";");

    $("#date1").val(dates[0]);

    if (dateErrors[0] === "none") {
        $("#date1-formgroup").removeClass("has-success");
        $("#date1-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#date1").removeClass("form-control-success");
        $("#date1").addClass("form-control-danger");
        $("#date1").addClass("date-error");
        $("#date1").attr("aria-invalid", "true");

        //add error
        $("#date1-feedback").html("Must input at least one date");
    }

    if (dates.length > 1) {
        $("#addDate-formgroup").remove();
        $("#removedate1").removeAttr("hidden");
        //create all the date fields for the previously entered dates
        for (i = 0; i < dates.length; i++) {
            n = i + 1;
            // for the first date, we can just set the value of the first date field
            // else, create a new date
            if (n === 1) {

            } else {

                $("#date-fieldset").append("<div class='test-dates form-group row' id='date" + n + "-formgroup'>" +
                        "<div class='col-1 align-self-center'>" +
                        "<span class='count-column'> " + n + " </span>" +
                        "</div>" +
                        "<div class='col-8 col-xl-10 col-lg-10'>" +
                        "<label id='date" + n + "lbl' for='date" + n + "' class='sr-only'>Test " + n + " date</label>" +
                        //"<input class='form-control date-input' type='text' id='date" + n + "' value='" + dates[i] + "' data-toggle='tooltip' placeholder='YYYY-MM-DD' data-placement='top' title='format: YYYY-MM-DD'>" +
                        "<input class='form-control date-input' type='text' id='date" + n + "' data-toggle='tooltip' placeholder='YYYY-MM-DD' data-placement='top' title='format: YYYY-MM-DD'>" +
                        "</div>" +
                        "<div class='col-1 align-self-center remove" + n + "'>" +
                        "<button aria-hidden='true' type='button' id='removedate" + n + "' class='close' aria-label='Remove test " + n + " date and time' data-toggle='tooltip' data-placement='top' title='Remove'>" +
                        "<span >&times;</span>" +
                        "</button>" +
                        "</div>" +
                        "<div class='offset-1 form-control-feedback text-left' id='date" + n + "-feedback'></div>" +
                        "</div>");

                $('#date' + n).val(dates[i]);
            }
        }
        $('#date-fieldset').append("<div class='form-group row ' id='addDate-formgroup'>" +
                "<div class='col-1 align-self-center'>" +
                "<span class='count-column'>" + (dates.length + 1) + "</span> </div>" +
                "<div class='col-8 col-xl-10 col-lg-10'>" +
                "<label id='addDatelbl' for='addDate' class='sr-only'>Add date</label>" +
                "<input id='addDate' class='add-date-input form-control' type='text' tabindex='0' placeholder='Add date...' value=''>" +
                "</div><div class='col-1 remove" + (dates.length + 1) + "'></div>" +
                "<div class='form-control-feedback offset-1' id='addDate-feedback'></div></div>");
        $(".close").tooltip();
        $(".close").on("click", removeDate);
        $(".date-input").off("blur");
        $(".date-input").on("blur", checkDate);
        $("#addDate").on("input", function () {
            if ($(".date-error").length === 0) {
                $("#addDate-formgroup").removeClass("has-danger");
                //set success on the actual input : form-control-success
                $("#addDate").removeClass("form-control-danger");
                //add error
                $("#addDate-feedback").html("");

                $("#addDate").off("input");
                createNewDate();
            } else {
                $("#addDate-formgroup").addClass("has-danger");
                //set success on the actual input : form-control-success
                $("#addDate").addClass("form-control-danger");
                //add error
                $("#addDate-feedback").html("Must fix incorrect dates before you can add another");
            }
        });
    }
    //add / remove styling for date validation
    for (i = 0; i < dateErrors.length; i++) {
        //possible values = valid, invalid, after term end, in the past, duplicate
        n = i + 1;
        switch (dateErrors[i]) {
            case "valid" :
                $("#date" + n + "-formgroup").removeClass("has-danger");
                $("#date" + n + "-formgroup").addClass("has-success");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-danger");
                $("#date" + n).addClass("form-control-success");
                $("#date" + n).removeClass("date-error");
                $("#date" + n).removeAttr("aria-invalid");


                //add error
                $("#date" + n + "-feedback").html("");
                break;
            case "invalid" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                //add error
                $("#date" + n + "-feedback").html("Date is in incorrect format or does not exist, must be YYYY-MM-DD");
                break;
            case "after term end" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                //add error
                $("#date" + n + "-feedback").html("This date occurs after the term ends - choose a date during the term");
                break;
            case "before term start" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                //add error
                $("#date" + n + "-feedback").html("This date ocurs before term start");
                break;
            case "in the past" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                //add error
                $("#date" + n + "-feedback").html("This date ocurs in the past - must choose future date");
                break;
            case "duplicate" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                //add error
                $("#date" + n + "-feedback").html("Date occurs more than once - please choose unique dates");
                break;
            case "already submitted" :
                $("#date" + n + "-formgroup").removeClass("has-success");
                $("#date" + n + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $("#date" + n).removeClass("form-control-success");
                $("#date" + n).addClass("form-control-danger");
                $("#date" + n).addClass("date-error");
                $("#date" + n).attr("aria-invalid", "true");

                $(':input').removeAttr('readonly');

                //add error
                $("#date" + n + "-feedback").html("Date has already been submitted");
                break;
        }
    }

}

//grab all of the dates that the user entered and put them in a hidden field so
//the bean can grab them
function fillDates() {
    if ($('.instr-dates').length > 0) {
        $('#dates').val("instr");
    } else {
        var obj = {};

        $(".date-input").each(function (index) {

            obj["date" + (index + 1)] = $(this).val();

        });

        var jsonString = JSON.stringify(obj);

        $('#dates').val(jsonString);

    }
}

//used on the date list to eliminate duplicate dates, this list is compared with
//the list of dates. If the list of dates is longer than the list without duplicates,
//then the user has enterred a duplicate date
var eliminateDuplicates = function (arr) {
    var i,
            len = arr.length,
            out = [],
            obj = {};

    for (i = 0; i < len; i++) {
        obj[arr[i]] = 0;
    }
    for (i in obj) {
        out.push(i);
    }
    return out;
};

//takes in the user date input, and splits it into the year, month, day values
//then creates a new Date object using the user's values. If the new object's year,
//month, and day match the user's year, month, and day - then the user entered a 
//real date. Javascript Date class forces the object to be a real date, so incorrect
//values get changed.
var validateDate = function (inDate) {
    var comp = inDate.split('-');
    var y = parseInt(comp[0], 10);
    var m = parseInt(comp[1], 10);
    var d = parseInt(comp[2], 10);
    var date = new Date(y, m - 1, d);
    if (date.getFullYear() === y && date.getMonth() + 1 === m && date.getDate() === d) {
        return true;
    } else {
        return false;
    }
};

var checkDate = function () {
    //ex 2011-08-19
    //var dateRegEx = new RegExp('^[2]{1}[0]{1}[0-9]{2}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}$');
    var dateRegEx = new RegExp('^[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}$');
    var illegalRegEx = /^.*?(?=[\^;#%&$\*:<>\?/\{\|\}]).*$/g;

    var targetID = $(this).attr("id");

    $(this).val($(this).val().trim());

    if (dateRegEx.test($(this).val())) {
        //invalid date - date does not exist
        if (!validateDate($(this).val())) {
            $("#" + targetID + "-formgroup").removeClass("has-success");
            $("#" + targetID + "-formgroup").addClass("has-danger");

            //set success on the actual input : form-control-success
            $(this).removeClass("form-control-success");
            $(this).addClass("form-control-danger");
            $(this).addClass("date-error");
            $(this).attr("aria-invalid", "true");

            //add error
            $("#" + targetID + "-feedback").html("Date does not exist - please enter a valid date");
        }

        //date exists
        else {
            var now = new Date();
            var dsplit = $(this).val().split("-");
            var d = new Date(dsplit[0], dsplit[1] - 1, dsplit[2]);

            //invalid date - occurs in the past
            if (d < now) {
                $("#" + targetID + "-formgroup").removeClass("has-success");
                $("#" + targetID + "-formgroup").addClass("has-danger");

                //set success on the actual input : form-control-success
                $(this).removeClass("form-control-success");
                $(this).addClass("form-control-danger");
                $(this).addClass("date-error");
                $(this).attr("aria-invalid", "true");

                //add error
                $("#" + targetID + "-feedback").html("This date occurs in the past - must choose future date");
            }
            //occurs in future
            else {
                var values = [];
                $(".date-input").each(function () {
                    values.push($(this).val());
                });

                x = $(this).val();

                numOccurences = $.grep(values, function (elem) {
                    return elem === x;
                }).length; // Returns 2

                //valid date - unique
                if (numOccurences === 1) {
                    $("#" + targetID + "-formgroup").removeClass("has-danger");
                    $("#" + targetID + "-formgroup").addClass("has-success");

                    //set success on the actual input : form-control-success
                    $(this).removeClass("form-control-danger");
                    $(this).addClass("form-control-success");
                    $(this).removeClass("date-error");

                    $(this).removeAttr("aria-invalid");

                    //add error
                    $("#" + targetID + "-feedback").html("");
                }
                //invalid date - not unique
                else {
                    $("#" + targetID + "-formgroup").removeClass("has-success");
                    $("#" + targetID + "-formgroup").addClass("has-danger");

                    //set danger on the actual input : form-control-success
                    $(this).removeClass("form-control-success");
                    $(this).addClass("form-control-danger");
                    $(this).addClass("date-error");

                    $(this).attr("aria-invalid", "true");

                    //add error
                    $("#" + targetID + "-feedback").html("Date occurs more than once - please choose unique dates");
                }
            }
        }
    }
    //invalid date - incorrect regex
    else {
        $("#" + targetID + "-formgroup").removeClass("has-success");
        $("#" + targetID + "-formgroup").addClass("has-danger");

        //set danger on the actual input
        $(this).removeClass("form-control-success");
        $(this).addClass("form-control-danger");
        $(this).addClass("date-error");

        $(this).attr("aria-invalid", "true");

        //add error
        $("#" + targetID + "-feedback").html("Date is in incorrect format, must be YYYY-MM-DD");
    }

    if ($(this).val() === "" && $(".date-input").length === 1) {
        $("#" + targetID + "-formgroup").removeClass("has-success");
        $("#" + targetID + "-formgroup").addClass("has-danger");

        //set danger on the actual input
        $(this).removeClass("form-control-success");
        $(this).addClass("form-control-danger");
        $(this).addClass("date-error");

        $(this).attr("aria-invalid", "true");

        //add error
        $("#" + targetID + "-feedback").html("Must input at least one date");
    }

    //if there are no errors - remove addDate error messages
    if ($(".date-error").length === 0) {
        $("#addDate-formgroup").removeClass("has-danger");
        //set success on the actual input : form-control-success
        $("#addDate").removeClass("form-control-danger");
        //add error
        $("#addDate-feedback").html("");
        //clear add date
        $("#addDate").val("");
    }

};

//display or hide other field
var displayOtherCourse = function () {
    if ($(this).val() === "Other") {
        $("#otherCourse-formgroup").removeAttr("hidden");
    } else {
        //if they select something other than "other", clear validation
        $("#otherCourse-formgroup").attr("hidden", "hidden");
        $("#otherCourse-formgroup").removeClass("has-danger");
        $("#otherCourse-formgroup").removeClass("has-success");
        $("#otherCourse").removeClass("form-control-danger");
        $("#otherCourse").removeClass("form-control-success");
        $("#otherCourse").removeAttr("aria-invalid");
        $("#otherCourse-feedback").html("");
        //clear field
        $("#otherCourse").val("");
    }
};

//display or hide other field
var displayOtherInstructor = function () {
    if ($(this).val() === "Other") {
        $("#otherInstructor-formgroup").removeAttr("hidden");
    } else {
        //if they select something other than "other", clear validation
        $("#otherInstructor-formgroup").attr("hidden", "hidden");
        $("#otherInstructor-formgroup").removeClass("has-danger");
        $("#otherInstructor-formgroup").removeClass("has-success");
        $("#otherInstructor").removeClass("form-control-danger");
        $("#otherInstructor").removeClass("form-control-success");
        $("#otherInstructor").removeAttr("aria-invalid");
        $("#otherInstructor-feedback").html("");
        //clear field
        $("#otherInstructor").val("");
    }
};

var checkOtherInstructor = function () {
    $("#otherInstructor").val($("#otherInstructor").val().trim());

    var illegalRegEx = /^.*?(?=[\^;#%&$\*:<>+@\\[\]=()_\?/\!{\|\}]).*$/g;

    //if the user has entered anything ...
    if ($("#otherInstructor").val() === "") {
        $("#otherInstructor-formgroup").removeClass("has-success");
        $("#otherInstructor-formgroup").addClass("has-danger");


        $("#otherInstructor").removeClass("form-control-success");
        $("#otherInstructor").addClass("form-control-danger");

        $("#otherInstructor").attr("aria-invalid", "true");

        //add error
        $("#otherInstructor-feedback").html("Please specify an instructor");
    } else if (illegalRegEx.test($("#otherInstructor").val())) {
        $("#otherInstructor-formgroup").removeClass("has-success");
        $("#otherInstructor-formgroup").addClass("has-danger");


        $("#otherInstructor").removeClass("form-control-success");
        $("#otherInstructor").addClass("form-control-danger");

        $("#otherInstructor").attr("aria-invalid", "true");

        //add error
        $("#otherInstructor-feedback").html("Cannot enter ^#%&$*:<>?{|}");
    } else {
        $("#otherInstructor-formgroup").removeClass("has-danger");
        $("#otherInstructor-formgroup").addClass("has-success");

        //set danger on the actual input : form-control-danger
        $("#otherInstructor").removeClass("form-control-danger");
        $("#otherInstructor").addClass("form-control-success");

        $("#otherInstructor").removeAttr("aria-invalid");

        //remove error message if there is one
        $("#otherInstructor-feedback").html("");
    }
};

var checkOtherCourse = function () {

    //ex MUSC101-001
    var courseRegEx = /^[A-Za-z]{2,4}[0-9]{3}[ABab]?-[0-9]{3}$/;
    var alternateCourseRegEx = /^[A-Za-z]{4}[Pp][0-9]{2}-[0-9]{3}$/;

    $("#otherCourse").val($("#otherCourse").val().trim());

    //check if user hasn't entered anything ...
    if ($("#otherCourse").val() === "") {
        $("#otherCourse-formgroup").removeClass("has-success");
        $("#otherCourse-formgroup").addClass("has-danger");

        //set success on the actual input : form-control-success
        $("#otherCourse").removeClass("form-control-success");
        $("#otherCourse").addClass("form-control-danger");

        $("#otherCourse").attr("aria-invalid", "true");

        //remove error message if there is one
        $("#otherCourse-feedback").html("Please specify a course-section");
    }
    //if the user's course code matches the regular expression
    else if (courseRegEx.test($("#otherCourse").val()) || alternateCourseRegEx.test($("#otherCourse").val())) {
        $("#otherCourse-formgroup").removeClass("has-danger");
        $("#otherCourse-formgroup").addClass("has-success");

        //set success on the actual input : form-control-success
        $("#otherCourse").removeClass("form-control-danger");
        $("#otherCourse").addClass("form-control-success");

        $("#otherCourse").removeAttr("aria-invalid");

        //remove error message if there is one
        $("#otherCourse-feedback").html("");
    } else {
        $("#otherCourse-formgroup").removeClass("has-success");
        $("#otherCourse-formgroup").addClass("has-danger");

        //set danger on the actual input : form-control-danger
        $("#otherCourse").removeClass("form-control-success");
        $("#otherCourse").addClass("form-control-danger");

        $("#otherCourse").attr("aria-invalid", "true");

        //add error message if there is one
        $("#otherCourse-feedback").html("Invalid course-section format (Expected format is MUSC123-001 or MUSC123A-001)");
    }
};

var checkInstructorSelect = function () {
    var target = $(this);
    //if they have made a choice, success
    if (target.prop('selectedIndex') > 0) {
        $("#instructor-formgroup").removeClass("has-danger");
        $("#instructor-formgroup").addClass("has-success");

        $("#instructorSelect").removeClass("form-control-danger");
        $("#instructorSelect").addClass("form-control-success");

        $("#instructorSelect").removeAttr("aria-invalid");

        //remove error message if there is one
        $("#instructor-feedback").html("");
    }
    //otherwise, fail
    else {
        $("#instructor-formgroup").removeClass("has-success");
        $("#instructor-formgroup").addClass("has-danger");

        $("#instructorSelect").removeClass("form-control-success");
        $("#instructorSelect").addClass("form-control-danger");

        $("#instructorSelect").attr("aria-invalid", "true");

        //set error message
        $("#instructor-feedback").html("Please select an instructor");
    }

};

var checkCourseSelect = function () {

    onCourseChange();

    var target = $(this);
    //if they have made a choice, success
    if (target.prop('selectedIndex') > 0) {
        $("#course-formgroup").removeClass("has-danger");
        $("#course-formgroup").addClass("has-success");

        $("#courseSelect").removeClass("form-control-danger");
        $("#courseSelect").addClass("form-control-success");

        $("#courseSelect").removeAttr("aria-invalid");

        //remove error message if there is one
        $("#course-feedback").html("");
    }
    //otherwise, fail
    else {
        $("#course-formgroup").removeClass("has-success");
        $("#course-formgroup").addClass("has-danger");

        $("#courseSelect").removeClass("form-control-success");
        $("#courseSelect").addClass("form-control-danger");

        $("#courseSelect").attr("aria-invalid", "true");

        //set error message
        $("#course-feedback").html("Please select a course-section");
    }

};

var removeDate = function () {
    var target = $(this);
    var targetID = target.attr("id");
    var targetDiv = targetID.substring(6) + "-formgroup";
    target.tooltip('hide');
    $("#" + targetDiv).remove();

    $(".count-column").each(function (index) {
        $(this).html(index + 1);
    });

    if ($('.test-dates').length === 1) {
        $(".close").attr("hidden", "hidden");
    }


    //if there are no errors - remove addDate error messages
    if ($(".date-error").length === 0) {
        $("#addDate-formgroup").removeClass("has-danger");
        $("#addDate").removeClass("form-control-danger");
        //add error
        $("#addDate-feedback").html("");
        $("#addDate").val("");
    }
};


var i = 1;

var createNewDate = function () {



    var numItems = $('.test-dates').length;

    $(".close").each(function () {
        $(this).removeAttr("hidden");
    });

    //var nextNum = numItems + 2;
    //var nextID = numItems + 1;
    var nextNum = i + 2;
    var nextID = i + 1;
    i++;

    //changes to current row
    $("#addDate").attr("id", "date" + nextID);
    $("#date" + nextID).removeClass("add-date-input");
    $("#addDate-feedback").attr("id", "date" + nextID + "-feedback");

    //remove id=addDateDiv and make the div a test-dates div
    $('#addDate-formgroup').addClass("test-dates");
    $('#addDate-formgroup').attr("id", "date" + nextID + "-formgroup");
    //add remove button to current row
    $(".remove" + nextID).append("<button type='button' id='removedate" + nextID + "' class='close' aria-label='Remove test " + nextID + " date'" +
            "data-toggle='tooltip' data-placement='top' title='Remove'>" +
            "<span aria-hidden='true'>&times;</span></button>");
    //remove the 'add date' attributes
    $("#date" + nextID).removeAttr("placeholder");
    $("#date" + nextID).removeAttr("tabindex");
    //add date-input class
    $("#date" + nextID).addClass("date-input");
    //add all the different attributes now
    $("#date" + nextID).attr("type", "text");
    $("#date" + nextID).attr("data-toggle", "tooltip");
    $("#date" + nextID).attr("data-placement", "top");
    $("#date" + nextID).attr("title", "format: YYYY-MM-DD");
    //turn on tooltips for new input and new remove
    $("#date" + nextID).tooltip('show');
    $("#removedate" + nextID).tooltip();
    $("#removedate" + nextID).on("click", removeDate);



    //change current row's hidden label to reflect this row is no longer 'add date'
    var newLbl = "date" + nextID + "lbl";
    $("#addDatelbl").attr("id", newLbl);
    $("#" + newLbl).html("Test " + nextID + " date");
    //create/add new date row to the date fieldset
    $('#date-fieldset').append("<div class='form-group row ' id='addDate-formgroup'>" +
            "<div class='col-1 align-self-center'>" +
            "<span class='count-column'>" + ($(".count-column").length + 1) + "</span> </div>" +
            "<div class='col-8 col-xl-10 col-lg-10'>" +
            "<label id='addDatelbl' for='addDate' class='sr-only'>Add date</label>" +
            "<input id='addDate' class='add-date-input form-control' type='text' tabindex='0' placeholder='Add date...' value=''>" +
            "</div><div class='col-1 remove" + nextNum + "'></div>" +
            "<div class='form-control-feedback offset-1' id='addDate-feedback'></div></div>");

    $("#addDate").on("input", function () {
        if ($(".date-error").length === 0) {
            $("#addDate-formgroup").removeClass("has-danger");
            $("#addDate").removeClass("form-control-danger");
            $("#addDate-feedback").html("");

            $("#addDate").off("input");
            createNewDate();
        } else {
            $("#addDate-formgroup").addClass("has-danger");
            //set success on the actual input : form-control-success
            $("#addDate").addClass("form-control-danger");
            //add error
            $("#addDate-feedback").html("Must fix incorrect dates before you can add another");
        }
    });


    $(".date-input").off("blur");
    $(".date-input").on("blur", checkDate);
};

var focusOtherCourse = function () {
    $("#otherCourse").focus();
};

var focusOtherInstructor = function () {
    $("#otherInstructor").focus();
};

var focusCourse = function () {
    $("#courseSelect").focus();
};

var focusInstructor = function () {
    $("#instructorSelect").focus();
};

var loadDash = function () {

};


function onCourseChange() {
    var course;
    if ($("#courseSelect").prop('selectedIndex') > 0) {
        if ($("#courseSelect").val() === "Other") {
            course = $("#otherCourse").val().trim();
        } else {
            course = $("#courseSelect").val();
        }
    }

    $.ajax({
        url: 'loadInstr',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        processdata: true,
        data: {courseName: course},
        success: function (data)
        {
            req = data.req;

            var $select = $('#instructorSelect');
            $select.find('option').remove();
            $('<option>').val("-1").text("Select instructor").appendTo($select);
            $("#instructorSelect option[value='-1']").attr('disabled', 'disabled');
            $.each(data.instructors, function (key, value) {
                $('<option>').val(value).text(value).appendTo($select);
            });

            $("#instructorSelect").val(req.instructor);

            $("#instructor-formgroup").removeClass("has-danger");
            $("#instructor-formgroup").addClass("has-success");

            $("#instructorSelect").removeClass("form-control-danger");
            $("#instructorSelect").addClass("form-control-success");

            $("#instructorSelect").removeAttr("aria-invalid");

            //remove error message if there is one
            $("#instructor-feedback").html("");

            //if they select something other than "other", clear validation
            $("#otherInstructor-formgroup").removeClass("has-danger");
            $("#otherInstructor-formgroup").removeClass("has-success");
            $("#otherInstructor").removeClass("form-control-danger");
            $("#otherInstructor").removeClass("form-control-success");
            $("#otherInstructor").removeAttr("aria-invalid");
            $("#otherInstructor-feedback").html("");
            //clear field
            $("#otherInstructor").val("");

            if ($("#instructorSelect").val() === "Other") {
                $("#otherInstructor-formgroup").removeAttr("hidden");
                $("#otherInstructor").focus();
            } else {
                $("#otherInstructor-formgroup").attr("hidden", "hidden");
            }

        }
    });
    return false;
}