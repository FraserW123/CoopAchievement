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
        numMatchesPlayed++;
        System.out.println("added a match the size is now" + matchesPlayed.size());
    }

    public void removeMatch(int index){
        matchesPlayed.remove(index);
        numMatchesPlayed--;

        for(int i = 0; i<numMatchesPlayed; i++){
            System.out.println(getMatchesNamesList().get(i));
        }
    }

    public ScoreCalculator getLatestMatch(){
        return matchesPlayed.get(currentMatch);
    }
    public ScoreCalculator getMatch(int index){return matchesPlayed.get(index);}
    public void setCurrentMatch(int index){currentMatch = index;}



    public int getNumMatchesPlayed(){
        return numMatchesPlayed;
    }




    public List<String> getMatchesNamesList(){
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

}
