/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Score;
import com.tsguild.wordguesser.model.Score;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author ahill
 */
public class HighScoresDaoTest {

    HighScoreDao testDao;

    Score emptyScore = new Score("Nobody", 0);

    Score[] testScores = {
        new Score("Kate", 120),
        new Score("Bill", 100),
        new Score("Evelyn", 67),
        new Score("Ada", 500),
        new Score("Babbage", 444),
        new Score("Grace", 324)
    };

    Score[] similarScores = {
        new Score("Kate", 150),
        new Score("Bill", 120),
        new Score("Evelyn", 89),
        new Score("Ada", 1000),
        new Score("Babbage", 555),
        new Score("Grace", 360)
    };

    Score adaScore = new Score("Ada Lovelace", 500);
    Score babbageScore = new Score("Charles Babbage", 444);
    Score graceScore = new Score("Grace Hopper", 324);
    Score billScore = new Score("Bill Gates", 100);
    Score barbaraScore = new Score("Barbara Liskov", 324);

    public HighScoresDaoTest() {
        // The empty HighScoreDaoImpl constructor doesn't load from file.
        // It's contents will be empty, so we can run normal CRUD tests
        testDao = new HighScoreDaoImpl();
    }

    /*  HIGH SCORES DAO TESTS
    
        The HighScoresDAO can CRUD Score Objects.
        The HighScoresDAOImpl CRUDs Score - and also persists them to a local JSON file.
            This local persistence means the scores can persist between uses or hot deploys of the web program 
            - but won't survive a clean/build/deploy.

        The DAO also has the ability to get the 'topThree' scores -
            This will _always_ return 3 scores, even if there are technically none stored.
            If there are less than 3 scores, the DAO returns extra scores w/ a name of Nobody & 0 points.
    
    
        Test Plan:
            > EMPTY DAO TEST
            - test no/args constructor (no file persistance)
                - The dao should be empty.
                - all 'getScore' should return null
                - 'getAllScores' should return an empty list
                - 'getTopThree' should return a list of 3, all Nobody/0 scores
                - all 'removeSore' should return null
                - all 'updateScore' should leave the dao unaffected/empty
             
            > CRUD TESTS
            - test add/get one score to/from an empty dao
                - construct an score dao
                - add a score
                - getScore should return score
                - getAllScores should return a list of one, w/ that score
                - 'getTopThree' should return a list of 3, w/ the added score first & 2 Nobody/0 scores
            
            - test adding/getting multiple scores to/from an empty dao
                - repeat above but w/ multiple scores

            - test add/remove one score to/from an empty dao
                - construct an empty score dao
                - add a score
                - remove should return the same score
                - getScore should now return null
                - getAllScores should return an empty list
                - getTopThree should return a list of 3 w/ all Nobody/0
            - test adding/removing multiple scores to/from an empty dao
                - repeat above w/ multiple scores
    
            - test add/update one score to/from an empty dao
                - construct an empty score dao
                - add a score
                - update the score w/ new Score object
                - dao should contain one score, and it should look like the 'updated' score, not the original
            - test add/update one score to/from an empty dao - updating multiple times
                - repeat above, but update the score multiple times
                - should still only contain one score, and it should look like most recent 'update'.
            - test adding/updating multiple scores to/from an empty dao
                - repeat above, but with multiple scores
    
            - test add/update/remove one score to/from an empty dao
                - construct an empty score dao
                - create a new score and add it
                - update score
                - remove score
                - test state change of dao w/ above ^^^, final state should be empty
            - test add/update/remove multiple scores to/from an empty dao
                - repeat above w/ multiple scores
    
            > FILE PERSISTANCE TESTS
            - test File Read works to spec
                - create a file to spec
                - create DAO w/ pointer to file
                - assert expected scores exist
            - test File Read works to spec
                - create a file to spec
                - create a DAO w/ pointer to file
                - Update DAO w/ new, unique score
                - reinstantiate DAO w/ pointer to file
                - assert original + new score exists within DAO
                
    */
    
