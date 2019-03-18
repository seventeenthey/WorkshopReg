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
        <!-- Bursary styles -->
        <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">

    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Details Page</h1>
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

        <div class="container"> 
            <div class="text-center">
                <h3> Workshop Name </h3>
                <p width="1"> Description........... Description........... Description........... Description........... Description...........</p>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <h4> Location: </h4>
                    <h4> </h4>
                </div>
                <div class="col-sm-4">
                    <h4> Time & Date: </h4>
                    <h4> </h4>
                </div>
                <div class="col-sm-4">
                    <h4> Facilitator: </h4>
                    <h4> </h4>
                </div>
                <div class="col-sm-4">
                    <h4> Status (Will be hidden depending on user class): </h4>
                    <h4> </h4>
                </div>
            </div>

            <ul>
                <li type="button" class="btn btn-primary"> 
                    <s:url action="personalDetailAction" var="personalDetailUrl" />
                    <a href='<s:property value="personalDetailUrl"/>' style="color:white">Register</a>
                </li>
                <li type="button" class="btn btn-primary">
                    <s:url action="functionLoadAction" var="functionUrl" />
                    <a href='<s:property value="functionUrl"/>' style="color:white">Edit Workshop (Will be hidden depending on user class)</a>
                </li>
            </ul>
        </div>

        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- End JS -->
    </body>
</html>
