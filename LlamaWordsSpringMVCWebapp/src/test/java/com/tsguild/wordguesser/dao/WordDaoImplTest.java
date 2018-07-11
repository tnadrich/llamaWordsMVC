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
public class WordDaoImplTest {

    WordDao testDao;

    Word[] testWords = {
        new Word("Llama", 5),
        new Word("Pikachu", 0),
        new Word("Xanadu", 10),
        new Word("Quinoa", 6)
    };

    Word[] moreWords = {
        new Word("Fancy"),
        new Word("Pants"),
        new Word("Beans"),
        new Word("Zany")
    };

    public WordDaoImplTest() {
        testDao = new WordDaoImpl();
    }

    /*  WORD DAO TESTS
    
        The WordDAO can CRUD Word Objects.
        The WordDAOImpl CRUDs Words - storing them in an in memory Set data structure.
        (This is important, as Word objects are effectively equal based on their underlying word String, 
            irregardless of the state of the rest of the object properties).
        
        WordDAOs can also 'loadWordSets' which can populate the Set w/ some predefined words & points.
        
        Test Plan:
            - test no/args constructor
                - The dao should be empty.
                - all 'getWord' should return null
                - 'getAllWords' should return an empty list
                - all 'removeWord' should return null
                - all 'updateWord' should leave the dao unaffected/empty

            - test overloaded constructor
                - build a dao w/ a word set parameter
                - assert there are the proper number of words from that set
                - and getting them out gets the right word
                
             
            > CRUD TESTS
            - test add/get one word to/from an empty dao
                - construct an empty word dao
                - add a word
                - getWord should return word
                - getAllWords should return a list of one, w/ that word
            
            - test adding/getting multiple words to/from an empty dao
                - repeat above but w/ multiple words

            - test add/remove one word to/from an empty dao
                - construct an empty word dao
                - add a word
                - remove should return the same word
                - getWord should now return null
                - getAllWords should return an empty list
            - test adding/removing multiple words to/from an empty dao
                - repeat above w/ multiple words
    
            > Update tests
            - test add/update one word to/from an empty dao
                - construct an empty word dao
                - add a word
                - update the word w/ new Word object
                - dao should contain one word, and it should look like the 'updated' word, not the original
            - test add/update one word to/from an empty dao - updating multiple times
                - repeat above, but update the word multiple times
                - should still only contain one word, and it should look like most recent 'update'.
            - test adding/updating multiple words to/from an empty dao
                - repeat above, but with multiple words
    
            - test add/update/remove one word to/from an empty dao
                - construct an empty word dao
                - create a new word and add it
                - update word
                - remove word
                - test state change of dao w/ above ^^^, final state should be empty
            - test add/update/remove multiple words to/from an empty dao
                - repeat above w/ multiple words
                
    */
    
    @Test
    public void testEmptyDao() {

        // When the dao is made as empty
        // All the words should never be null, just empty
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());

        // Trying to get non existant words shouldn't crash it, but return null
        Assert.assertNull(testDao.getWord("Word"));
        Assert.assertNull(testDao.getWord("Fancy"));
        Assert.assertNull(testDao.getWord("Silly"));

        // And removing non existant words should also not crash, but return null
        Assert.assertNull(testDao.removeWord("Invisible"));

