/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Word;
import java.util.List;

/**
 *
 * @author ahill
 */
public interface HighScoreDao {
    
    // CREATE
    public Score addScore(String name, long points);
    public Score addScore(Score score);
    
    // READ
    public Score getScore(String name);
    public List<Score> getAllScores();
    public List<Score> getTopThree();
    
    // UPDATE
    public void updateScore(String name, long points);
    public void updateScore(Score score);
    
    // DELETE
    public Score removeScore(String name);
}
