/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser;

import com.tsguild.wordguesser.model.CurrentBoard;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.ops.LlamaWordsService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author capta
 */
@Controller
public class MainController {

    private LlamaWordsService service;

    @Inject
    public MainController(LlamaWordsService serviceLayer) {
        this.service = serviceLayer;
    }

    @RequestMapping(value = "/gameboard", method = RequestMethod.GET)
    public String displayGameBoardPage(HttpServletRequest request,
            Model dataForJSP) {
        CurrentBoard gameBoard = service.getGameState();
        if (gameBoard == null || gameBoard.getLivesLeft() <= 0) {
            gameBoard = service.startNewGame();
        }
        if (gameBoard != null && gameBoard.getChosenWord().getIsComplete()) {
            Score currentScore = gameBoard.getCurrentScore();
            int livesLeft = gameBoard.getLivesLeft();
            gameBoard = service.chooseNewWord();
            gameBoard.setCurrentScore(currentScore);
            gameBoard.setLivesLeft(livesLeft);
        }
        dataForJSP.addAttribute("gameBoard", gameBoard);
        return "gameBoard";
    }

    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public String getLetter(HttpServletRequest request,
            Model dataForJSP) {
        String letter = request.getParameter("letter");
        CurrentBoard gameBoard = service.getGameState();
        boolean correctGuess = gameBoard.getChosenWord().guessLetter(letter.charAt(0));

        if (!correctGuess) {
            gameBoard.setLivesLeft(gameBoard.getLivesLeft() - 1);
        }
        if (gameBoard.getChosenWord().getIsComplete()) {
            gameBoard.getCurrentScore().addToScore(gameBoard.getChosenWord());
        }
        gameBoard.getChosenLetters().add(letter.charAt(0));
        List<Score> topThreeScores = service.getTopThreeScores();
        dataForJSP.addAttribute("topThreeScores", topThreeScores);
        dataForJSP.addAttribute("gameBoard", gameBoard);
        return "gameBoard";

    }

    @RequestMapping(value = "/highscores", method = RequestMethod.GET)
    public String displayHighScoresPage(HttpServletRequest request,
            Model dataForJSP) {
        List<Score> topThreeScores = service.getTopThreeScores();
        dataForJSP.addAttribute("topThreeScores", topThreeScores);
        return "highScores";
    }

    @RequestMapping(value = "/highscores", method = RequestMethod.POST)
    public String submitHighScore(HttpServletRequest request,
            Model dataForJSP) {
        CurrentBoard gameBoard = service.getGameState();
        Score userHighScore = gameBoard.getCurrentScore();
        String highScoreName = request.getParameter("submitHighScore");
        userHighScore.setName(highScoreName);
        service.submitNewScore(userHighScore);
        dataForJSP.addAttribute("gameBoard", gameBoard);
        return "redirect:/highscores";
    }

}
