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
        <!-- Bursary styles -->
        <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">


    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Workshop List View</h1>
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

        <h1>Need to add workshop list view functionality here.</h1>

        <h3>Login/Logout</h3>

        <div>
            <s:url action="detailsLoadAction" var="detailsUrl" />
            <a href='<s:property value="detailsUrl"/>'>Go To Workshop Details Page </a>
        </div>

        <div>
            <s:url action="dashboardAction" var="dashboardUrl" />
            <a href='<s:property value="dashboardUrl"/>'>Go Back </a>
        </div>



        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/searchBar.js"></script>
        <!-- End JS -->
    </body>
</html>
