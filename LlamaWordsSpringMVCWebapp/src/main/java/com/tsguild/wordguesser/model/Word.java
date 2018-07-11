/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.model;

import java.util.Objects;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahill
 */
@Getter @Setter
public final class Word implements Comparable<Word>{

    private int id;
    private String word;
    private int points;
    private char[] letters;
    private char[] guesses;

    public Word(String word){
        this.word = word;
        letters = word.toCharArray();
        
        Random r = new Random();
        this.points = r.nextInt(word.length()) + 1;
        this.resetGuesses();
    }
    
    public Word(String word, int points){
        this.points = points;
        this.word = word;
        
        letters = word.toCharArray();
        this.resetGuesses();
    }
    
    public boolean getIsComplete(){
        boolean guessesAndLettersMatch = true;
        
        for (int i = 0; i < letters.length; i++) {
            if(letters[i] != guesses[i]){
                guessesAndLettersMatch = false;
                break;
            }
        }
        
        return guessesAndLettersMatch;
    }
    
    public void resetGuesses(){
        guesses = new char[letters.length];
        
        for (int i = 0; i < guesses.length; i++) {
            guesses[i] = '_';
        }
        
        // they don't have to guess spaces, dashes or commas if they occur
        for(int i = 0; i < letters.length; i++){
            if(letters[i] == ' ' || letters[i] == '-' || letters[i] == ',' ){
                guesses[i] = letters[i];
            }
        }
    }
    
    public boolean guessLetter(char x){
        boolean foundLetter = false;
        x = Character.toLowerCase(x);
        
        for (int i = 0; i < letters.length; i++) {
            
            if(Character.toLowerCase(letters[i]) == x && guesses[i] == '_'){
                foundLetter = true;
                guesses[i] = letters[i];
            }
        }
        
        return foundLetter;
    }
    
    public String getWord() {
        return word;
    }

    private void setWord(String word) {
        this.word = word;
    }

    public char[] getLetters() {
        return letters;
    }

    private void setLetters(char[] letters) {
        this.letters = letters;
    }

    public char[] getGuesses() {
        return guesses;
    }

    private void setGuesses(char[] guesses) {
        this.guesses = guesses;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        if (!Objects.equals(this.word, other.word)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
    
}
