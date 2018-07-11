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
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">        
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Llama Words</h1>
            <hr/>
            <div class="navbar navbar-default" style="text-align: center;">
                <ul class="nav navbar-nav" style="display: inline-block; float: none; vertical-align: top;">
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/gameboard">Current Board</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/highscores">High Scores</a></li>
                </ul>    
            </div>
            <hr/>
            <div style="text-align: center;">
                <br/>
                <h1>Current Top Scores</h1>
            </div>
            <div class="col-md-6 col-md-offset-3">
                <div style="text-align: left">
                    <c:set var="kiwiGold" value="<i class='fas fa-kiwi-bird' aria-hidden='true' style='font-size:75px;color:#FFD700;margin:20px;'></i>"/>
                    <c:set var="kiwiSilver" value="<i class='fas fa-kiwi-bird' aria-hidden='true' style='font-size:75px;color:#C0C0C0;margin:20px;'></i>"/>
                    <c:set var="kiwiBronze" value="<i class='fas fa-kiwi-bird' aria-hidden='true' style='font-size:75px;color:#cd7f32;margin:20px;'></i>"/>
                    <h2>${kiwiGold} ${topThreeScores[0].name} : ${topThreeScores[0].points} pts</h2>
                    <h2>${kiwiSilver} ${topThreeScores[1].name} : ${topThreeScores[1].points} pts</h2>
                    <h2>${kiwiBronze} ${topThreeScores[2].name} : ${topThreeScores[2].points} pts</h2>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

