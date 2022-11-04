package com.example.coopachievement.model;

public class ScoreCalculator {
    private static ScoreCalculator instance;
    int numPlayers;
    int Score;
    int PoorScore = 100;
    int GreatScore = 1000;

    private ScoreCalculator(){

    }

    public static ScoreCalculator getCalculatorInstatnce(){
        if(instance == null){
            instance = new ScoreCalculator();
        }
        return instance;
    }

    public void setNumPlayers(int num_players){numPlayers = num_players;}
    public int getNumPlayers(){return numPlayers;}

    public void setScore(int score){Score = score;}
    public int getScore(){return Score;}

    public String achievementLevel(){
        if (Score <= PoorScore*numPlayers){
            return "Goofy Goblins!";
        }
        else if (Score >= GreatScore*numPlayers){
            return "Dancing Dragons!";
        }
        return "Upright Unicorns!";
    }
}
