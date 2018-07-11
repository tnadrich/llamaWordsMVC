/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Word;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ahill
 */
public class WordDTOTests {

    /*  WORD DAO TESTS
    **      Just a few extra tests to check on the Word object's state creation / modification
    **      are working appropriately.
    **      
    **      - Word construction
    **          If a word is constructed via String / Int constructor
    **          A word should be initialized to have the underlying structure of the String as it's 'word'
    **          and the int as its point value.
    **          
    **          It should also have a guess array that matches the String in length
    **          but have all letters instead substituted as '-' but all normal word punctuation ignored.
    **          
    **          It should also have a letter array that matches the String instance in length / characters
    **          And it should return as 'not complete'
     */
    @Test
    public void testNormalWordCreation() {

        // If I make a new word
        String wordString = "Llama";
        int wordPoints = 10;
        Word word = new Word(wordString, wordPoints);

        // Check the underlying word
        Assert.assertEquals("Word should match given String in constructor", word.getWord(), wordString);

        // Check the associated points
        Assert.assertEquals("Word should match given points in constructor", word.getPoints(), wordPoints);

        // The guess array should be initialized to all '_' characters
        // but be of the same length as the number of letters in the word
        Assert.assertNotNull(word.getGuesses());
        Assert.assertEquals("Llama".length(), word.getGuesses().length);
        Assert.assertEquals('_', word.getGuesses()[0]);
        Assert.assertEquals('_', word.getGuesses()[1]);
        Assert.assertEquals('_', word.getGuesses()[2]);
        Assert.assertEquals('_', word.getGuesses()[3]);
        Assert.assertEquals('_', word.getGuesses()[4]);

        // The letters array should be initialized to all the characters of the original word.
        Assert.assertNotNull(word.getLetters());
        Assert.assertEquals("Llama".length(), word.getLetters().length);
        Assert.assertEquals('L', word.getLetters()[0]);
        Assert.assertEquals('l', word.getLetters()[1]);
        Assert.assertEquals('a', word.getLetters()[2]);
        Assert.assertEquals('m', word.getLetters()[3]);
        Assert.assertEquals('a', word.getLetters()[4]);

    }

    @Test
    public void testWeirdWordCreation() {

        // If I make a new word
        String wordString = "Compound-Word";
        int wordPoints = 500;
        Word word = new Word(wordString, wordPoints);

        // Check the underlying word
        Assert.assertEquals("Word should match given String in constructor", word.getWord(), wordString);

        // Check the associated points
        Assert.assertEquals("Word should match given points in constructor", word.getPoints(), wordPoints);

        // The guess array should be initialized to all '_' characters unless it's the - character
        // but be of the same length as the number of letters in the word
        Assert.assertNotNull(word.getGuesses());
        Assert.assertEquals("Compound-Word".length(), word.getGuesses().length);
        Assert.assertEquals('_', word.getGuesses()[0]); // C
        Assert.assertEquals('_', word.getGuesses()[1]); // o
        Assert.assertEquals('_', word.getGuesses()[2]); // m
        Assert.assertEquals('_', word.getGuesses()[3]); // p
        Assert.assertEquals('_', word.getGuesses()[4]); // o
        Assert.assertEquals('_', word.getGuesses()[5]); // u
        Assert.assertEquals('_', word.getGuesses()[6]); // n
        Assert.assertEquals('_', word.getGuesses()[7]); // d
        Assert.assertEquals('-', word.getGuesses()[8]); // -
        Assert.assertEquals('_', word.getGuesses()[9]); // W
        Assert.assertEquals('_', word.getGuesses()[10]); // o
        Assert.assertEquals('_', word.getGuesses()[11]); // r
        Assert.assertEquals('_', word.getGuesses()[12]); // d
        

        // The letters array should be initialized to all the characters of the original word.
        Assert.assertNotNull(word.getLetters());
        Assert.assertEquals("Compound-Word".length(), word.getLetters().length);
        char[] wordLetters = "Compound-Word".toCharArray();
        for(int i = 0; i < "Compound-Word".length() ; i++){
            Assert.assertEquals(word.getLetters()[i], wordLetters[i]);
        }

    }
    
    @Test
    public void testWordPhraseCreation() {

        // If I make a new word
        String wordString = "Several Words Separated By Spaces";
        int wordPoints = 123456789;
        Word word = new Word(wordString, wordPoints);

        // Check the underlying word
        Assert.assertEquals("Word should match given String in constructor", word.getWord(), wordString);

        // Check the associated points
        Assert.assertEquals("Word should match given points in constructor", word.getPoints(), wordPoints);

        // The guess array should be initialized to all '_' characters
        // but be of the same length as the number of letters in the word
        Assert.assertNotNull(word.getGuesses());
        
        // If it's not a space, it should be a letter and thus hidden
        Assert.assertEquals(wordString.length(), word.getGuesses().length);
        for (int i = 0; i < wordString.length(); i++) {
            char c = wordString.charAt(i);
            if(c != ' '){
                Assert.assertEquals('_', word.getGuesses()[i]);
            } else{
                Assert.assertEquals(' ', word.getGuesses()[i]);
            }
        }

        // The letters array should be initialized to all the characters of the original word.
        Assert.assertNotNull(word.getLetters());
        Assert.assertEquals(wordString.length(), word.getLetters().length);
        for(int i = 0; i < "Compound-Word".length() ; i++){
            Assert.assertEquals(word.getLetters()[i], wordString.charAt(i));
        }

    }

}
