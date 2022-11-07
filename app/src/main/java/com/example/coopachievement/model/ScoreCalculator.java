package com.example.coopachievement.model;

public class ScoreCalculator {
    private static ScoreCalculator instance;
    int numPlayers;
    int Score;
    int WorstScore = 100;
    int PoorScore = 200;
    int LowScore = 300;
    int OkayScore = 400;
    int AlrightScore = 500;
    int GoodScore = 600;
    int GreatScore = 700;
    int SuperbScore = 800;
    int AmazingScore = 900;

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
        if (Score <= WorstScore*numPlayers){
            return "Goofy Goblins!";
        }
        else if (Score <= PoorScore*numPlayers){
            return "Timid Trolls!";
        }
        else if (Score <= LowScore*numPlayers){
            return "Zippy Zombies!";
        }
        else if (Score <= OkayScore*numPlayers){
            return "Spooky Spiders!";
        }
        else if (Score <= AlrightScore*numPlayers){
            return "Vicious Vampires!";
        }
        else if (Score <= GoodScore*numPlayers){
            return "Lucky Lions!";
        }
        else if (Score <= GreatScore*numPlayers){
            return "Fantastic Fairies!";
        }
        else if (Score <= SuperbScore*numPlayers){
            return "Supreme Serpents!";
        }
        else if (Score <= AmazingScore*numPlayers){
            return "Dancing Dragons!";
        }
        else{
            return "Ultimate Unicorns!";
        }
    }
}
