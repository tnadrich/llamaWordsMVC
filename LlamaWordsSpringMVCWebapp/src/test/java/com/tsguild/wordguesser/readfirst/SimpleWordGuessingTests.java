/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.readfirst;

import com.tsguild.wordguesser.model.Word;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ahill
 */
public class SimpleWordGuessingTests {

    public SimpleWordGuessingTests() {

    }

    /*  SIMPLE WORD GUESSING TESTS:
    
        A word keeps track of it's original 'word' string structure.
        It also keeps track of the state of hidden & unhidden words...
        aka the current state of 'guesses'.
    
        If you have a word, you can use the guessLetter method to pass in letters
        that you wish to try and 'guess'. If the letter would correctly reveal
        a hidden letter of the word, the method returns true. If it would not, false.
    
        Test Plan:
            - guess a word's letter correctly
            - guess a word's letter correctly (but in a different case)
            - guess an incorrect letter
            - guess a correct letter twice
    */
    
    @Test
    public void testCorrectSimpleWordGuess() {
        // A new word starts off with all its letters considered 'hidden'.
        Word justI = new Word("I", 0);
        
        // If you then try and guess a letter that is hidden
        // the guessLetter method should return true.
        boolean guessWasCorrect = justI.guessLetter('I');
        Assert.assertTrue(guessWasCorrect);

    }

    @Test
    public void testCorrectSimpleWordGuessCaseInsensitive() {
        // A new word starts off with all its letters considered 'hidden'.
        Word justA = new Word("A", 0);

        // Guessing a (correct) hidden letter should return true
        // regardless of the casing of both the letter guessed, and the hidden letter.
        boolean guessWasCorrect = justA.guessLetter('a');
        Assert.assertTrue(guessWasCorrect);

    }

    @Test
    public void testIncorrectSimpleWordGuess() {
        // A new word starts off with all its letters considered 'hidden'.
        Word justThe = new Word("the", 0);
        // If the letter was not revealed, because the letter is not a hidden part of the word,
        // it should return false. 
        boolean guessWasCorrect = justThe.guessLetter('i');
        Assert.assertFalse(guessWasCorrect);
        
        // It should still be false, even if you try and guess the wrong letter twice,
        // or in a different case.
        Assert.assertFalse(justThe.guessLetter('i'));
        Assert.assertFalse(justThe.guessLetter('I'));

        // Guessing other wrong letters should also return false.
        Assert.assertFalse(justThe.guessLetter('A'));
        Assert.assertFalse(justThe.guessLetter('x'));
        Assert.assertFalse(justThe.guessLetter('L'));

    }

    @Test
    public void testSimpleWordCorrectGuessTwice() {
        // A new word starts off with all its letters considered 'hidden'.
        Word justI = new Word("I", 0);

        // The first time a correct letter is guessed, and thus revealed
        // the guessLetter method should return true.
        boolean guessWasCorrect = justI.guessLetter('I');
        Assert.assertTrue(guessWasCorrect);

        // However, after it is has been guessed, the letter is no longer hidden.
        // Therefore, if guessed again, the guessLetter method should return false
        // because you are not revealing any new letters with the guess.
        guessWasCorrect = justI.guessLetter('I');
        Assert.assertFalse(guessWasCorrect);

    }

}
