<%--
    Document   : attendance
    Created on : 1-Mar-2019, 7:59:02 PM
    Author     : dwesl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attendance</title>

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">
        <!-- Bursary styles -->
        <!--<link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">-->
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">
        
        <script type="text/javascript">
            function checkAll(){
             var listc = document.getElementsByName("listCheck");
             if(document.getElementById("CheckAll").checked==true){
              for(var i=0;i<listc.length;i++){
               listc[i].checked=true;
              }
             }else{
              for(var i=0;i<listc.length;i++){
               listc[i].checked=false;
              }
             }
            }
        </script>

    </head>
    <body>
        <div class="jumbotron text-center banner-row mb-0">
            <h1>Workshop Attendance</h1>
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
                                <li class="workshopCreator nav-item">
                                    <s:url action="functionLoadAction" var="functionUrl">
                                        <s:param name="workshopId" value="workshopId"/>
                                    </s:url>
                                    <a href='<s:property value="functionUrl"/>'>Event Setup</a>
                                </li>
                                <li class="workshopCreator nav-item">
                                    <s:url action="copyworkshopLoadAction" var="copyworkshopUrl">
                                        <s:param name="workshopId" value="workshopId"/>
                                    </s:url>
                                    <a href='<s:property value="copyworkshopUrl"/>'>Copy Workshop</a>
                                </li>
                                <li class="workshopCreator nav-item">
                                    <s:url action="facilitatorLoadAction" var="facilitatorUrl" >
                                        <s:param name="workshopId" value="workshopId"/>
                                    </s:url>
                                    <a href='<s:property value="facilitatorUrl"/>'>Facilitators</a>
                                </li>
                                <li class="workshopCreator nav-item">
                                    <s:url action="emaileditLoadAction" var="emaileditUrl" >
                                        <s:param name="workshopId" value="workshopId"/>
                                    </s:url>
                                    <a href='<s:property value="emaileditUrl"/>'>Message Center</a>
                                </li>
                                <li class="nav-item">
                                    <s:url action="attendanceLoadAction" var="attendanceUrl">
                                        <s:param name="workshopId" value="workshopId"/>
                                    </s:url>
                                    <a href='<s:property value="attendanceUrl"/>'>Attendance</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

            <div class="col-md-9">
                <s:actionmessage theme="bootstrap"/>
                <s:form action="attendanceExeAction?workshopId=%{workshopId}" method="post">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Student-ID</th>
                                <th>Name</th>
                                <th>Attendance</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="attendance" var="attend" status="status">
                                <tr>
                                    <td><s:property value="person.netId"/></td>
                                    <td><s:property value="person.commonName"/></td>
                                    <td><s:checkbox name="attendance[%{#status.index}].attended" value="attended"/></td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                    <s:submit value="submit" cssClass="btn btn-primary"/>
                </s:form>
            </div>

        </div>

        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/searchBar.js"></script>
        
        <!--Controls Visibility -->
        <!--This control needs to be after all other elements AND any other .js that affect visibility, and these two lines need to be in this order-->
        <div id="role" style="display:none"><s:property value="person.roleId.roleId"/></div> 
        <script src="js/visibility.js"></script>
        <!--End Visibility Control-->
        
        <!-- End JS -->
    </body>
</html>
