/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.wordguesser.dao;

import com.tsguild.wordguesser.model.Score;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author ahill
 */
public class HighScoreDaoImpl implements HighScoreDao{
    
    private Map<String, Score> topScores = new HashMap<>();;
    private final String resourceFile;
    
    public HighScoreDaoImpl(){
        this.resourceFile = "";
    }
    
    public HighScoreDaoImpl(String resourceFile){
        this.resourceFile = resourceFile;
        FileReader reader = null;
        
        try {
            JSONParser parser = new JSONParser();
            reader = new FileReader(new ClassPathResource(this.resourceFile).getFile()); 
            JSONArray scoreArray = (JSONArray)parser.parse(reader);
            for (int i = 0; i < scoreArray.size(); i++) {
                JSONObject scoreJSON = (JSONObject)scoreArray.get(i);
                // Compile all potential answers into a list
                
                Score aScore = new Score();
                aScore.setName((String)scoreJSON.get("name"));
                aScore.setPoints((Long)scoreJSON.get("points"));
            
                topScores.put(aScore.getName(), aScore);

            }
            reader.close();
        } catch (ParseException | IOException | NumberFormatException ex) {
            System.out.println(ex.getClass().getSimpleName() + " occurred when attempting to load score file.");
            System.out.println(ex.getMessage());
            try{
                reader.close();
            } catch (Exception e){
                System.out.println("Something went wrong w/ close.");
            }
        }
    }

    @Override
    public Score addScore(String name, long points) {
        Score aScore = new Score();
        aScore.setName(name);
        aScore.setPoints(points);
        
        if(topScores.containsKey(name)){
            return null;
        }
        
        topScores.put(name, aScore);
        this.saveScores();
        return aScore;
    }
    
    @Override
    public Score addScore(Score scoreToAdd) {
        
        if(topScores.containsKey(scoreToAdd.getName())){
            return null;
        }
        
        topScores.put(scoreToAdd.getName(), scoreToAdd);
        this.saveScores();
        return scoreToAdd;
    }

    @Override
    public Score getScore(String name) {
        return topScores.get(name);
    }
    
    @Override
    public List<Score> getTopThree(){
        List<Score> topScores =
        this.getAllScores().stream()
                .sorted((Score s1, Score s2) -> 
                            s2.getPoints() - s1.getPoints() == 0 ? 
                                    s1.getName().compareTo(s2.getName()) : 
                                    (int)s2.getPoints() - (int)s1.getPoints()
                        )
                .collect(Collectors.toList());
        
        while(topScores.size() < 3){
            topScores.add(new Score("Nobody", 0));
        }
        
        while(topScores.size() > 3){
            topScores.remove(3);
        }
        
        return topScores;
    }

    @Override
    public List<Score> getAllScores() {
        return topScores.size() <= 0 ? new ArrayList<>() : new ArrayList<>(topScores.values());
    }

    @Override
    public void updateScore(Score score) {
        topScores.replace(score.getName(), score);
        this.saveScores();
    }
    
    @Override
    public void updateScore(String name, long points) {
        Score aScore = new Score();
        aScore.setName(name);
        aScore.setPoints(points);
        topScores.replace(name, aScore);
        this.saveScores();
    }

    @Override
    public Score removeScore(String name) {
        Score x = topScores.remove(name);
        this.saveScores();
        return x;
    }

    
    private void saveScores(){
        if(resourceFile == null || resourceFile.isEmpty()) return;
        
        try {
            JSONArray scoreArray = new JSONArray();
            for (Score aScore : this.getAllScores()) {
                JSONObject score = new JSONObject();
                score.put("name", aScore.getName());
                score.put("points", aScore.getPoints());
                scoreArray.add(score);
            }
            
            PrintWriter writer = new PrintWriter(new FileWriter(new ClassPathResource(this.resourceFile).getFile()));
            writer.println(scoreArray.toString());
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            System.err.println(ex.getClass().getSimpleName() + " occurred when attempting to load score file.");
            System.err.println(ex.getMessage()); 
        }
    }
    
}
