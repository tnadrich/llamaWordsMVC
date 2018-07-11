/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.ops;

import com.tsguild.wordguesser.model.CurrentBoard;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Word;
import java.util.List;

/**
 *
 * @author ahill
 */
public interface LlamaWordsService {
    
    public CurrentBoard startNewGame();
    public CurrentBoard chooseNewWord();
    public CurrentBoard getGameState();
    
    public List<Score> getAllScores();
    public List<Score> getTopThreeScores();
    public boolean isInTopThree(Score potentialScore);
    public void submitNewScore(Score score);
    
    public List<Word> getAllWords();
       
}
