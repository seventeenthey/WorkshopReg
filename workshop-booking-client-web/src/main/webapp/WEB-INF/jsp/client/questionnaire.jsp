<%-- 
    Document   : questionnaire
    Created on : 18-Mar-2019, 4:24:43 PM
    Author     : dwesl
--%>
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
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="utf-8">  
        <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
        <meta name="viewport" content="width=device-width, initial-scale=1">                
        <title>Questionnaire JSP</title>  
        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        
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
            if (self === top) {
                var antiClickjack = document.getElementById("antiClickjack");
                antiClickjack.parentNode.removeChild(antiClickjack);
            } else {
                top.location = self.location;
            }
        </script>
    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Workshop Calendar</h1>
            <p class="h3"> <s:property value="getText('general.title.h3')" /> </p>
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
                    <s:url action="dashboard" var="dashboardUrl" />
                    <a id="loginBtn" class="nav-link" href='<s:property value="dashboardUrl"/>'>Logout</a>
                </li>
            </ul>              
        </nav>

        <div class="container">
            <s:actionmessage theme="bootstrap"/>
            
            <h2>Registration Questionnaire</h2>
            
            <s:form id="regQuestionnaire" action="questionnaireExeAction?workshopId=%{workshopId}" 
                    theme="bootstrap" method="post" cssClass="form-vertical">
                <s:fielderror theme="bootstrap"/>
                
                <%--Todo: Need connect the expectation to Database--%>
                <s:textarea 
                    label="Are there any expectation for the workshop?"
                    name="expectation"
                    placeholder="Write something here..."
                    elementCssClass="col-sm-5"/>

                <s:checkbox
                    label="*I understand that by registering for this event I am committing to attend the entire event."
                    name="personInfo.checkbox"
                    required="required"/>

                <s:submit 
                    value="Register"
                    cssClass="btn btn-success"/>
            </s:form>
        </div>


        <script src="js/searchBar.js"></script>
        <div id="role" style="display:none"><s:property value="person.roleId.roleId"/></div> 
        <script src="js/visibility.js"></script>
    </body>
</html>
