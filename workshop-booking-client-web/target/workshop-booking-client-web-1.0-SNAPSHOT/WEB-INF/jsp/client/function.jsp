<%-- 
    Document   : function
    Created on : 1-Mar-2019, 7:59:08 PM
    Author     : dwesl
    Event Setup Page for Workshop Registration System
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>


<!DOCTYPE html>
<html>
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

        <sj:head jqueryui="true"/>
        <sb:head includeScripts="true"/>

    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Workshop Functions</h1>
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
                        <div class="active-cyan-3 active-cyan-4">
                            <input class="form-control" type="text" placeholder="Search" aria-label="Search"/>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Logout</a>
                    </li>
                </ul>              
        </nav>
    
    
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
           
        <div class="col-md-9">
            <s:form id="workshopEditForm" action="functionLoadAction" 
                    theme="bootstrap" method="post" cssClass="form-vertical"
                    label="Form with jQuery UI Elements">

                <s:radio 
                    label="Status:"
                    name="workshopForm.status" 
                    list="statusList" />

                <s:textfield 
                    label="Event Title:"
                    placeholder="Event Title"
                    name="workshopForm.eventTitle"
                    tooltip="Enter Workshop Title Here"/>

                <s:select 
                    label="Location"
                    name="workshopForm.location" 
                    list="locationList"/>

                <s:textarea 
                    label="Teaser:"
                    name="workshopForm.teaser"
                    emptyOption="true"/>
                

                <s:textfield 
                    label="Maximum Participants:"
                    name="workshopForm.maxParticipant"/>

                <s:textfield 
                    label="Wait List Limit:"
                    name="workshopForm.waitlistLimit"/>

                <sj:datepicker
                        parentTheme="bootstrap"
                        label="DateTimePicker"
                        cssClass="form-control"
                        elementCssClass="col-md-8"
                        labelCssClass="col-md-4"
                        placeholder="dd/mm/yyyy"
                        showOn="focus"
                        inputAppendIcon="calendar"
                />

                <s:submit cssClass="btn btn-primary"/>
            </s:form>
            
            <!--JS test-->
            <s:form action="echo" theme="bootstrap" cssClass="form-horizontal" label="Form with jQuery UI Elements">
                <sj:datepicker
                        id="datepicker"
                        parentTheme="bootstrap"
                        label="Datepicker"
                        tooltip="Tooltip for Datepicker"
                        cssClass="form-control"
                        elementCssClass="col-sm-5"
                        showOn="focus"
                        inputAppendIcon="calendar"
                />
                <s:url var="languages_url" action="languages"/>
                <sj:autocompleter
                        id="autocompleter"
                        parentTheme="bootstrap"
                        label="Autocompleter"
                        tooltip="Tooltip for Autocompleter"
                        cssClass="form-control"
                        href="%{languages_url}"
	    	            loadMinimumCount="1"
                 />
            </s:form>
        </div>
    </div>        
            
            
        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- End JS -->
        
        <!-- /container -->
        <script type="text/javascript">
            $(document).ready(function () {
                $(".collapse").collapse();
                $("#code1btn").click(function () {
                    $('#code1').collapse('toggle')
                });
            });
        </script>
        
    </body>
</html>
