<%-- 
    Document   : listview
    Created on : 1-Mar-2019, 7:58:45 PM
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

        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <!-- Bursary styles -->
        <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">


    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>My Workshops</h1>
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

        <div class="container" >
            <h3>Workshops You Are Registered For</h3>
            <table class="table table-bordered table-striped table-hover" id="registeredWorkshops">
                <thead>
                    <tr>
                        <th>Workshop Name</th>
                        <th>Workshop Number</th>
                        <th>Date</th
                        <th></th>
                        <th>Time</th>
                        <th>End Date</th>
                        <th>Department</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="workshopsRegistered">
                        <tr>
                            <td><s:property value="title" /></td>
                            <td><s:property value="workshopId" /></td>
                            <td><s:property value="eventStart"/></td>
                            <td><s:property value="startTimeToString()"/></td>
                            <td><s:property value="eventEnd"/></td>
                            <td><s:property value="departmentId.departmentName"/></td>
                            <td>
                                <s:url action="detailsLoadAction" var="detailsUrl">
                                    <s:param name="workshopId" value="workshopId" />
                                </s:url>
                                <a href='<s:property value="detailsUrl"/>'>Workshop Details</a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>

            <div id="noRegWorkshopsMsg" style="display:none">You are not currently registered for any workshops.</div>

            <h3>Workshops You Have Attended</h3>
            <table class="table table-bordered table-striped table-hover" id="pastWorkshops">
                <caption style="caption-side:top"><h3>Workshops You Have Attended</h3></caption>
                <thead>
                    <tr>
                        <th>Workshop Name</th>
                        <th>Workshop Number</th>
                        <th>Date</th
                        <th></th>
                        <th>Time</th>
                        <th>Registration End Date</th>
                        <th>Department</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="pastWorkshops">
                        <tr>
                            <td><s:property value="title" /></td>
                            <td><s:property value="workshopId" /></td>
                            <td><s:property value="eventStart"/></td>
                            <td><s:property value="startTimeToString()"/></td>
                            <td><s:property value="registrationEnd"/></td>
                            <td><s:property value="departmentId.departmentName"/></td>
                            <td>
                                <s:url action="detailsLoadAction" var="detailsUrl">
                                    <s:param name="workshopId" value="workshopId" />
                                </s:url>
                                <a href='<s:property value="detailsUrl"/>'>Leave a Review</a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>

            <div id="noPastWorkshopsMsg" style="display:none">You have not attended any workshops in the past.</div>

            <table class="workshopCreator table table-bordered table-striped table-hover" id="createdWorkshops">
                <caption style="caption-side:top"><h3>Workshops You have Created</h3></caption>
                <thead>
                    <tr>
                        <th>Workshop Name</th>
                        <th>Workshop Number</th>
                        <th>Date</th
                        <th></th>
                        <th>Time</th>
                        <th>Department</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="workshopsHosted">
                        <tr>
                            <td><s:property value="title" /></td>
                            <td><s:property value="workshopId" /></td>
                            <td><s:property value="eventStart"/></td>
                            <td><s:property value="startTimeToString()"/></td>
                            <td><s:property value="departmentId.departmentName"/></td>
                            <td>
                                <s:url action="detailsLoadAction" var="detailsUrl">
                                    <s:param name="workshopId" value="workshopId" />
                                </s:url>
                                <a href='<s:property value="detailsUrl"/>'>Workshop Details</a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>

            <div id="noCreatedWorkshopsMsg" style="display:none">You are not currently running any workshops.</div>
        </div>

        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/searchBar.js"></script>
        <script src="js/listViewTableManager.js"></script>
        
        
        <!--Controls Visibility -->
        <!--This control needs to be after all other elements, and these two lines need to be in this order-->
        <div id="role" style="display:none"><s:property value="person.roleId.roleId"/></div> 
        <script src="js/visibility.js"></script>
        <!--End Visibility Control-->

        <!-- End JS -->
    </body>
</html>
