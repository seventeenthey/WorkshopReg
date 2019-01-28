<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies
    response.setHeader("X-Frame-Options", "DENY"); // Does not allow this page to be displayed in an iframe. This helps prevent clickjacking.
    response.setHeader("X-XSS-Protection: 1", "mode=block"); //xss reflected attack prevention
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="en">
    <head> 
        <meta charset="utf-8">      
        <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
        <meta name="viewport" content="width=device-width, initial-scale=1">                
        <title>Accommodation Request Form</title>  
        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">

        <!-- 
        The style element below is used to prevent clickjacking. It sets the the body's display property to none. 
        When the page loads, the script below the style element checks to make sure that this page is not being displayed in an
        iframe. If it isn't being displayed in an iframe, the style element is removed, thus displaying the body.
        If the page is being displayed in an iframe, we make the top window go to the iframe window's location.
        -->
        <style id="antiClickjack">body{display:none !important;}</style>

        <script type='text/javascript'>

            // Check to see if this window is the top window. If this window is the top window, i.e this window is not in an iframe,
            // remove the antiClickjack style element, which will result in the body being displayed.
            if (self === top)
            {
                var antiClickjack = document.getElementById("antiClickjack");
                antiClickjack.parentNode.removeChild(antiClickjack);
            } else
            {
                top.location = self.location;
            }
        </script>

    </head>
    <!----------------------------------------------->
    <body>
        <!-- Allow sr users / keyboard users to move directly to the request area -->
        <a href="#content" class="sr-only sr-only-focusable"><s:property value="getText('dashboard.skip.content')" /></a>

        <div class="container banner-row"> <!-- banner row -->
            <p class="h3"> <s:property value="getText('general.title.h3')" /> </p>
            <p class="h1"> <s:property value="getText('general.title.h1')" /> </p>
        </div> <!-- /banner row -->
        <div class="container main-container request-container"> <!-- main container -->
            <nav class="navbar navbar-toggleable navbar-light justify-content-center" style="background-color: #fff;">
                <button class="navbar-toggler navbar-toggle-left" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span> <s:property value="getText('general.btn.menu')" /> 
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav mx-auto w-100 justify-content-around">
                        <s:url action="dashboard" var="home"></s:url>
                        <a class="nav-item nav-link mr-auto hover-focus-border" id="nav-newrequest" href="<s:property value="#home"/>"><s:property value="getText('request.btn.home')" /></a>
                        <s:url action="logoutAction" var="logout"></s:url>
                        <a class="nav-item nav-link hover-focus-border" id="nav-logout" href="<s:property value="#logout"/>"><s:property value="getText('general.btn.logout')" /></a>
                    </div>
                </div>
            </nav>
            <div class="row"> <!-- body row -->
                <div class="col-sm-4 col-md-3 col-xl-2 info-col"> <!-- info col -->
                    <div class="row personal-info-row"> <!--personal info row-->
                        <table class="personal-info-table table-sm">
                            <thead>
                                <tr>
                                    <th scope="col" class="side-header"><s:property value="getText('general.pinfo.title')" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th class="hidden-header" scope="row"><s:property value="getText('general.pinfo.name')" /></th>
                                    <td><s:property value='#session["COMMON-NAME"]' /></td>
                                </tr>
                                <tr>
                                    <th class="hidden-header" scope="row"><s:property value="getText('general.pinfo.email')" /></th>
                                    <td><s:property value='#session["QUEENSU-MAIL"]' /></td>
                                </tr>
                                <tr>
                                    <th class="hidden-header" scope="row"><s:property value="getText('general.pinfo.program')" /></th>
                                    <td class="program-td"><s:property value='#session["QUEENSU-PROGRAM"]' /></td>
                                </tr>
                            </tbody>
                        </table>
                    </div> <!-- /personal info row -->

                    <div class="row study-period-row"> <!--study period row-->
                        <table class="study-period-table table-sm">
                            <thead>
                                <tr>
                                    <th class="side-header"><s:property value="getText('general.speriod.title')" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th class="hidden-header" scope="row"><s:property value="getText('general.speriod.termtitle')" /></th>
                                    <td><s:property value='#session["QUEENSU-TERM"]' /></td>
                                </tr>
                            </tbody>
                        </table>
                    </div> <!-- /study period row -->
                </div> <!-- /info col -->

                <div class="col-sm-8 col-md-9  col-xl-10 body-col"> <!-- body col -->
                    <h1 class="text-center body-title"> <s:property value="getText('request.body.title')" /> </h1>
                    <div id="request-form">
                        <s:form id="requestForm" action="submitRequest">
                            <div class="row justify-content-center">
                                <div class="col-8">
                                    <div id="actionError">
                                        <s:actionerror  escape="false"/>
                                    </div>
                                    <div id="course-formgroup" class="form-group">
                                        <s:if test="reviewInstructor==null">
                                            <label for="courseSelect"><strong><s:property value="getText('request.form.course')" /><span class="required"><s:property value="getText('request.label.required')" /></span></strong></label>
                                            <s:select headerKey="-1" headerValue="Select course"
                                                      list="courses" class="form-control"
                                                      name="req.courseSection" id="courseSelect"
                                                      oninput="displayOtherCourse"/>
                                        </s:if>
                                        <s:else>
                                            <label for="courseSelect"><strong><s:property value="getText('request.form.course')" /></strong></label>
                                            <input id="courseSelect" value="<s:property value="codeSection"/>" name="req.courseSection" class="form-control formReadOnly" type="text" readonly>
                                        </s:else>

                                        <div class="form-control-feedback" id="course-feedback"></div>
                                    </div>
                                    <!-- /course form group -->
                                    <div class="form-group" id="otherCourse-formgroup" hidden="hidden">
                                        <label for="otherCourse"><s:property value="getText('request.form.specify.course')" /> <span class="required"><s:property value="getText('request.label.required')" /></span></label>
                                        <input value="<s:property value="req.courseTxt"/>" name="req.courseTxt" type="text" class="form-control" id="otherCourse"  data-toggle="tooltip" data-placement="top" title="ex: MUSC363-001">

                                        <div class="form-control-feedback" id="otherCourse-feedback"></div>
                                        <s:fielderror fieldName="othercourse"/>
                                    </div>
                                    <!-- /otherCourse -->
                                    <br>
                                    <div id="instructor-formgroup" class="form-group">
                                        <s:if test="reviewInstructor==null">
                                            <label for="instructorSelect"><strong><s:property value="getText('request.form.instructor')" /> <span class="required"><s:property value="getText('request.label.required')" /></span></strong></label>
                                            <s:select headerKey="-1" headerValue="Select instructor"
                                                      list="instructors" class="form-control"
                                                      name="req.instructor" id="instructorSelect"
                                                      oninput="displayOtherInstructor"/>
                                        </s:if>
                                        <s:else>
                                            <label for="instructorSelect"><strong><s:property value="getText('request.form.instructor')" /></strong></label>
                                            <input id="instructorSelect" value="<s:property value="reviewInstructor"/>" name="req.instructor" class="form-control formReadOnly" type="text" readonly>
                                        </s:else>
                                        <div class="form-control-feedback" id="instructor-feedback"></div>
                                    </div>
                                    <!-- /instructor form group -->
                                    <div class="form-group" id="otherInstructor-formgroup" hidden="hidden">
                                        <label for="otherInstructor"><s:property value="getText('request.form.specify.instructor')" /><span class="required"><s:property value="getText('request.label.required')" /></span></label>
                                        <input value="<s:property value="req.instructorTxt"/>" name="req.instructorTxt" type="text" class="form-control" id="otherInstructor"  data-toggle="tooltip" data-placement="top" title="ex: John Smith">
                                        <div class="form-control-feedback" id="otherInstructor-feedback"></div>
                                        <s:fielderror fieldName="otherinstructor"/>
                                    </div>
                                    <!-- /other instructor -->
                                    <br>
                                </div> <!-- /col-8-->
                            </div> <!--/row-->
                            <div class="row justify-content-center">
                                <div class="col-8">
                                    <s:if test="approvedTests==null">
                                        <fieldset id="date-fieldset">
                                            <legend><s:property value="getText('request.form.test.dates')" /> <span class="required"><s:property value="getText('request.label.required.date')" /></span></legend>
                                            <!--date 1-->
                                            <div class="test-dates form-group row " id="date1-formgroup">
                                                <div class="col-1 align-self-center">
                                                    <span class="count-column"> <s:property value="getText('request.form.1')" /> </span>
                                                </div>
                                                <div class="col-8 col-xl-10 col-lg-10">
                                                    <label id="date1lbl" for="date1" class="sr-only"><s:property value="getText('request.form.date.1')" /></label>
                                                    <input class="form-control date-input" type="text" id="date1" data-toggle="tooltip"  data-placement="top" title="format: YYYY-MM-DD">
                                                </div>
                                                <div class="col-1 align-self-center remove1">
                                                    <button aria-hidden="true" type="button" id="removedate1" hidden="hidden" class="close" aria-label="Remove test 1 date and time" data-toggle="tooltip" data-placement="top" title="Remove">
                                                        <span >&times;</span>
                                                    </button>
                                                </div>
                                                <div class="offset-1 form-control-feedback text-left" id="date1-feedback"></div>
                                            </div>

                                            <!--/date 1-->
                                            <!-- add date -->
                                            <div id="addDate-formgroup" class="form-group row ">
                                                <div class="col-1 align-self-center">
                                                    <span class="count-column"><s:property value="getText('request.form.2')" /></span>
                                                </div>
                                                <div class="col-8 col-xl-10 col-lg-10">
                                                    <label id="addDatelbl" for="addDate" class="sr-only"><s:property value="getText('request.form.adddate')" /></label>
                                                    <input id="addDate" class="add-date-input form-control" type="text" tabindex="0"  value="" placeholder="Add date...">
                                                </div>
                                                <div class="col-1 align-self-center remove2">
                                                </div>
                                                <div class="offset-1 form-control-feedback text-left" id="addDate-feedback"></div>
                                            </div>
                                        </fieldset>
                                    </s:if>
                                    <s:else>
                                        <fieldset id="date-fieldset">
                                            <legend>Test date(s) <span class="required"><s:property value="getText('request.label.required.date')" /></span></legend>
                                            <s:iterator value="approvedTests" status="incr">
                                                <div class="test-dates form-group row " id="testdates-formgroup">
                                                    <div class="col-1 ">
                                                        <span class="align-self-center count-column"> <s:property value="%{#incr.index+1}"/> </span>
                                                    </div>
                                                    <div class="col-8 col-xl-10 col-lg-10">
                                                        <input id="testcheck%{#incr.index+1}" aria-label="test date" readonly class="form-control instr-dates" type="text" value="<s:property value="testDate"/>">
                                                    </div>
                                                    <div class="col-1 align-self-center checkBoxes">
                                                        <s:checkbox name="selectedTests" class="large-check" id="check%{#incr.index+1}" fieldValue="%{testDate}" value="true" label="%{testDate}" onclick="checkSelectedTests(check%{#incr.index+1})"/>
                                                    </div>
                                                </div>
                                            </s:iterator>
                                            <div class="offset-1 form-control-feedback text-left" id="testdates-feedback"></div>
                                        </fieldset>
                                    </s:else>
                                </div><!--/col-8-->
                            </div> <!--/row-->

                            <div class="row justify-content-center">
                                <button class="btn btn-secondary" id="requestSubmit" type="button">Submit accommodation request</button>
                                
                                
                                <!--s:submit
                                    value="Submit accommodation request"
                                    class="btn btn-secondary"
                                    id="requestSubmit"
                                    type="button"
                                    /-->
                            </div>
                            <s:hidden name="req.dates" id="dates"/>
                            <s:hidden name="otherCourseError" id="otherCourseError"/>
                            <s:hidden name="otherInstructorError" id="otherInstructorError"/>
                            <s:hidden name="courseError" id="courseError"/>
                            <s:hidden name="instructorError" id="instructorError"/>
                            <s:hidden name="dateError" id="dateError" />
                            <s:hidden name="instrSubmitted" id="instrSubmitted" />
                        </s:form>
                    </div> <!-- /request-form -->
                </div> <!-- /body col -->
            </div> <!-- /body row -->

        </div> <!-- /main container -->
        <!--------------------------------------------->
        <footer class="footer" id="footer">
            <div class="container-fluid copyright">
                <div class="row justify-content-end">
                    <div class="registrar-foot col-lg-3 col-md-3 col-xl-2 col-sm-4 col-xs-12">
                        <span><s:property value="getText('reg.foot.1')" /></span>
                        <span><s:property value="getText('reg.foot.2')" /></span>
                        <span><s:property value="getText('reg.foot.3')" /></span>
                        <span><s:property value="getText('reg.foot.4')" /></span>
                        <span><s:property value="getText('reg.foot.5')" /></span>
                        <span><s:property value="getText('reg.foot.6')" /></span>
                    </div>
                    <div class="exams-office-foot col-lg-2 col-md-2 col-xl-2 col-sm-3 col-xs-12">
                        <span><s:property value="getText('eo.foot.1')" /></span>
                        <span><s:property value="getText('eo.foot.2')" /></span>
                        <span><s:property value="getText('eo.foot.3')" /></span>
                        <span><a href="mailto:exams@queensu.ca"><s:property value="getText('eo.foot.4')" /></a></span>
                    </div>
                    <div class="logo-foot col-lg-2 col-md-2 col-xl-2 col-sm-3 col-xs-12">
                        <img class="logo-img" src="images/QueensLogo_white.png" alt=""> 
                    </div>

                </div>
            </div>
        </footer>
        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/request.js"></script>
        <!-- End JS -->
    </body>
</html>