    @Test
    public void testEmptyDao() {

        // Get all scores should never be null, just empty, if there are no scores being tracked.
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());

        // Double check there aren't some weird scores lurking inside from an accidental file load
        Assert.assertNull(testDao.getScore("Ada"));
        Assert.assertNull(testDao.getScore("Kate"));
        Assert.assertNull(testDao.getScore("Grace"));

        // Removing a non existant score shouldn't crash, and should return null
        Assert.assertNull(testDao.removeScore("Ada"));

        // Updating a non existant score shouldn't crash, and shouldn't accidentally add it or anything.
        testDao.updateScore(new Score("Cellophane", 52));

        // Get all scores should still be empty, and the 'updated' score shouldn't be added.
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());
        Assert.assertNull(testDao.getScore("Cellophane"));

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is no score, there should be three 'empty' scores @ first, second & third.
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

    }

    public void testAddGetOneScoreStartingEmpty() {

        Score lilysScore = new Score("Lily", 3);
        
        // If we add one score, you should be able to get it out again by its name.
        // And the size of all scores should increase by the number of scores added.
        // Add one score
        Score addedScore = testDao.addScore("Lily", 3);

        // Ensure that the returned score object looks like its supposed to
        Assert.assertNotNull(addedScore);
        Assert.assertEquals(testScores[0], addedScore);

        // getAllScores()'s list should increase in size, and also have the newly added score inside it.
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertFalse(testDao.getAllScores().isEmpty());
        Assert.assertEquals(1, testDao.getAllScores().size());
        Assert.assertEquals(testScores[0], testDao.getAllScores().get(0));

        // You should be able to 'get' the new score by it's name.
        Assert.assertNotNull(testDao.getScore(testScores[0].getName()));
        Assert.assertEquals(testDao.getScore(testScores[0].getName()), testScores[0]);

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is only 1 score tracked, it should be first, and have two 'empty' scores @ second & third.
        Assert.assertEquals(testScores[0], testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

    }

    @Test
    public void testAddGetManyScoreStartingEmpty() {

        // If we add a bunch of scores, you should be able to find all of them by their name.
        // And the size of all scores should increase by the number of scores added.
        for (int i = 0; i < testScores.length; i++) {
            // Double check the score we're about to add isn't in there.
            Assert.assertNotNull(testDao.getAllScores());
            Assert.assertEquals(i, testDao.getAllScores().size());
            Assert.assertFalse(testDao.getAllScores().contains(testScores[i]));

            // Add the new score
            Score addedScore = testDao.addScore(testScores[i].getName(), testScores[i].getPoints());
            // Ensure that the returned score object looks like its supposed to
            Assert.assertNotNull(addedScore);
            Assert.assertEquals(testScores[i], addedScore);

            // getAllScores()'s list should increase in size, and also have the newly added score inside it.
            Assert.assertEquals(i + 1, testDao.getAllScores().size());
            Assert.assertTrue(testDao.getAllScores().contains(testScores[i]));
            // You should be able to 'get' the new score by it's name.
            Assert.assertNotNull(testDao.getScore(testScores[i].getName()));
            Assert.assertEquals(testDao.getScore(testScores[i].getName()), testScores[i]);

            // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
            Assert.assertNotNull(testDao.getTopThree());
            Assert.assertFalse(testDao.getTopThree().isEmpty());
            Assert.assertEquals(3, testDao.getTopThree().size());
        }

    }

    @Test
    public void testAddRemoveOneScore() {

        // Add the new score
        Score addedScore = testDao.addScore(testScores[0].getName(), testScores[0].getPoints());

        // Now let's remove it!
        Score removedScore = testDao.removeScore(testScores[0].getName());
        // Make sure it's the same score we added
        Assert.assertEquals(addedScore, removedScore);

        // The DAO should be empty again
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());
        Assert.assertNull(testDao.getScore(testScores[0].getName()));

        // Updating the removed score shouldn't crash, but shouldn't re-add it
        testDao.updateScore(testScores[0].getName(), testScores[0].getPoints());
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());
        Assert.assertNull(testDao.getScore(testScores[0].getName()));

        // Removing it again, should just return a null
        Assert.assertNull(testDao.removeScore(testScores[0].getName()));

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is no score, there should be three 'empty' scores @ first, second & third.
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

    }

    @Test
    public void testAddRemoveManyScoreStartingEmpty() {

        Score[] addedScores = new Score[testScores.length];

        // Add each score and store it in the array for checking later
        for (int i = 0; i < testScores.length; i++) {
            addedScores[i] = testDao.addScore(testScores[i].getName(), testScores[i].getPoints());
        }

        // Now that we've added all the scores, there should be correct number in the dao
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertEquals(addedScores.length, testDao.getAllScores().size());

        // Now let's remove each one.
        for (int i = 0; i < addedScores.length; i++) {
            // As we remove scores the number of scores tracked should decrease
            Assert.assertEquals(addedScores.length - i, testDao.getAllScores().size());

            // Remove the score!
            Score removedScore = testDao.removeScore(addedScores[i].getName());
            // Check it's the same as the one we added earlier
            Assert.assertEquals(addedScores[i], removedScore);

            // Check that the number of scores decremented by 1
            Assert.assertNotNull(testDao.getAllScores());
            Assert.assertEquals(addedScores.length - (i + 1), testDao.getAllScores().size());

            // And double check that the removed score isn't in there anymore
            Assert.assertNull(testDao.getScore(addedScores[i].getName()));
            Assert.assertFalse(testDao.getAllScores().contains(addedScores[i]));
            // Also make sure that trying to remove the same score again, doesn't break things
            // And just returns null
            Assert.assertNull(testDao.removeScore(addedScores[i].getName()));
        }

    }

    @Test
    public void testAddUpdateScore() {

        // Add the new score
        Score addedScore = testDao.addScore(testScores[0].getName(), testScores[0].getPoints());
        // Now that it's been added, update it (same name, different points)
        testDao.updateScore(similarScores[0].getName(), similarScores[0].getPoints());

        // There should still only be _one_ score in there, since it was just updated, not adding more
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertEquals(1, testDao.getAllScores().size());
        Assert.assertTrue(testDao.getAllScores().contains(similarScores[0]));
        Assert.assertFalse(testDao.getAllScores().contains(testScores[0]));

        // The updated score should be in there under the same name
        Assert.assertNotNull(testDao.getScore(testScores[0].getName()));

        // However it should look like the score w/ the updated points
        Assert.assertEquals(similarScores[0], testDao.getAllScores().get(0));
        Assert.assertEquals(similarScores[0], testDao.getScore(similarScores[0].getName()));

    }

    @Test
    public void testAddUpdateScoreTwiceAndRemove() {

        // Add the new score
        Score addedScore = testDao.addScore(testScores[0].getName(), testScores[0].getPoints());
        // Now that it's been added, update it (same name, different points)
        testDao.updateScore(similarScores[0].getName(), similarScores[0].getPoints());
        // Change it back to the old number of points, using the overloaded update method
        testDao.updateScore(testScores[0]);

        // There should still only be one score, since it was just _updated_ not added
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertEquals(1, testDao.getAllScores().size());

        // Similarly, the score should look like the original again, not the updated
        Assert.assertEquals(testScores[0], testDao.getAllScores().get(0));
        Assert.assertFalse(testDao.getAllScores().contains(similarScores[0]));

        // It should also be accessible by name, and look like the original
        Assert.assertEquals(testScores[0], testDao.getScore(testScores[0].getName()));

        // If we remove it, it should look like the original added score
        Score removedScore = testDao.removeScore(testScores[0].getName());
        Assert.assertEquals(addedScore, removedScore);

        // The DAO should be empty again
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());
        Assert.assertNull(testDao.getScore(testScores[0].getName()));

        // Trying to update again shouldn't crash, but shouldn't re-add it
        testDao.updateScore(testScores[0].getName(), testScores[0].getPoints());
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertTrue(testDao.getAllScores().isEmpty());
        Assert.assertNull(testDao.getScore(testScores[0].getName()));

        // Removing it again, should just return a null
        Assert.assertNull(testDao.removeScore(testScores[0].getName()));

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is no score, there should be three 'empty' scores @ first, second & third.
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

    }

    @Test
    public void testAddUpdateAndTopThree() {

        // Add the new score
        Score firstScore = testDao.addScore("Ada Lovelace", 300);
        // Now that it's been added, update it (same name, different points)
        Score updatedFirstScore = new Score("Ada Lovelace", 500);
        testDao.updateScore(updatedFirstScore);

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is one score, it should be top, w/ 2 empty scores after.
        Assert.assertEquals(updatedFirstScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // Change it back to the old number of points, using the overloaded update method
        testDao.updateScore(firstScore);

        // If there is one score, it should be top - w/ the updated points, w/ 2 empty scores after.
        Assert.assertEquals(firstScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // Add another new score
        Score secondScore = testDao.addScore("Charles Babbage", 200);

        // If there are two scores, it should be top - w/ the updated points, w/ 1 empty scores after.
        Assert.assertEquals(firstScore, testDao.getTopThree().get(0));
        Assert.assertEquals(secondScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // Now lets update the second score to have more points than the first.
        Score updatedSecondScore = new Score("Charles Babbage", 700);
        testDao.updateScore(updatedSecondScore);

        // If there are two scores, it should be top - w/ the updated points, w/ 1 empty scores after.
        Assert.assertEquals(updatedSecondScore, testDao.getTopThree().get(0));
        Assert.assertEquals(firstScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // If we remove the top score, the next score should become top
        Score removedScore = testDao.removeScore(updatedSecondScore.getName());
        // check that the removed one matches the one we expect
        Assert.assertEquals(updatedSecondScore, removedScore);

        // If there is one score, it should be top - w/ the updated points, w/ 2 empty scores after.
        Assert.assertEquals(firstScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

    }

    @Test
    public void testAddAndTopThreeScoreStartingEmpty() {

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertFalse(testDao.getTopThree().isEmpty());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // If there is no score, there should be three 'empty' scores @ first, second & third.
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // If there is only 1 scores, it should be first, and have two 'empty' scores @ second & third.
        Score adaScore = testDao.addScore("Ada Lovelace", 500);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // If there are only 2 scores, they should be ranked, and have one 'empty' score @ third.
        Score billScore = testDao.addScore("Bill Gates", 100);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(billScore, testDao.getTopThree().get(1));
        Assert.assertEquals(emptyScore, testDao.getTopThree().get(2));

        // If they have they have more points than the second, they should take over as third, and second move down.
        Score kateScore = testDao.addScore("Kate Compton", 250);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(kateScore, testDao.getTopThree().get(1));
        Assert.assertEquals(billScore, testDao.getTopThree().get(2));

        // If they have they have less points than the third, they shouldn't show in the list.
        Score graceScore = testDao.addScore("Grace Hopper", 99);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(kateScore, testDao.getTopThree().get(1));
        Assert.assertEquals(billScore, testDao.getTopThree().get(2));
        Assert.assertFalse(testDao.getTopThree().contains(graceScore));

        // If they have they have more points than the third, they should take over as third, and the old ousted from the list.
        Score evelynScore = testDao.addScore("Evelyn Granville", 101);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(kateScore, testDao.getTopThree().get(1));
        Assert.assertEquals(evelynScore, testDao.getTopThree().get(2));

        // If they have they have more points than any other, they should move to the front.
        Score babbageScore = testDao.addScore("Charles Babbage", 750);

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(babbageScore, testDao.getTopThree().get(0));
        Assert.assertEquals(adaScore, testDao.getTopThree().get(1));
        Assert.assertEquals(kateScore, testDao.getTopThree().get(2));

        // If they have the same number of points, keep the alphabetically 'earlier' one higher.
        Score barbaraScore = testDao.addScore("Barbara Liskov", adaScore.getPoints());

        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());
        Assert.assertEquals(babbageScore, testDao.getTopThree().get(0));
        Assert.assertEquals(adaScore, testDao.getTopThree().get(1));
        Assert.assertEquals(barbaraScore, testDao.getTopThree().get(2));

    }

    @Test
    public void testFileReadFromFileDao() {
        // Recreate the file we're going to read w/ the dao to make sure of the contents.
        this.setUpTestFile("test-scores.json");
        // If you pass it the resource file name, and it's in the right format, it should load it into the map!
        testDao = new HighScoreDaoImpl("test-scores.json");

        // There should be 5 scores in there, loaded from the file.
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertFalse(testDao.getAllScores().isEmpty());
        Assert.assertEquals(5, testDao.getAllScores().size());

        // Regardless of how many scores are being tracked, the top three should always be a list of size 3.
        Assert.assertNotNull(testDao.getTopThree());
        Assert.assertEquals(3, testDao.getTopThree().size());

        // Since we wrote the file, we know that there are 5 scores in there.
        // Ada Lovelace w/ 500 pts, Charles Babbage w/ 444 , Grace Hopper w/ 324
        // Barbara Liskov w/ 324 & Bill Gates w/ 100
        // Therefore Ada should be first, charles second, and barbara 3rd (b/c she's first alphabetically)
        Assert.assertEquals(adaScore, testDao.getTopThree().get(0));
        Assert.assertEquals(babbageScore, testDao.getTopThree().get(1));
        Assert.assertEquals(barbaraScore, testDao.getTopThree().get(2));
    }

    @Test
    public void testFileReadThenWriteThenReadAgainDao() {
        // Recreate the file we're going to read w/ the dao to make sure of the contents.
        this.setUpTestFile("test-scores.json");
        // If you pass it the resource file name, and it's in the right format, it should load it into the map!
        testDao = new HighScoreDaoImpl("test-scores.json");
        int numScores = testDao.getAllScores().size();

        // If we add a score, it should persist to file
        // but double check there's not one like it in there before we add...
        Assert.assertNull(testDao.getScore("Beans"));
        Score beansScore = testDao.addScore("Beans", 0);

        // Therefore reading the file again should have 1+ the number of scores it would have previously.
        testDao = new HighScoreDaoImpl("test-scores.json");

        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertFalse(testDao.getAllScores().isEmpty());
        Assert.assertEquals(numScores + 1, testDao.getAllScores().size());

        // And if we try and get the Score, it should come back!
        Assert.assertEquals(beansScore, testDao.getScore("Beans"));

        // If we remove a score it should persist to file
        testDao.removeScore("Beans");

        // Therefore reading the file again should have the same number of scores it had originally.
        testDao = new HighScoreDaoImpl("test-scores.json");
        Assert.assertNotNull(testDao.getAllScores());
        Assert.assertFalse(testDao.getAllScores().isEmpty());
        Assert.assertEquals(numScores, testDao.getAllScores().size());

        // Similarly Beans shouldn't be in there anymore.
        Assert.assertNull(testDao.getScore("Beans"));

    }

    private void setUpTestFile(String resourceFile) {
        try {
            JSONArray scoreArray = new JSONArray();

            Score[] scores = {
                babbageScore,
                adaScore,
                graceScore,
                barbaraScore,
                billScore
            };

            for (Score aScore : scores) {
                JSONObject score = new JSONObject();
                score.put("name", aScore.getName());
                score.put("points", aScore.getPoints());
                scoreArray.add(score);
            }

            PrintWriter writer = new PrintWriter(new FileWriter(new ClassPathResource(resourceFile).getFile()));
            writer.println(scoreArray.toString());
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            System.out.println(ex.getClass().getSimpleName() + " occurred when attempting to load score file.");
            System.out.println(ex.getMessage());
        }
    }

}
