<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies
    response.setHeader("X-Frame-Options", "DENY"); // Does not allow this page to be displayed in an iframe. This helps prevent clickjacking.
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">                                   
        <title>Queen's University - Exam Office Error</title>  

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">       
        <!-- Bursary styles -->   
        <link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">        
    </head>    

    <body>
        <header>
            <img src="images/queenslogo.png" width="138" height="105" alt="Queen's University">
        </header>


        <div class="container">
            <div class="formcontent">
                <h1 class="h3"> 
                    Error 404. The page you are requesting does not exist.
                </h1>
                <h4>
                    If you require assistance, please contact the Archetype Office. Please take a screen shot of the page and make note of the date and time that this error occurred. Thank you.
                </h4>
            </div>
        </div>


    </body>
</html>