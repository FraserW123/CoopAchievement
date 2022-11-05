package com.example.coopachievement.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@RequiresApi(api = Build.VERSION_CODES.O)
@RequiresApi(api = Build.VERSION_CODES.O)
public class ScoreCalculator {
    private static ScoreCalculator instance;
    int numPlayers;
    int Score;
    int PoorScore = 100;
    int GreatScore = 1000;
    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d @ HH:mm a");
    String date = time.format(format);
    private ArrayList<String> matchName = new ArrayList<>();


    boolean accessed = false;
    int gameIndex = 0;


    private ScoreCalculator(){

    }

    public static ScoreCalculator getCalculatorInstance(){

        if(instance == null){
            instance = new ScoreCalculator();
        }
        return instance;
    }
    public List<String> getMatches(){
        return matchName;
    }
    public void accessed(boolean choice){
        accessed = choice;
    }
    public boolean isAccessed(){
        return accessed;
    }

    public int getGameIndex() {
        return gameIndex;
    }
    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public void setMatchName(){
        String ret = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score;
        matchName.add(ret);
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
