/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Word;
import java.util.List;

/**
 *
 * @author ahill
 */
public interface WordDao {
    
    public void loadWordSet(String setName);
    
    // CREATE
    public Word addWord(Word w);
    
    // READ
    public Word getWord(String word);
    public List<Word> getAllWords();
    
    // UPDATE
    public void updateWord(String oldWord, Word newWord);
    
    // DELETE
    public Word removeWord(String word);
}
