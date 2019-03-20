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
        <title>Assign Roles JSP</title>  


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">


        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <!-- Custom styles for this template -->
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
            <h1>Admin Console</h1>
            <h3>Assign Roles</h3>
        </div>

        <!-- Navigation Bar - INCLUDED IN EVERY .JSP FILE -->
        <nav class="navbar navbar-toggleable-md navbar-inverse bg-primary">
            <div class='navbar-brand'>Queen's ITS Workshop Registration</div>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <s:url action="dashboard" var="dashboardUrl" />
                    <a class="nav-link" href='<s:property value="dashboardUrl"/>'>View Workshops</a>
                    <!--<a class="nav-link" href="#home">Home <span class="sr-only">(current)</span></a>-->
                </li>
                <li class="nav-item">
                    <s:url action="assignRolesAction" var="assignRolesUrl" />
                    <a class="nav-link" href='<s:property value="assignRolesUrl"/>'>Assign Roles</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Logout</a>
                </li>
            </ul>              
        </nav>

        <div class="container">
            <table class="table table-bordered table-striped table-hover">
                <th>
                    Name
                </th>
                <th>
                    Person PK
                </th>
                <th>
                    Roles
                </th>
                <th>
                    Workshops Created
                </th>
                <th>
                    Workshops Registered For
                </th>
                <th>
                    Link
                </th>


                <s:iterator value="people">
                    <tr>
                        <td>
                            <s:property value="commonName" />
                        </td>
                        <td>
                            <s:property value="personPK" />
                        </td>
                        <td>

                            <table>
                                <th>
                                    Department
                                </th>
                                <th>
                                    Role
                                </th>
                                <s:iterator value="roleList">
                                    <tr>
                                        <td>
                                            <s:property value="department"/>
                                        </td>
                                        <td>
                                            <s:property value="role"/>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </table>
                        </td>
                        <td>
                            <p>#</p>
                        </td>
                        <td>
                            <p>#</p>
                        </td>
                        <td>
                            <p>Edit roles</p>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
        <div class="container">
            <h3>Example</h3>
            <div class="active-cyan-3 active-cyan-4 mb-4 nav-item col-sm-12">
                <input id="searchKey" class="form-control" type="text" placeholder="Search" aria-label="Search" onkeyup="filterTable()">
            </div>
            <table id="userTable" class="table table-bordered table-striped">
                <th>
                    Name
                </th>
                <th>
                    Person PK
                </th>
                <th>
                    Roles
                </th>
                <th>
                    Workshops Created
                </th>
                <th>
                    Workshops Registered For
                </th>



                <tr>
                    <td>
                        <p>John Doe</p>
                    </td>
                    <td>
                        <p>3</p>
                    </td>
                    <td>

                        <table>
                            <th></th>
                            <th></th>
                            <th></th>
                            <tr>
                                <td>Queen's ITS</td>
                                <td>Departmental Admin</td>
                                <td>
                                    <a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>
                                    <a class="confirm" title="ConfirmEdit" data-toggle="tooltip" style="display:none"><i class="material-icons check">&#xe5ca;</i></a>
                                    <!--<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons add">&#xe145;</i></a>-->
                                    <!--<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>-->
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <p>3</p>
                    </td>
                    <td>
                        <p>1</p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Jane Hey</p>
                    </td>
                    <td>
                        <p>2</p>
                    </td>
                    <td>

                        <table>
                            <th></th>
                            <th></th>
                            <th></th>
                            <tr>
                                <td><p>Physics</p></td>
                                <td><p>Workshop Creator</p></td>
                                <td>
                                    <a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>
                                    <a class="confirm" title="ConfirmEdit" data-toggle="tooltip" style="display:none"><i class="material-icons check">&#xe5ca;</i></a>
                                    <!--<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons add">&#xe145;</i></a>-->
                                    <!--<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>-->
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <p>8</p>
                    </td>
                    <td>
                        <p>0</p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Mr. Nobody</p>
                    </td>
                    <td>
                        <p>1</p>
                    </td>
                    <td>

                        <table>
                            <th></th>
                            <th></th>
                            <th></th>
                            <tr>
                                <td><p>Computing</p></td>
                                <td><p>Attendee</p></td>
                                <td>
                                    <a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>
                                    <a class="confirm" title="ConfirmEdit" data-toggle="tooltip" style="display:none"><i class="material-icons check">&#xe5ca;</i></a>
                                    <!--<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons add">&#xe145;</i></a>-->
                                    <!--<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>-->
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <p>0</p>
                    </td>
                    <td>
                        <p>15</p>
                    </td>
                </tr>
            </table>
        </div>        
        <script src="js/tableControl.js"></script>
    </body>
</html>
