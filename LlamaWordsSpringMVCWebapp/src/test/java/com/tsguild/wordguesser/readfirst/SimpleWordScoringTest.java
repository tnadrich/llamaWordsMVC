/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.readfirst;

import com.tsguild.wordguesser.dao.WordDao;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Word;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ahill
 */
public class SimpleWordScoringTest {

    WordDao testDao;

    Word[] testWords = {
        new Word("Llama", 5),
        new Word("Pikachu", 0),
        new Word("Xanadu", 10),
        new Word("Quinoa", 6)
    };

    public SimpleWordScoringTest() {
    }
    
    /*  SIMPLE WORD SCORING TESTS:
    
        A word has a point value.
        Scores also have a point value. 
    
        Scores also have a method to update its total number of points by the words score
        if it is passed a word. 
    
        Test Plan:
            - add one word to score
            - add word to score multiple times
            - add mulitple words to score
    */
    
    @Test
    public void testOneWordAndScorePoints() {
        // A word is worth points.
        // Making a new word here, I can specify it is worth 10 points.
        Word word = new Word("Llama", 10);
        Assert.assertEquals(10, word.getPoints());

        // A score also has points.
        // If I make a new score, I can specify its starting points.
        // Here I've made sure that the score starts with 10 points.
        Score score = new Score("Lily", 10);
        Assert.assertEquals(10, score.getPoints());
        
        // If I then tell a score to 'addToScore' the word worth 10 points.
        // The word was worth 10, the score was at 10, so the total should now be 20.
        score.addToScore(word);
        Assert.assertEquals(20, score.getPoints());
        
    }

    @Test
    public void testOneWordAndScorePointsMultipleTimes() {
        // A word is worth points.
        // Making a new word here, I can specify it is worth 10 points.
        Word word = new Word("Llama", 10);
        Assert.assertEquals(10, word.getPoints());

        // A score also has points.
        // If I make a new score, I can specify its starting points.
        // Here I've made sure that the score starts with 0 points.
        Score score = new Score("Lily", 0);
        Assert.assertEquals(0, score.getPoints());
        
        // If I then tell a score to 'addToScore' the word (worth 10 points)
        // The score, starting at 0, should now total 10 points.
        score.addToScore(word);
        Assert.assertEquals(10, score.getPoints());
        
        // If I score the word a second time, the total score should be 20.
        score.addToScore(word);
        Assert.assertEquals(20, score.getPoints());
        
    }
    
    @Test
    public void addMultipleWordsToScore() {

        // If I make a new score worth 0 points
        Score score = new Score("Lily", 0);
        
        // And I iterate thru all of the test words
        // and add each word's points to my score
        for(Word wordToAddToScore : testWords){
            score.addToScore(wordToAddToScore);
        }
        
        // at the end I should have a score worth
        // the sum of all of the word scores in my test words array
        long scoreSum = 0;
        for(Word testWord : testWords){
            scoreSum += testWord.getPoints();
        }
        
        Assert.assertEquals("Score and total points of word should match", score.getPoints(), scoreSum);
        
    }
}
