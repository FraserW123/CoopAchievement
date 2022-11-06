package com.example.coopachievement.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    String name;
    String description;
    int numMatchesPlayed = 0;
    int currentMatch = 0;

    public boolean isAccessed() {
        return isAccessed;
    }

    public void setAccessed(boolean accessed) {
        isAccessed = accessed;
    }

    boolean isAccessed;

    ArrayList<ScoreCalculator> matchesPlayed = new ArrayList<>();

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addMatch(ScoreCalculator match){
        matchesPlayed.add(match);
        System.out.println("added a match the size is now" + matchesPlayed.size());
    }

    public ScoreCalculator getMatch(){
        return matchesPlayed.get(numMatchesPlayed-1);
    }

    public int getNumMatchesPlayed(){
        return numMatchesPlayed;
    }
    public List<String> getMatchList(){
        ArrayList<String> matchList = new ArrayList<>();
        System.out.println("current number of matches is " + matchList.size());
        for(int i = 0; i<numMatchesPlayed; i++){
            matchList.add(matchesPlayed.get(i).getMatchName());
        }
        return matchList;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void addMatchesPlayed(){
        numMatchesPlayed++;
    }
}
