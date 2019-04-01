<%-- 
    Document   : viewReview
    Created on : 31-Mar-2019, 9:22:45 PM
    Author     : dd123
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
            <h1>View Reviews</h1>
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
            
            <s:actionmessage theme="bootstrap"/>
            
            <s:form action="ViewReviewExeAction?workshopId=%{workshopId}"
                    theme="bootstrap" method="post" cssClass="form-vertical">
                <s:fielderror/>
                <h2>Leave a review:</h2>
                <s:textarea 
                    name="reviewNew"
                    cssClass="input-sm"
                    elementCssClass="col-sm-7"/>
                <s:submit value="submit" cssClass="btn btn-primary"/><br><br>
            </s:form>

            
            <s:form>
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Reviews</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="reviews">
                            <tr>
                                <td><s:property value="person.commonName"/></td>
                                <td><s:property value="review"/></td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </s:form>
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
        <script src="js/visibility.js"></script>
        <!--End Visibility Control-->
        <!-- End JS -->
    </body>
</html>

