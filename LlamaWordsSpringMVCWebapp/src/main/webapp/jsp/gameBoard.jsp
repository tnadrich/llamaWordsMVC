<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
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
                    <li class="active"><a href="${pageContext.request.contextPath}/gameboard">Current Board</a></li>
                    <li ><a href="${pageContext.request.contextPath}/highscores">High Scores</a></li>
                    <p class="navbar-text">Pts: ${gameBoard.currentScore.points}</p>
                </ul>
            </div>
            <hr/>
            <div class="col-md-6 col-md-offset-3" align="center" id="word-section">
                <br/>
                <br/>
                <div>
                    <h1 style="letter-spacing:10px">
                        <c:forEach var="guess" items="${gameBoard.chosenWord.guesses}">
                            ${guess}
                        </c:forEach>
                    </h1>
                </div>
                <hr/>
                <div>
                    <c:choose>
                        <c:when test="${gameBoard.livesLeft >= 1}">
                            <c:forEach var="i" begin="${gameBoard.livesLeft}" end="4">
                                <i class="far fa-frown" aria-hidden="true" style="font-size:75px;color:#ff0000;letter-spacing:25px"></i>
                            </c:forEach> 
                            <c:forEach var="i" begin="1" end="${gameBoard.livesLeft}">
                                <i class="far fa-smile" aria-hidden="true" style="font-size:75px;color:#00cc33;letter-spacing:25px"></i>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="i" begin="1" end="5">
                                <i class="far fa-frown" aria-hidden="true" style="font-size:75px;color:#ff0000;letter-spacing:25px"></i>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                <hr/>
            </div>
            <div class="col-md-6 col-md-offset-3" align="center">
                <div class="form-group">
                    <form class="horizontal" action="${pageContext.request.contextPath}/guess">
                        <c:choose>
                            <c:when test="${gameBoard.gameOver != true}">
                                <c:forEach var="letter" items="${gameBoard.ALL_LETTERS}">
                                    <c:set var="disable" value=""/>
                                    <c:forEach var="guessedLetter" items="${gameBoard.chosenLetters}">
                                        <c:if test="${guessedLetter eq letter}">
                                            <c:set var="disable" value="disabled"/>
                                        </c:if>
                                    </c:forEach>
                                    <button ${disable} type="submit" class="form-control" id="${letter}" name="letter" value="${letter}" style="text-transform:uppercase;font-size:20px;width:50px;height:50px;display:inline-block;margin:10px;">${letter}</button>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </form>  
                </div>
                <div class="form-group">
                    <c:choose>
                        <c:when test="${gameBoard.gameOver && gameBoard.currentScore.points <= topThreeScores[2].points}">
                            <h1>Too bad.</h1>
                            <p>You got a total of ${gameBoard.currentScore.points}</p>
                            <p>but you needed at least ${topThreeScores[2].points} to make a top score.</p>
                            <form class="horizontal" action="${pageContext.request.contextPath}/gameboard">
                                <button type="submit" class="form-control" id="try-again" name="reset" value="game" style="font-size:20px;width:200px;height:50px;display:inline-block;margin:10px;">Try Again?</button>
                            </form>
                        </c:when>
                        <c:when test="${gameBoard.gameOver && gameBoard.currentScore.points >= topThreeScores[2].points}">
                            <c:if test="${gameBoard.currentScore.points >= topThreeScores[0].points}">
                                <c:set var="color" value="#FFD700"/>
                                <c:set var="userPlace" value="FIRST"/>
                            </c:if>
                            <c:if test="${gameBoard.currentScore.points >= topThreeScores[1].points && gameBoard.currentScore.points < topThreeScores[0].points}">
                                <c:set var="color" value="#C0C0C0"/>
                                <c:set var="userPlace" value="SECOND"/>
                            </c:if>
                            <c:if test="${gameBoard.currentScore.points >= topThreeScores[2].points && gameBoard.currentScore.points < topThreeScores[1].points}">
                                <c:set var="color" value="#cd7f32"/>
                                <c:set var="userPlace" value="THIRD"/>
                            </c:if>
                            <div class="modal-body">
                                <h1><i class="fas fa-kiwi-bird" aria-hidden="true" style="font-size:75px;color:${color};margin:20px;"></i>
                                    Game Over.
                                    <i class="fas fa-kiwi-bird" aria-hidden="true" style="font-size:75px;color:${color};margin:20px;"></i>
                                </h1>
                                <p>Good news - you got a total of ${gameBoard.currentScore.points} pts and came in ${userPlace}!</p>
                                <p>What is your name genius?!</p>
                                <form action="${pageContext.request.contextPath}/highscores" method="POST">
                                    <input type="text" name="submitHighScore"/>
                                    <button type="submit" class="form-control" value="score" style="font-size:20px;width:200px;height:75px;margin:10px;">Submit Score & Start New Game</button>
                                </form>
                            </c:when>
                            <c:when test="${!gameBoard.gameOver && gameBoard.chosenWord.isComplete}">
                                <h1>You win!!!</h1>
                                <h1>+ ${gameBoard.chosenWord.points} pts</h1>
                                <form class="horizontal" action="${pageContext.request.contextPath}/gameboard">
                                    <button type="submit" class="form-control" id="try-again" name="new" value="word" style="font-size:20px;width:200px;height:50px;display:inline-block;margin:10px;">Get a New Word?</button>
                                </form>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

