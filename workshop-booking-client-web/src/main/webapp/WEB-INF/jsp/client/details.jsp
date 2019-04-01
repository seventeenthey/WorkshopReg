<%--
    Document   : details
    Created on : 5-Mar-2019, 1:00:18 PM
    Author     : dwesl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

        <!-- Bursary styles -->
        <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">

    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Workshop Details</h1>
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
                <li class="workshopCreator nav-item">
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

        <div class="container">
            <div class="card text-center">
                <div class='bg-info card-header'>
                    <h3><s:property value="workshop.Title"/></h3>
                    <p width="1"><s:property value="workshop.details"/></p>
                </div>
                <div class="row mx-1">
                    <div class="col-sm-4 my-2">
                        <div class="card bg-info">
                            <h4 class="card-header">Registered Status: </h4>
                            <p class='card-body text-white' id="registeredStatus"><s:property value="registeredStatus"/></p> <!--This needs to say: "Registered", "Not Registered", or "WaitListed" -->
                        </div>
                    </div>
                    <div class="col-sm-4 my-2">
                        <div class="card bg-info">
                            <h4  class="card-header">Location: </h4>
                            <p class='card-body text-white'><s:property value="workshop.location"/></p>
                        </div>
                    </div>
                    <div class="col-sm-4 my-2">
                        <div class="card bg-info">
                            <h4  class="card-header">Start Time & Date: </h4>
                            <div class="card-body text-white">
                                <p><s:property value="workshop.dateToString()"/></p>
                                <p id="startDate"><s:property value="workshop.getEventStart().toString()"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 my-2">
                        <div class="card bg-info">
                            <h4  class="card-header">Facilitator: </h4>
                            <div class='card-body text-white'>
                                <s:iterator value="workshop.myFacilitators">
                                    <p><s:property value="commonName"/></p>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 my-2">
                        <div class="card bg-info">
                            <h4 class="card-header">Workshop Status: </h4>
                            <p class='card-body text-white'><s:property value="workshop.eventStatus.eventStatus"/></p>
                        </div>
                    </div>
                    <div class="col-sm-4 my-2">
                        <div class='card bg-info'>
                            <h5 class="card-header">Workshop ID: </h5>
                            <p class='card-body text-white'> <s:property value="workshopId"/> </p>
                        </div>
                    </div>
                </div>
            </div>

            <!--Todo: Add a Review Form for Attendee to fill out after the workshop ends-->

            <ul>

                <!--Workshop Register-->
                <li type="button" class="btn btn-primary" id="registerBtn">
                    <s:url action="questionnaireLoadAction" var="questionnaireUrl" >
                        <s:param name="workshopId" value="workshopId"/>
                    </s:url>
                    <a href='<s:property value="questionnaireUrl"/>' style="color:white">Register</a>
                </li>

                <!--Workshop Unregister-->
                <li type="button" class="btn btn-primary" id="unRegisterBtn">
                    <s:url action="detailsUnregisterAction" var="unregisterUrl" >
                        <s:param name="workshopId" value="workshopId"/>
                    </s:url>
                    <a href='<s:property value="unregisterUrl"/>' style="color:white">Cancel Registration</a>
                </li>

                <!--Workshop Review-->
                <li type="button" class="btn btn-primary" id="reviewBtn" style="display:none">
                    <s:url action="detailsLoadAction" var="loadUrl" >
                        <s:param name="workshopId" value="workshopId"/>
                    </s:url>
                    <a href='<s:property value="loadUrl"/>' style="color:white">Leave Review</a>
                </li>

                <!--Workshop Management-->
                <div class="workshopCreator creatorIdCheck">
                    <li type="button" class="btn btn-primary">
                        <s:url action="functionLoadAction" var="functionUrl">
                            <s:param name="workshopId" value="workshopId"/>
                        </s:url>
                        <a href='<s:property value="functionUrl"/>' style="color:white">Advanced Options</a>
                    </li>
                </div>

                <!--Attendance Management-->
                <div class="facilitator facilIdCheck">
                    <li type="button" class="btn btn-primary">
                        <s:url action="attendanceLoadAction" var="attendanceUrl">
                            <s:param name="workshopId" value="workshopId"/>
                        </s:url>
                        <a href='<s:property value="attendanceUrl"/>' style="color:white">Manage Attendance</a>
                    </li>
                </div>

                <div class="facilitator">
                    <li type="button" class="btn btn-primary">
                        <s:url action="ViewReviewLoadAction" var="viewReviewUrl">
                            <s:param name="workshopId" value="workshopId"/>
                        </s:url>
                        <a href='<s:property value="viewReviewUrl"/>' style="color:white">review</a>
                    </li>
                </div>

                <!--Todo: Add a Review Form for Attendee to fill out after the workshop ends-->

            </ul>
        </div>
        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/searchBar.js"></script>
        <script src="js/detailsManager.js"></script>

        <!--Controls Visibility -->
        <!--This control needs to be after all other elements AND any other .js that affect visibility, and these two lines need to be in this order-->
        <div id="role" style="display:none"><s:property value="person.roleId.roleId"/></div>
        <div id="creatorAuth" style="display:none"><s:property value="creatorAuth"/></div>
        <div id="facilAuth" style="display:none"><s:property value="facilAuth"/></div>
        <script src="js/visibility.js"></script>
        <!--End Visibility Control-->
        <!-- End JS -->
    </body>
</html>
