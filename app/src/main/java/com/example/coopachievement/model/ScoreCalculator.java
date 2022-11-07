package com.example.coopachievement.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ScoreCalculator {

    int numPlayers;
    int Score;
    int PoorScore = 100;
    int GreatScore = 1000;
    String level;
    String name;


    int matchesPlayed = 0;
    private ArrayList<String> matchName = new ArrayList<>();


    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
    String date = time.format(format);

    public void setMatchName(){
        name = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score + " "+getAchievementLevel();
        matchesPlayed++;
        matchName.add(name);
    }

    public String getMatchName(){
        return name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }


    public void setNumPlayers(int num_players){numPlayers = num_players;}
    public int getNumPlayers(){return numPlayers;}

    public void setScore(int score){Score = score;}
    public int getScore(){return Score;}


    public void setAchievementLevel(){
        if (Score <= PoorScore*numPlayers){
            level = "Goofy Goblins!";
        }
        else if (Score >= GreatScore*numPlayers){
            level = "Dancing Dragons!";
        }
        else{
            level = "Upright Unicorns!";
        }
    }
    public String getAchievementLevel(){
        return level;
    }
}
