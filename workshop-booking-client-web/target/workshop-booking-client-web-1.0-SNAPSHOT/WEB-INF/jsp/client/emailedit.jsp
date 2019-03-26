<%-- 
    Document   : emailedit
    Created on : 10-Mar-2019, 4:31:05 PM
    Author     : sylvi
    Message Center Page
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Email Edit</title>

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
            <h1>Email Edit</h1>
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
                    <s:form id="RegEmailForm" action="emailEditLoadAction" 
                            theme="bootstrap" method="post" cssClass="form-vertical"
                            enctype="multipart/form-data">

                        <h2>Registrant Notification Messages</h2>
                        <s:textfield 
                            label="Notification Email From Name:"
                            name="EmailInfoForm.notifyFromName"/>

                        <s:textarea 
                            label="Confirmation Message"
                            name="EmailInfoForm.confirmMsg"
                            emptyOption="true"/>

                        <s:textarea 
                            label="Wait List Message"
                            name="EmailInfoForm.waitListMsg"
                            emptyOption="true"/>

                        <s:textarea 
                            label="Cancellation Message"
                            name="EmailInfoForm.cancelMsg"
                            emptyOption="true"/>

                        <s:textarea 
                            label="Evaluation Message"
                            name="EmailInfoForm.evalMsg"
                            emptyOption="true"/>

                        <h2>Internal Notification Options</h2>
                        <s:checkboxlist 
                            label="Notify:" 
                            labelposition="inline"
                            name="EmailInfoForm.notifyGroup"
                            list="{'Event Host','Assigned Facilitators'}"/>

                        <s:checkboxlist 
                            label="Receive Notifications For:" 
                            labelposition="inline"
                            name="EmailInfoForm.notifyCondition"
                            list="{'Confirmation','Wait List','Cancellation','Event Full'}"/>                

                        <s:submit cssClass="btn btn-primary"/>
                    </s:form>
                </div>
            </div>
        </div>          
                             
        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <!--<script src="js/jquery-3.2.1.min.js"></script>-->
        <script src="js/bootstrap.min.js"></script>
        <script src="js/searchBar.js"></script>
        <!-- End JS -->
    </body>
</html>
