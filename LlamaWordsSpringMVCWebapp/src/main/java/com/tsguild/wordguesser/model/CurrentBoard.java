/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahill
 */
@Getter @Setter
public class CurrentBoard {

    public final char[] ALL_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    private Score currentScore;
    private Word chosenWord;
    private int livesLeft;
    private List<Character> chosenLetters;
    
    public CurrentBoard(Word firstWord, int lives){
        this.chosenWord = firstWord;
        this.livesLeft = lives;
        this.chosenLetters = new ArrayList<>();
        this.currentScore = new Score("???", 0);
    }
    
    public boolean isGameOver(){
        return livesLeft <= 0;
    }
    
}
