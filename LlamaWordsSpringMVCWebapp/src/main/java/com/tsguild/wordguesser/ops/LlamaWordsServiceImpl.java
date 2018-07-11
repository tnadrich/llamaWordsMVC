/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.ops;

import com.tsguild.wordguesser.dao.HighScoreDao;
import com.tsguild.wordguesser.dao.WordDao;
import com.tsguild.wordguesser.model.CurrentBoard;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Word;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 *
 * @author ahill
 */
public class LlamaWordsServiceImpl implements LlamaWordsService {

    private WordDao words;
    private HighScoreDao scores;
    private CurrentBoard currentBoard;

    @Inject
    public LlamaWordsServiceImpl(WordDao words,
            HighScoreDao scores) {
        this.words = words;
        this.scores = scores;
    }

    @Override
    public CurrentBoard startNewGame() {
        Random rGen = new Random();
        List<Word> wordList = getAllWords();
        int rNum = rGen.nextInt(wordList.toArray().length);
        Word newWord = wordList.get(rNum);

        this.currentBoard = new CurrentBoard(newWord, 5);

        return currentBoard;
    }

    @Override
    public CurrentBoard chooseNewWord() {
        Random rGen = new Random();
        List<Word> wordList = getAllWords();
        int rNum = rGen.nextInt(wordList.toArray().length);
        Word newWord = wordList.get(rNum);

        newWord.resetGuesses();

        this.currentBoard = new CurrentBoard(newWord, 5);

        return currentBoard;

    }

    @Override
    public CurrentBoard getGameState() {
        return currentBoard;
    }

    @Override
    public List<Score> getAllScores() {
        List<Score> scoreList = new ArrayList<>();
        scoreList = scores.getAllScores();
        return scoreList;
    }

    @Override
    public List<Score> getTopThreeScores() {
        List<Score> topThreeScores = scores.getTopThree();
        return topThreeScores;
    }

    @Override
    public boolean isInTopThree(Score potentialScore) {
        List<Score> topThreeScores = scores.getTopThree();
        boolean isInTop = false;
        for (Score compareScore : topThreeScores) {
            if (potentialScore.getPoints() >= compareScore.getPoints()) {
                isInTop = true;
            }
        }
        return isInTop;
    }

    @Override
    public void submitNewScore(Score score) {
        scores.addScore(score);
//        scores.addScore(score.getName(), score.getPoints());
    }

    @Override
    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<>();
        wordList = words.getAllWords();

        return wordList;
    }

}
