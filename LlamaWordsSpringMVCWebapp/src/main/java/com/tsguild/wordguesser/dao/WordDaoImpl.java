/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author ahill
 */
public final class WordDaoImpl implements WordDao{

    private Set<Word> wordGroup = new TreeSet<>();
    
    public WordDaoImpl(){
        
    }
    
    public WordDaoImpl(String wordSet){
        this.loadWordSet(wordSet);
    }

    private int findMaxId(){
        OptionalInt x = wordGroup.stream().mapToInt(Word::getId).max();
        if(x.isPresent()){
            return x.getAsInt();
        }else{
            return 1;
        }
    }
    
    @Override
    public Word addWord(Word w) {
        if(w == null || this.getWord(w.getWord()) != null){
            return null;
        } else{
            w.setId(this.findMaxId() + 1);
            wordGroup.add(w);
            return w;
        }
    }

    @Override
    public Word getWord(String word) {
        if(word == null || word.isEmpty()){
            return null;
        }
        
        for (Word w : wordGroup) {
            if(word.equals(w.getWord())){
                return w;
            }
        }
        
        return null;
    }

    @Override
    public List<Word> getAllWords() {
        return new ArrayList<>(wordGroup);
    }

    @Override
    public void updateWord(String oldWord, Word w) {
        if(w != null && this.getWord(oldWord) != null){
            Word currentWord = this.getWord(oldWord);
            wordGroup.remove(currentWord);
            wordGroup.add(w);
        }        
    }

    @Override
    public Word removeWord(String word) {
        Word currentWord = this.getWord(word);
        if(currentWord != null){
            wordGroup.remove(currentWord);
        }
        return currentWord;
    }
    
    @Override
    public void loadWordSet(String wordSet){
        switch(wordSet){
            case "Camelids": 
                this.addWord(new Word("Llama", 20));
                this.addWord(new Word("Alpaca", -5));
                this.addWord(new Word("Guanaco", 18));
                this.addWord(new Word("Vicuna", 15));
                this.addWord(new Word("Camel", 7));
                this.addWord(new Word("Dromedary", 11));
                this.addWord(new Word("Bactrian", 6));
                this.addWord(new Word("Cria", 3));
                this.addWord(new Word("Camelidae", 10));
                break;
            case "Funny Animals": 
                this.addWord(new Word("Echidna", 20));
                this.addWord(new Word("Goblin Shark", -5));
                this.addWord(new Word("Potoo", 18));
                this.addWord(new Word("Earwig", 15));
                this.addWord(new Word("Pangolin", 7));
                this.addWord(new Word("Arrowhead crab", 11));
                this.addWord(new Word("Caracal", 6));
                this.addWord(new Word("Elephant Shrew", 3));
                this.addWord(new Word("Pharoah Cuttlefish", 10));
                break;
            case "Delicious": 
                this.addWord(new Word("Gnocchi", 20));
                this.addWord(new Word("Parfait", 7));
                this.addWord(new Word("Icecream", 3));
                this.addWord(new Word("Brisket", 5));
                this.addWord(new Word("Sashimi", 12));
                this.addWord(new Word("Tacos", 4));
                break;
            case "Dinosaurs": 
                this.addWord(new Word("Velociraptor", 10));
                this.addWord(new Word("Compsognathus", 15));
                this.addWord(new Word("Pachycephalosaurus", 12));
                this.addWord(new Word("Dilophosaurus", 8));
                this.addWord(new Word("Stegosaurus", 5));
                this.addWord(new Word("Tyrannosaurus", 5));
                this.addWord(new Word("Iguanodon", 8));
                this.addWord(new Word("Ankylosaurus", 8));
                this.addWord(new Word("Parasaurolophus", 12));
                this.addWord(new Word("Triceratops", 7));
                this.addWord(new Word("Brachiosaurus", 10));
                this.addWord(new Word("Microraptor", 11));
                this.addWord(new Word("Oviraptor", 13));
                this.addWord(new Word("Gallimimus", 15));
                this.addWord(new Word("Allosaurus", 6));
                this.addWord(new Word("Pteranodon", 25));
                this.addWord(new Word("Diplodocus", 10));
                break;
            case "Plane Trains Automobiles": 
                this.addWord(new Word("Mooney", 20));
                this.addWord(new Word("Monorail", 5));
                this.addWord(new Word("Cesna", 10));
                this.addWord(new Word("Archer", 8));
                this.addWord(new Word("Tram", 3));
                this.addWord(new Word("Corvette", 10));
                this.addWord(new Word("VW Beetle", 50));
                break;
            default: // I don't know that set!
                this.addWord(new Word("Wat"));
                this.addWord(new Word("Dunno"));
                this.addWord(new Word("Offbeat"));
                this.addWord(new Word("Mebbe"));
                this.addWord(new Word("Huh"));
                this.addWord(new Word("Mistaken"));
                this.addWord(new Word("Misunderstood"));
                this.addWord(new Word("Mixed-Up"));
        }
    }
}
