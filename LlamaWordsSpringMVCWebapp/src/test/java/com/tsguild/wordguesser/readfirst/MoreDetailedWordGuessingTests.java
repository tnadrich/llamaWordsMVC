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
public class MoreDetailedWordGuessingTests {

    /*  MORE DETAILED WORD GUESSING TESTS:
    
        There are several state changes when working with a word object...
        This should be expected given that there are several properties associated with a Word.
        
        There are two ways to build a word -
            - Word(String word)
            - Word(String word, int points)
        
        Each of these then takes the time to populate other word properties including the:
            - char[] letters
            - char[] guesses
    
        The letters array is simply the given word String, broken into pieces.
        However, the guesses array corresponds with which letters within the 'Word' have been revealed or still hidden.
    
        As in most Word Guessing games - a word starts out with all letters hidden!
        Given a word - you can use the guessLetter method to pass a letter to 'guess'.
        The guess will be correct if there are letters still hidden that match the guessed letter.
        The guess will be incorrect, if there are no letters that match, or if that letter has already been revealed.
    
        Once all letters have been guessed, a word can be considered 'complete'.
    
        The tests below run through a few more of the state checks for the above functionality.
    
        Test Plan:
            - Test Word Guess Array
                - Create a word, check the guess array is properly initialized to be hidden
                - guess a correct letter
                - check that the guess array has properly unhidden that letter (but not the others)
                - guess the other correct letters, & check that they unhide
                - guess several incorrect letters, & check that they do no unduly affect the word
                - check that the guess array is properly unhidden
            - Test Word Guess Completiton
                - Create a word & check that the guess array is properly initialized
                - check that the word still returns as incomplete (w/ still hidden letters)
                - Guess all the letters in the word
                - check that the word is now 'complete'.
                - reset the word (all hidden again)
                - check that the word is now incomplete again.
            - Test Word Guess Reset
                - Create a word
                - Guess all letters (& check guess array)
                - Reset the guesses
                - Check all letters are hidden
                - Guess all letters again (& assert works as expected)
            - guess an incorrect letter
            - guess a correct letter twice
     */
    @Test
    public void testWordGuessArray() {

        // If I make a new word
        Word word = new Word("Llama", 10);

        // The guess array should be initialized to all '_' characters
        // but be of the same length as the number of letters in the word
        Assert.assertEquals("Llama".length(), word.getGuesses().length);
        Assert.assertEquals('_', word.getGuesses()[0]);
        Assert.assertEquals('_', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('_', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // If I guess the letter L (uppercase)
        // it should return true, because 'Llama' contains 2 Ls.
        Assert.assertTrue(word.guessLetter('L'));

        // It should update the 'guess' character array
        // to have all L's revealed regardless of casing
        // all other letters should stay hidden as '_'
        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('_', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // If I guess an M, it should also be true
        Assert.assertTrue(word.guessLetter('M'));

        // and now the m should be revealed
        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('m', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // And finally an A
        Assert.assertTrue(word.guessLetter('A'));

        // The whole word should be revealed
        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('a', word.getGuesses()[2]);
        Assert.assertEquals('m', word.getGuesses()[3]);
        Assert.assertEquals('a', word.getGuesses()[4]);

        // If I guess a bunch of other letters, they should all return false
        // because the word has already been completed, and revealed
        Assert.assertFalse(word.guessLetter('l'));
        Assert.assertFalse(word.guessLetter('m'));
        Assert.assertFalse(word.guessLetter('a'));
        Assert.assertFalse(word.guessLetter('c'));
        Assert.assertFalse(word.guessLetter('v'));
        Assert.assertFalse(word.guessLetter('b'));

        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('a', word.getGuesses()[2]);
        Assert.assertEquals('m', word.getGuesses()[3]);
        Assert.assertEquals('a', word.getGuesses()[4]);

    }

    @Test
    public void testWordGuessCompletition() {
        // If I make a new word
        Word word = new Word("Llama", 10);

        // The guess array should be initialized to all '_' characters
        // but be of the same length as the number of letters in the word
        Assert.assertEquals("Llama".length(), word.getGuesses().length);
        Assert.assertEquals('_', word.getGuesses()[0]);
        Assert.assertEquals('_', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('_', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // The word shouldn't be marked as 'completed' until all the letters are guessed.
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('l'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('a'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('m'));
        // Now that it is entirely guessed it should be complete
        Assert.assertTrue(word.getIsComplete());

        // If you reset the guesses, it shouldn't be complete anymore
        word.resetGuesses();
        Assert.assertFalse(word.getIsComplete());
    }

    @Test
    public void testWordGuessReset() {

        // If I make a word
        Word word = new Word("Llama", 10);

        // And then completely guess it
        Assert.assertTrue(word.guessLetter('l'));
        Assert.assertTrue(word.guessLetter('a'));
        Assert.assertTrue(word.guessLetter('m'));

        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('a', word.getGuesses()[2]);
        Assert.assertEquals('m', word.getGuesses()[3]);
        Assert.assertEquals('a', word.getGuesses()[4]);

        // But reset the guesses on the word
        word.resetGuesses();

        // All of the characters in the Guess array should be reset to '_'
        Assert.assertEquals("Llama".length(), word.getGuesses().length);
        Assert.assertEquals('_', word.getGuesses()[0]);
        Assert.assertEquals('_', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('_', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // And I should be able to completely guess it again
        Assert.assertTrue(word.guessLetter('l'));
        Assert.assertTrue(word.guessLetter('a'));
        Assert.assertTrue(word.guessLetter('m'));

        Assert.assertEquals('L', word.getGuesses()[0]);
        Assert.assertEquals('l', word.getGuesses()[1]);
        Assert.assertEquals('a', word.getGuesses()[2]);
        Assert.assertEquals('m', word.getGuesses()[3]);
        Assert.assertEquals('a', word.getGuesses()[4]);

    }

    @Test
    public void testGuessingWeirdWordsWordsWithDashes() {
        Word word = new Word("Mixed-Up");
        Assert.assertEquals("Mixed-Up".length(), word.getGuesses().length);

        // Mixed should be hidden by '_'
        Assert.assertEquals('_', word.getGuesses()[0]); // M
        Assert.assertEquals('_', word.getGuesses()[1]); // I 
        Assert.assertEquals('_', word.getGuesses()[2]); // X
        Assert.assertEquals('_', word.getGuesses()[3]); // E
        Assert.assertEquals('_', word.getGuesses()[4]); // D

        // The dash should stay & be visible
        Assert.assertEquals('-', word.getGuesses()[5]);

        // Up should be hidden by '_'
        Assert.assertEquals('_', word.getGuesses()[6]); // U
        Assert.assertEquals('_', word.getGuesses()[7]); // P

        // Guessing all the words should return true
        // but it should not be complete until all letters are guessed
        // the dash shouldn't count.
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('M'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('i'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('x'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('e'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('d'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('U'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('p'));
        // Now that everything is guessed, it should be complete
        Assert.assertTrue(word.getIsComplete());

        // If you ever did try to guess the dash
        // it should return false
        Assert.assertFalse(word.guessLetter('-'));

    }
    
    @Test
    public void testGuessingWeirdWordsWordsWithSpaces() {
        Word word = new Word("Elephant Shrew", 3);
        Assert.assertEquals("Elephant Shrew".length(), word.getGuesses().length);

        // Elephant should be hidden by '_'
        Assert.assertEquals('_', word.getGuesses()[0]); // E
        Assert.assertEquals('_', word.getGuesses()[1]); // L
        Assert.assertEquals('_', word.getGuesses()[2]); // E
        Assert.assertEquals('_', word.getGuesses()[3]); // P
        Assert.assertEquals('_', word.getGuesses()[4]); // H
        Assert.assertEquals('_', word.getGuesses()[5]); // A
        Assert.assertEquals('_', word.getGuesses()[6]); // N
        Assert.assertEquals('_', word.getGuesses()[7]); // T

        // The dash should stay & be visible
        Assert.assertEquals(' ', word.getGuesses()[8]);

        // Shrew should be hidden by '_'
        Assert.assertEquals('_', word.getGuesses()[9]);  // S
        Assert.assertEquals('_', word.getGuesses()[10]); // H
        Assert.assertEquals('_', word.getGuesses()[11]); // R
        Assert.assertEquals('_', word.getGuesses()[12]); // E
        Assert.assertEquals('_', word.getGuesses()[13]); // W

        // Guessing all the words should return true
        // but it should not be complete until all letters are guessed
        // the dash shouldn't count.
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('e'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('l'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('p'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('h'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('a'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('n'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('t'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('s'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('r'));
        Assert.assertFalse(word.getIsComplete());
        Assert.assertTrue(word.guessLetter('w'));
        // Now that everything is guessed, it should be complete
        Assert.assertTrue(word.getIsComplete());

        // If you ever did try to guess the dash
        // it should return false
        Assert.assertFalse(word.guessLetter(' '));

    }

}
