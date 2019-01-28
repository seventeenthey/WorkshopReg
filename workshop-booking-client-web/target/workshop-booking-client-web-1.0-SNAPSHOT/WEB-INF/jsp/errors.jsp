<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies
    response.setHeader("X-Frame-Options", "DENY"); // Does not allow this page to be displayed in an iframe. This helps prevent clickjacking.
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html>
<html>
    <head>        
        <s:head/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">                                   
        <title>Queen's University - Archetype Error</title>  

        <!-- Bootstrap core CSS -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">       
        <!-- Bursary styles -->   
        <link href="<%=request.getContextPath()%>/css/bursaryapp.css" rel="stylesheet">  
        <script type='text/javascript'>


        </script>
    </head>    

    <body>
        <header>
            <img src="images/queenslogo.png" width="138" height="105" alt="Queen's University">
        </header>

        <!--img src="images/2_17_18_GeneralApp_1170x290_nodate.png" class="img-responsive" alt="Admission Bursary Application 2017/2018"-->
        <div class="container">   
            <div class="formcontent">
                <h1 class="h3"> 
                    <img src="images/inforicon.png" alt="Information"> 
                    An error has occurred while processing your request. Please contact the Archetype Office for assistance.
                </h1>
                <hr>
                <s:actionerror/>
                <!--
                <s:actionmessage/>
                -->            
            </div>
        </div>
    </body>
</html>