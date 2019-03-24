<%-- 
    Document   : personalDetail
    Created on : Mar 7, 2019, 8:55:05 PM
    Author     : dd123
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Personal Detail</title>
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
            <h1>Personal Info</h1>
        </div>

        <!-- INCLUDED IN EVERY .JSP FILE -->
        <div class="container-fluid">
            <div class="row" style="background-color:#6699ff">
                <div class="col-sm-10"></div>
                <div class="col-sm-1">
                    <div class="active-cyan-3 active-cyan-4 mb-4">
                        <input class="form-control" type="text" placeholder="Search" aria-label="Search">
                    </div>
                </div>
                <div class="col-sm-1">

                    <div class="dropwdown text-right">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">. . .
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu dropdown-menu-right text-right">
                            <li><s:url action="dashboard" var="dashboardUrl" />
                                <a href='<s:property value="dashboardUrl"/>'>Workshop Calendar</a>
                            </li>
                            <li>
                                <s:url action="listViewLoadAction" var="listViewUrl" />
                                <a href='<s:property value="listViewUrl"/>'>My Workshops</a>
                            </li>
                            <li class="nav-item">
                                <s:url action="functionLoadAction" var="functionUrl" />
                                <a class="nav-link" href='<s:property value="functionUrl"/>'>Create Workshop</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="#">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>


        <div class="container">
            <s:form id="workshopEditForm" action="functionLoadAction" 
                    theme="bootstrap" method="post" cssClass="form-vertical">

                <h1>Workshop Name:</h1>

                <s:textfield 
                    label="*First Name:"
                    name="personInfo.firstName"/>

                <s:textfield 
                    label="*Last Name:"
                    name="personInfo.lastName"/>

                <s:textfield 
                    type="email"
                    label="*Email Address"
                    name="personInfo.emial1"
                    placeholder="Enter email address"/>

                <s:textfield 
                    type="email"
                    label="*Retype email Address"
                    name="personInfo.emial2"
                    placeholder="Enter email address"/>

                <s:select 
                    label="*Department:"
                    name="personInfo.department" 
                    list="departmentList"/>

                <s:radio 
                    label="Role:"
                    name="personInfo.role" 
                    list="roleList" />

                <s:textfield 
                    label="Are there any steps we can take to make this a more inclusive and barrier-free event?"
                    name="personInfo.comment"
                    placeholder="Write something here..."/>

                <s:checkbox
                    label="*I understand that by registering for this event I am committing to attend the entire event."
                    name="personInfo.checkbox"/>

                <s:submit cssClass="btn btn-primary"/>
                <s:submit 
                    name="cancel"
                    cssClass="btn btn-success"/>
            </s:form>

        </div>

        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="js/searchBar.js"></script>
    </body>
</html>
