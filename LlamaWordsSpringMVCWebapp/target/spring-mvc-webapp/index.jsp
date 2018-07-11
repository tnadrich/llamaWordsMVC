<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Llama Words</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Llama Words</h1>
            <hr/>
            <div class="navbar navbar-default" style="text-align: center;">
                <ul class="nav navbar-nav" style="display: inline-block; float: none; vertical-align: top;">
                    <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li ><a href="${pageContext.request.contextPath}/gameboard">Current Board</a></li>
                    <li ><a href="${pageContext.request.contextPath}/highscores">High Scores</a></li>
                </ul>    
            </div>
            <hr/>
            <div class="col-xs-3 col-xs-offset-5">
                <img src="${pageContext.request.contextPath}/imgs/littlellama.png">
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

