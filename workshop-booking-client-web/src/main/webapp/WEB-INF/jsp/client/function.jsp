<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>


<!DOCTYPE html>
<html lang="en">
    <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Function</title>

            <!-- Bootstrap core CSS -->
            <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
            <!-- Custom styles for this template -->
            <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">       
            <!-- Bursary styles -->
            <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
            <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
            <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">

            <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
            <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <sx:head />
    </head>



    <body>
        <div class="jumbotron text-center banner-row mb-0">
                <h1>Workshop Management</h1>
        </div>

        <!-- Navigation Bar - INCLUDED IN EVERY .JSP FILE -->
        <nav class="navbar navbar-toggleable-md navbar-inverse bg-primary">
            <div class='navbar-brand'>Queen's ITS Workshop Registration</div>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <s:url action="dashboard" var="dashboardUrl" />
                    <a class="nav-link" href='<s:property value="dashboardUrl"/>'>Workshop Calendar</a>
                    <!--<a class="nav-link" href="#home">Home <span class="sr-only">(current)</span></a>-->
                </li>
                <li class="nav-item">
                    <s:url action="listViewLoadAction" var="listViewUrl" />
                    <a class="nav-link" href='<s:property value="listViewUrl"/>'>My Workshops</a>
                </li>
                <li class="nav-item">
                    <s:url action="functionLoadAction" var="functionUrl" />
                    <a class="nav-link" href='<s:property value="functionUrl"/>'>Create Workshop</a>
                </li>
                <li class="nav-item">
                    <div class="active-cyan-3 active-cyan-4">
                        <input class="form-control" type="text" placeholder="Search" aria-label="Search" id="searchKey"/>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Logout</a>
                </li>
            </ul>              
        </nav>

        <div class="function">
            <div class="row">
                <!--Nav bar for Function, included in every workshop function .jsp file-->
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-header">
                            Workshop Edit
                        </div>
                        <div class="card-body">
                            <ul class="nav flex-column">
                                <li class="nav-item">
                                    <s:url action="functionLoadAction" var="functionUrl" />
                                    <a href='<s:property value="functionUrl"/>'>Event Setup</a>
                                </li>
                                <li class="nav-item">
                                    <s:url action="facilitatorLoadAction" var="facilitatorUrl" />
                                    <a href='<s:property value="facilitatorUrl"/>'>Facilitators</a>
                                </li>
                                <li class="nav-item">
                                    <s:url action="emaileditLoadAction" var="emaileditUrl" />
                                    <a href='<s:property value="emaileditUrl"/>'>Message Center</a>
                                </li>
                                <li class="nav-item">
                                    <s:url action="attendanceLoadAction" var="attendanceUrl" />
                                    <a href='<s:property value="attendanceUrl"/>'>Attendance</a>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>

                <!--Workshop Information Gathering-->
                <div class="col-md-9">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    
                    <s:form id="workshopEditForm" action="functionExeAction" 
                            theme="bootstrap" method="post" cssClass="form-vertical">
                        
                        <s:fielderror/>
                        <s:radio 
                            label="Status:"
                            labelposition="inline"
                            name="workshopForm.status" 
                            list="statusList"
                            elementCssClass="col-sm-9"/>
                        
                        <s:textfield 
                            label="Event Title:"
                            placeholder="Event Title"
                            name="workshopForm.eventTitle"
                            tooltip="Enter Workshop Title Here"
                            cssClass="input-sm"
                            elementCssClass="col-sm-5"/>

                        <s:select 
                            label="Location:"
                            name="workshopForm.location" 
                            list="locationList"
                            elementCssClass="col-sm-5"/>

                        <s:textarea 
                            label="Teaser:"
                            name="workshopForm.teaser"
                            emptyOption="true"
                            elementCssClass="col-sm-9"/>


                        <s:textfield 
                            label="Maximum Participants:"
                            name="workshopForm.maxParticipant"
                            type="number"
                            placeholder="10"
                            elementCssClass="col-sm-2"/>

                        <s:textfield 
                            label="Wait List Limit:"
                            name="workshopForm.waitlistLimit"
                            type="number"
                            placeholder="0"
                            elementCssClass="col-sm-2"/>

                        <!--Registration Start/End Date/Time-->
                        <div class="workshopdatetime row">
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Registration Start Date" 
                                    name="workshopForm.rgStDate" 
                                    displayFormat="dd-MMM-yyyy"/>
                            </div>
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Registration Start Time" 
                                    name="workshopForm.rgStTime" 
                                    displayFormat="HH:mm" 
                                    type="time"/>
                            </div>
                        </div>

                        <div class="workshopdatetime row">
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Registration End Date" 
                                    name="workshopForm.rgEndDate" 
                                    displayFormat="dd-MMM-yyyy"/>
                            </div>                            
                            <div class="col-md-4">
                            <sx:datetimepicker 
                                label="Registration End Time" 
                                name="workshopForm.rgEndTime" 
                                displayFormat="HH:mm" 
                                type="time"/>
                            </div>
                        </div>

                        <!--Event Start/End Date/Time-->
                        <div class="workshopdatetime row">
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Event Start Date" 
                                    name="workshopForm.eventStDate" 
                                    displayFormat="dd-MMM-yyyy"/>
                            </div>
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Event Start Time" 
                                    name="workshopForm.eventStTime" 
                                    displayFormat="HH:mm" 
                                    type="time"/>
                            </div>
                        </div>

                        <div class="workshopdatetime row">
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Event End Date" 
                                    name="workshopForm.eventEndDate" 
                                    displayFormat="dd-MMM-yyyy"/>
                            </div>
                            <div class="col-md-4">
                                <sx:datetimepicker 
                                    label="Event End Time" 
                                    name="workshopForm.eventEndTime" 
                                    displayFormat="HH:mm" 
                                    type="time"/>  
                            </div>
                        </div>
                        
                        <s:submit 
                            value="Save"
                            cssClass="btn btn-primary"/>
                    </s:form>
                </div>
            </div>
        </div>
                            


        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <!--<script src="js/bootstrap.min.js"></script>-->
        <script src="js/searchBar.js"></script>
        <!-- End JS -->
    </body>

</html>