        // same goes for updating non existant words
        testDao.updateWord("Mister", new Word("Cellophane"));
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());
        Assert.assertNull(testDao.getWord("Cellophane"));

    }

    @Test
    public void testLoadedDao() {
        
        // Construct a DAO w/ the Camelid WordSet
        testDao = new WordDaoImpl("Camelids");
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertEquals("Should be full of camelid words", testDao.getAllWords().size(), 9);
        Assert.assertNotNull("Should have Llama in there now ...", testDao.getWord("Llama"));
        
        
        // Construct a DAO w/ the Dinosaur WordSet
        testDao = new WordDaoImpl("Dinosaurs");
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertEquals("Should be full of dinosaur words", testDao.getAllWords().size(), 17);
        Assert.assertNotNull("Should have Velociraptors in there now ...", testDao.getWord("Velociraptor"));
        
    }
    
    @Test
    public void testAddGetOneWordStartingEmpty() {

        // If you add a word
        testDao.addWord(testWords[0]);

        // There should be one word in there.
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertFalse(testDao.getAllWords().isEmpty());
        Assert.assertEquals(1, testDao.getAllWords().size());
        
        // And it should be equal to the word you added.
        Assert.assertEquals(testWords[0], testDao.getAllWords().get(0));

        // If you try and get that word by it's String value, it should come back
        Assert.assertNotNull(testDao.getWord(testWords[0].getWord()));
        Assert.assertEquals(testWords[0], testDao.getWord(testWords[0].getWord()));

    }

    @Test
    public void testAddGetManyWordStartingEmpty() {

        // If you add a bunch of words
        for (int i = 0; i < testWords.length; i++) {
            // The size stored should increase
            Assert.assertNotNull(testDao.getAllWords());
            Assert.assertEquals(i, testDao.getAllWords().size());
            Assert.assertFalse(testDao.getAllWords().contains(testWords[i]));

            testDao.addWord(testWords[i]);

            // And you should see the newly added words in the stores
            Assert.assertEquals(i + 1, testDao.getAllWords().size());
            Assert.assertTrue(testDao.getAllWords().contains(testWords[i]));
            Assert.assertNotNull(testDao.getWord(testWords[i].getWord()));
            Assert.assertEquals(testWords[i], testDao.getWord(testWords[i].getWord()));
        }

    }

    @Test
    public void testAddRemoveOneWord() {

        // If you add a word
        testDao.addWord(testWords[0]);
        // And then remove it it should return the same word
        Word removed = testDao.removeWord(testWords[0].getWord());

        // The dao should be empty again
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());
        
        // And trying to retrieve it shouldn't crash, but should return null
        Assert.assertNull(testDao.getWord(testWords[0].getWord()));

        // Similarly trying to update it shouldn't do anything, but also not crash
        testDao.updateWord(testWords[0].getWord(), testWords[0]);
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());
        Assert.assertNull(testDao.getWord(testWords[0].getWord()));

        // Make sure that those words are completely equal
        // since equals is overridden just for the String value of the word.
        checkWordsAreTotallyEqual(testWords[0], removed);

        // If you try to remove it again, nothing should happen, and it should return null
        Assert.assertNull(testDao.removeWord(testWords[0].getWord()));

    }

    @Test
    public void testAddRemoveManyWordStartingEmpty() {

        // so if you add a bunch of words
        for (int i = 0; i < testWords.length; i++) {
            testDao.addWord(testWords[i]);
        }

        // All should be contained within in the DAO
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().size() == testWords.length);

        
        for (int i = 0; i < testWords.length; i++) {
            // And as you remove them the stored number should shrink
            Assert.assertEquals(testWords.length - i, testDao.getAllWords().size() );
            
            // They should be exactly the same
            checkWordsAreTotallyEqual(testWords[i], testDao.getWord(testWords[i].getWord()));
            Word removed = testDao.removeWord(testWords[i].getWord());
            checkWordsAreTotallyEqual(testWords[i], removed);
            
            // And once it's gone, it shouldn't be in the dao anymore
            Assert.assertNotNull(testDao.getAllWords());
            Assert.assertEquals(testWords.length - (i + 1), testDao.getAllWords().size());
            Assert.assertNull(testDao.getWord(testWords[i].getWord()));
            Assert.assertFalse(testDao.getAllWords().contains(testWords[i]));
            Assert.assertNull(testDao.removeWord(testWords[i].getWord()));
        }

    }

    @Test
    public void testAddUpdateWord() {

        // After you add a word
        testDao.addWord(testWords[0]);
        // you should be able to update it
        testDao.updateWord(testWords[0].getWord(), moreWords[0]);

        // This should not increase the number of words stored
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertEquals(1, testDao.getAllWords().size());
        // Just update the word so that it looks like the new values instead of the old
        Assert.assertTrue(testDao.getAllWords().contains(moreWords[0]));
        Assert.assertFalse(testDao.getAllWords().contains(testWords[0]));

        Assert.assertNotNull(testDao.getWord(moreWords[0].getWord()));
        Assert.assertNull(testDao.getWord(testWords[0].getWord()));

        checkWordsAreTotallyEqual(testDao.getAllWords().get(0), moreWords[0]);
        checkWordsAreTotallyEqual(testDao.getWord(moreWords[0].getWord()), moreWords[0]);

    }

    @Test
    public void testAddUpdateWordTwice() {

        // If you add a word
        testDao.addWord(testWords[0]);
        // And then update it
        testDao.updateWord(testWords[0].getWord(), moreWords[0]);
        // and update it again
        testDao.updateWord(moreWords[0].getWord(), testWords[0]);

        // It should still only have one word
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertEquals(1, testDao.getAllWords().size());
        
        // And that word should look like the last value it was updated to be
        Assert.assertTrue(testDao.getAllWords().contains(testWords[0]));
        Assert.assertFalse(testDao.getAllWords().contains(moreWords[0]));

        Assert.assertNotNull(testDao.getWord(testWords[0].getWord()));
        Assert.assertNull(testDao.getWord(moreWords[0].getWord()));

        checkWordsAreTotallyEqual(testDao.getAllWords().get(0), testWords[0]);
        checkWordsAreTotallyEqual(testDao.getWord(testWords[0].getWord()), testWords[0]);

    }

    @Test
    public void testAddUpdateManyWordStartingEmpty() {

        // If you add a bunch of words
        for (int i = 0; i < testWords.length; i++) {
            testDao.addWord(testWords[i]);
        }

        // And then update all of them to look different
        for (int i = 0; i < testWords.length; i++) {
            // They should look like their original value
            checkWordsAreTotallyEqual(testDao.getWord(testWords[i].getWord()), testWords[i]);
            Assert.assertNull(testDao.getWord(moreWords[i].getWord()));
            Assert.assertTrue(testDao.getAllWords().contains(testWords[i]));
            Assert.assertFalse(testDao.getAllWords().contains(moreWords[i]));

            // Until you update the word
            testDao.updateWord(testWords[i].getWord(), moreWords[i]);

            // And then it should look like the newly updated value
            Assert.assertTrue(testDao.getAllWords().contains(moreWords[i]));
            Assert.assertFalse(testDao.getAllWords().contains(testWords[i]));
            Assert.assertNull(testDao.getWord(testWords[i].getWord()));
            checkWordsAreTotallyEqual(testDao.getWord(moreWords[i].getWord()), moreWords[i]);
        }

        // If you do the updates again to change it back to look like the original word
        for (int i = 0; i < testWords.length; i++) {
            // They should look like the updated value
            checkWordsAreTotallyEqual(testDao.getWord(moreWords[i].getWord()), moreWords[i]);
            Assert.assertNull(testDao.getWord(testWords[i].getWord()));
            Assert.assertTrue(testDao.getAllWords().contains(moreWords[i]));
            Assert.assertFalse(testDao.getAllWords().contains(testWords[i]));

            // Until you update them
            testDao.updateWord(moreWords[i].getWord(), testWords[i]);

            // And then it should look like it did when you first added it
            Assert.assertTrue(testDao.getAllWords().contains(testWords[i]));
            Assert.assertFalse(testDao.getAllWords().contains(moreWords[i]));
            Assert.assertNull(testDao.getWord(moreWords[i].getWord()));
            checkWordsAreTotallyEqual(testDao.getWord(testWords[i].getWord()), testWords[i]);
        }

        // However, this should just keep the number of words equal to the number added
        // as updating shouldn't create extra words in the dao, just change existing ones
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().size() == testWords.length);

    }

    @Test
    public void testAddUpdateRemoveOneWordStartingEmpty() {
        // if you add a word
        testDao.addWord(testWords[0]);
        // and then update it
        testDao.updateWord(testWords[0].getWord(), moreWords[0]);

        // And then remove that word
        Word removed = testDao.removeWord(moreWords[0].getWord());
        // the returned removed word should look like the updated value
        // not the original one
        this.checkWordsAreTotallyEqual(removed, moreWords[0]);

        // And now the dao should be empty of words
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());

        Assert.assertNull(testDao.getWord(moreWords[0].getWord()));
        Assert.assertNull(testDao.getWord(testWords[0].getWord()));

        Assert.assertNull(testDao.removeWord(moreWords[0].getWord()));
        Assert.assertNull(testDao.removeWord(testWords[0].getWord()));

    }

    @Test
    public void testAddUpdateRemoveManyWordStartingEmpty() {

        // If you add a bunch of words
        for (int i = 0; i < testWords.length; i++) {
            testDao.addWord(testWords[i]);
        }

        // update them to look different
        for (int i = 0; i < moreWords.length; i++) {
            testDao.updateWord(testWords[i].getWord(), moreWords[i]);
        }

        // and then remove them
        for (int i = 0; i < moreWords.length; i++) {
            Word removed = testDao.removeWord(moreWords[i].getWord());
            // the removed word should look like the updated word, not the original
            this.checkWordsAreTotallyEqual(removed, moreWords[i]);

            // The size of words stored should change as the words get removed
            Assert.assertNotNull(testDao.getAllWords());
            Assert.assertEquals(moreWords.length - (i + 1), testDao.getAllWords().size());
            Assert.assertNull(testDao.getWord(moreWords[i].getWord()));
            Assert.assertNull(testDao.getWord(testWords[i].getWord()));
            // also trying to remove the same word multiple times shouldn't break anything
            // but shouldn't mess with the size of anything either
            Assert.assertNull(testDao.removeWord(moreWords[i].getWord()));
            Assert.assertNull(testDao.removeWord(testWords[i].getWord()));
        }

        // After everything is removed, it should be empty again
        Assert.assertNotNull(testDao.getAllWords());
        Assert.assertTrue(testDao.getAllWords().isEmpty());
    }

    public void checkWordsAreTotallyEqual(Word expected, Word calculated) {
        // Since the a Word's equal method has been removed to not take
        // all its properties into consideration, this does that job for us
        Assert.assertEquals(expected, calculated);
        Assert.assertEquals(expected.getId(), calculated.getId());
        Assert.assertEquals(expected.getWord(), calculated.getWord());
        Assert.assertEquals(expected.getPoints(), calculated.getPoints());

        Assert.assertArrayEquals(expected.getGuesses(), calculated.getGuesses());
        Assert.assertArrayEquals(expected.getLetters(), calculated.getLetters());
    }

}
