package com.example.coopachievement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class describes/creates the structure for games played under the specific game name and description
 * also sets the name,description. also provides players to add delete, save matches and delete game/save
 */
public class Game {

    String name;
    String description;



    String difficulty = "";
    int poor_score;
    int great_score;
    int numMatchesPlayed = 0;
    int currentMatch = 0;
    boolean isAccessed;
    ArrayList<ScoreCalculator> matchesPlayed = new ArrayList<>();


    public boolean isAccessed()
    {
        return isAccessed;
    }
    public void setAccessed(boolean accessed)
    {
        isAccessed = accessed;
    }



    public Game(String name, String description, int poorScore, int greatScore)
    {
        this.name = name;
        this.description = description;
        this.poor_score = poorScore;
        this.great_score = greatScore;
    }

    public String getMatchDifficulty() {
        return difficulty;
    }

    public void setMatchDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void addMatch(ScoreCalculator match)
    {
        matchesPlayed.add(match);
        numMatchesPlayed++;
    }

    public void removeMatch(int index)
    {
        matchesPlayed.remove(index);
        numMatchesPlayed--;
        for(int i = 0; i<numMatchesPlayed; i++)
        {
            System.out.println(getMatchesNamesList().get(i));
        }
    }
    public List<ScoreCalculator> getMatchList(){
        return matchesPlayed;
    }
    public ScoreCalculator getLatestMatch(){
        return matchesPlayed.get(currentMatch);
    }

    public ScoreCalculator getMatch(int index){return matchesPlayed.get(index);}

    public void setCurrentMatch(int index){currentMatch = index;}

    public int getNumMatchesPlayed()
    {
        return numMatchesPlayed;
    }

    public List<String> getMatchesNamesList()
    {
        ArrayList<String> matchList = new ArrayList<>();
        System.out.println("current number of matches is " + matchesPlayed.size());
        for(int i = 0; i<numMatchesPlayed; i++)
        {
            System.out.println("adding " + i +"'th games to the list");
            matchList.add(matchesPlayed.get(i).getMatchName());
        }
        return matchList;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setPoorScore(int poorScore)
    {
        this.poor_score = poorScore;
    }
    public int getPoorScore()
    {
        return poor_score;
    }

    public void setGreatScore(int greatScore)
    {
        this.great_score = greatScore;
    }
    public int getGreatScore()
    {
        return great_score;
    }

}
