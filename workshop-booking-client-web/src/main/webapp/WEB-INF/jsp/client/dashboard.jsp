<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies
    response.setHeader("X-Frame-Options", "DENY"); // Does not allow this page to be displayed in an iframe. This helps prevent clickjacking.
    response.setHeader("X-XSS-Protection: 1", "mode=block"); //xss reflected attack prevention
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
    "http://struts.apache.org/dtds/struts-2.5.dtd">
<html lang="en">
    <head> 
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="utf-8">  
        <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
        <meta name="viewport" content="width=device-width, initial-scale=1">                
        <title>Archetype UIS JSP</title>  
        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/datatables.min.css" rel="stylesheet">
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        
        
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/calendar.css" rel="stylesheet">
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
                <li class="loggedIn nav-item">
                    <s:url action="listViewLoadAction" var="listViewUrl" />
                    <a class="nav-link" href='<s:property value="listViewUrl"/>'>My Workshops</a>
                </li>
                <li class="loggedIn workshopCreator nav-item">
                    <s:url action="functionLoadAction" var="functionUrl" />
                    <a class="nav-link" href='<s:property value="functionUrl"/>'>Create Workshop</a>
                </li>
                <li class="nav-item">
                    <div class="active-cyan-3 active-cyan-4">
                        <input class="form-control" type="text" placeholder="Search" aria-label="Search" onkeyup="filterTable()" id="searchKey" value="<s:property value='searchKey'/>"/>
                    </div>
                </li>
                <li class="nav-item">
                    <s:url action="dashboard" var="dashboardUrl" />
                    <a id="loginBtn" class="nav-link" href='<s:property value="dashboardUrl"/>'>Login</a>
                </li>
            </ul>              
        </nav>

        <div class="container">

            <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

            <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
            <script
                src="https://code.jquery.com/jquery-3.3.1.min.js"
                integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>



            <div class="container theme-showcase" id="holder">
            </div>


            <script type="text/tmpl" id="tmpl">
                {{ 
                var date = date || new Date(),
                month = date.getMonth(), 
                year = date.getFullYear(), 
                first = new Date(year, month, 1), 
                last = new Date(year, month + 1, 0),
                startingDay = first.getDay(), 
                thedate = new Date(year, month, 1 - startingDay),
                dayclass = lastmonthcss,
                today = new Date(),
                i, j; 
                if (mode === 'week') {
                thedate = new Date(date);
                thedate.setDate(date.getDate() - date.getDay());
                first = new Date(thedate);
                last = new Date(thedate);
                last.setDate(last.getDate()+6);
                } else if (mode === 'day') {
                thedate = new Date(date);
                first = new Date(thedate);
                last = new Date(thedate);
                last.setDate(thedate.getDate() + 1);
                }

                }}
                <table class="calendar-table table table-condensed table-tight">
                <thead>
                <tr>
                <td colspan="7" style="text-align: center">
                <table style="white-space: nowrap; width: 100%">
                <tr>
                <td style="text-align: left;">
                <span class="btn-group">
                <button class="js-cal-prev btn btn-default"><</button>
                <button class="js-cal-next btn btn-default">></button>
                </span>
                <button class="js-cal-option btn btn-default {{: first.toDateInt() <= today.toDateInt() && today.toDateInt() <= last.toDateInt() ? 'active':'' }}" data-date="{{: today.toISOString()}}" data-mode="month">{{: todayname }}</button>
                </td>
                <td>
                <span class="btn-group btn-group-lg">
                {{ if (mode !== 'day') { }}
                {{ if (mode === 'month') { }}<button class="js-cal-option btn btn-link" data-mode="year">{{: months[month] }}</button>{{ } }}
                {{ if (mode ==='week') { }}
                <button class="btn btn-link disabled">{{: shortMonths[first.getMonth()] }} {{: first.getDate() }} - {{: shortMonths[last.getMonth()] }} {{: last.getDate() }}</button>
                {{ } }}
                <button class="js-cal-years btn btn-link">{{: year}}</button> 
                {{ } else { }}
                <button class="btn btn-link disabled">{{: date.toDateString() }}</button> 
                {{ } }}
                </span>
                </td>
                <td style="text-align: right">
                <span class="btn-group">
                <button class="js-cal-option btn btn-default {{: mode==='year'? 'active':'' }}" data-mode="year">Year</button>
                <button class="js-cal-option btn btn-default {{: mode==='month'? 'active':'' }}" data-mode="month">Month</button>
                <button class="js-cal-option btn btn-default {{: mode==='week'? 'active':'' }}" data-mode="week">Week</button>
                <button class="js-cal-option btn btn-default {{: mode==='day'? 'active':'' }}" data-mode="day">Day</button>
                </span>
                </td>
                </tr>
                </table>

                </td>
                </tr>
                </thead>
                {{ if (mode ==='year') {
                month = 0;
                }}
                <tbody>
                {{ for (j = 0; j < 3; j++) { }}
                <tr>
                {{ for (i = 0; i < 4; i++) { }}
                <td class="calendar-month month-{{:month}} js-cal-option" data-date="{{: new Date(year, month, 1).toISOString() }}" data-mode="month">
                {{: months[month] }}
                {{ month++;}}
                </td>
                {{ } }}
                </tr>
                {{ } }}
                </tbody>
                {{ } }}
                {{ if (mode ==='month' || mode ==='week') { }}
                <thead>
                <tr class="c-weeks">
                {{ for (i = 0; i < 7; i++) { }}
                <th class="c-name">
                {{: days[i] }}
                </th>
                {{ } }}
                </tr>
                </thead>
                <tbody>
                {{ for (j = 0; j < 6 && (j < 1 || mode === 'month'); j++) { }}
                <tr>
                {{ for (i = 0; i < 7; i++) { }}
                {{ if (thedate > last) { dayclass = nextmonthcss; } else if (thedate >= first) { dayclass = thismonthcss; } }}
                <td style="width:150px" class="calendar-day {{: dayclass }} {{: thedate.toDateCssClass() }} {{: date.toDateCssClass() === thedate.toDateCssClass() ? 'selected':'' }} {{: daycss[i] }} js-cal-option" data-date="{{: thedate.toISOString() }}">
                <div class="date">{{: thedate.getDate() }}</div>
                {{ thedate.setDate(thedate.getDate() + 1);}}
                </td>
                {{ } }}
                </tr>
                {{ } }}
                </tbody>
                {{ } }}
                {{ if (mode ==='day') { }}
                <tbody>
                <tr>
                <td colspan="7">
                <table class="table table-striped table-condensed table-tight-vert" >
                <thead>
                <tr>
                <th> </th>
                <th style="text-align: center; width: 100%">{{: days[date.getDay()] }}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                <th class="timetitle" >All Day</th>
                <td class="{{: date.toDateCssClass() }}">  </td>
                </tr>
                <tr>
                <th class="timetitle" >Before 6 AM</th>
                <td class="time-0-0"> </td>
                </tr>
                {{for (i = 6; i < 22; i++) { }}
                <tr>
                <th class="timetitle" >{{: i <= 12 ? i : i - 12 }} {{: i < 12 ? "AM" : "PM"}}</th>
                <td class="time-{{: i}}-0"> </td>
                </tr>
                <tr>
                <th class="timetitle" >{{: i <= 12 ? i : i - 12 }}:30 {{: i < 12 ? "AM" : "PM"}}</th>
                <td class="time-{{: i}}-30"> </td>
                </tr>
                {{ } }}
                <tr>
                <th class="timetitle" >After 10 PM</th>
                <td class="time-22-0"> </td>
                </tr>
                </tbody>
                </table>
                </td>
                </tr>
                </tbody>
                {{ } }}
                </table>
            </script>
        </div>

        <div class="container">
            <table class="table table-bordered  table-hover table-striped table-sm" id="workshopData">
                <thead>
                    <th class="th-sm">Workshop Name</th>
                    <th class="th-sm">Workshop Number</th>
                    <th class="th-sm">Date</th>
                    <th class="th-sm">End Date</th>
                    <th class="th-sm">Department</th>
                    <th class="th-sm">Details</th>
                </thead>
                <tbody>
                <s:iterator value="workshopBeanList">
                    <tr>
                        <td><s:property value="title" /></td>
                        <td><s:property value="workshopId" /></td>
                        <td><s:property value="eventStart.toString()"/></td>
                        <td><s:property value="eventEnd.toString()"/></td>
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
        </div>

        <div id="loggedIn" style="display:none"><s:property value="loggedIn"/></div>

        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/calendar.js"></script> 
        <script src="js/loginManager.js"></script>
        <script src="js/datatables.min.js"></script>
        <script src="js/dashboardTableFilter.js"></script>

        <!--Controls Visibility -->
        <!--This control needs to be after all other elements AND any other .js that affect visibility, and these two lines need to be in this order-->
        <div id="role" style="display:none"><s:property value="person.roleId.roleId"/></div> 
        <script src="js/visibility.js"></script>
        <!--End Visibility Control-->
        <!-- End JS -->
    </body>
</html>
