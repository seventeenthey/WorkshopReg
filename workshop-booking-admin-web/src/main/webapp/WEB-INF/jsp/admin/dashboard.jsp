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
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/styles.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=Arvo|Playfair+Display|Raleway|Roboto" rel="stylesheet">

        <!-- 
        Test>>>>>>>
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
            } 
            else {
                top.location = self.location;
            }
        </script>
    </head>
    <body>
        <div class="container banner-row"> <!-- banner row -->
            <p class="h3"> <s:property value="getText('general.title.h3')" /> </p>
            <p class="h1"> <s:property value="getText('general.title.h1')" /> </p>
        </div> <!-- /banner row -->
        <div class="container main-container"> <!-- main container -->

            <nav class="navbar navbar-toggleable navbar-light justify-content-center" style="background-color: #fff;">
                <button class="navbar-toggler navbar-toggle-left" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span> <s:property value="Nav Bar" /> 
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav mx-auto w-100 justify-content-around">
                            <strong>This is just a sample JSP</strong>
                        <s:url action="logoutAction" var="logout"></s:url>
                        <a class="nav-item nav-link hover-focus-border" id="nav-logout" href="<s:property value="#logout"/>"><s:property value="getText('general.btn.logout')" /></a>
                    </div>
                </div>
            </nav>
            <div class="row"> <!-- body row -->
                <div class="col-sm-4 col-md-3 col-xl-2 info-col"> <!-- info col -->

                </div> <!-- /info col -->

                <div class="col-sm-8 col-md-9  col-xl-10 body-col">  <!--body col -->
                    <div id="archetypeDiv">
                        <h2 class="text-center"><s:property value="The Title" /></h2>
                        <s:if test="PersonDetails.isEmpty()">
                            <!--then display the messages saying the student does not have any requests
                            and that they can click +new to start one -->
                            <div class="text-center">
                                <s:if test="!instrReqs.isEmpty()">
                                    <p class='col-md-8 offset-md-2'> <s:property value="List Empty" /> </p>
                                </s:if>
                                <s:else>
                                    <p class='col-md-8 offset-md-2'> <s:property value="The List" /> </p>
                                </s:else>
                            </div>
                        </s:if>
                        <s:else>
                            <div class="col-lg-10 offset-lg-1">
                                <hr>
                                <div id="archetypeBody">
                                        <div class="row row-hover">
                                            <div class="col-3">
                                                <s:property value="person.commonName"/>  <s:property value="person.email"/>
                                            </div>
                                            <div class="col-9">
                                                <s:iterator value="person.detailList" var="persDeets" status="detailStat">  <!-- iterate through ArrayList<A11nRequest> -->
                                                    <div class="row">
                                                        <div class="col-4">
                                                            <s:property value="#persDeets.attr"/><br>
                                                        </div>
                                                    </div>
                                                    <s:if test="%{!#detailStat.last}">
                                                        <!-- only add this <hr> if this is NOT the last persDeets in the list. Otherwise we'd have two <hr> elements
                                                        stacked on top of each other because we add another one below -->
                                                        <hr>
                                                    </s:if>
                                                </s:iterator>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col">
                                                <hr>
                                            </div>
                                        </div>
                                </div>
                            </div>
                        </s:else>
                    </div> <!--/submitted-->
                    
                </div> <!-- /body col -->
            </div> <!-- /body row -->

        </div> <!-- /main container -->
        <footer class="footer" id="footer">
            <div class="container-fluid copyright">
                <div class="row justify-content-end">
                    

                </div>
            </div>
        </footer>
        <!-- JS -->
        <script src="js/tether.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/dashboard.js"></script>
        <!-- End JS -->
    </body>
</html>
