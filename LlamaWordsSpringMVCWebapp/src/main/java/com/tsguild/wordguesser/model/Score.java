/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ahill
 */
@Data @AllArgsConstructor
@NoArgsConstructor
public class Score {
    
    private String name;
    private long points;
    
    public void addToScore(Word w){
        points += w.getPoints();
    }
    
}
