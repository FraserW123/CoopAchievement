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
    int WorstScore = 100;
    int PoorScore = 200;
    int LowScore = 300;
    int OkayScore = 400;
    int AlrightScore = 500;
    int GoodScore = 600;
    int GreatScore = 700;
    int SuperbScore = 800;
    int AmazingScore = 900;

    String level;
    String name;


    int matchesPlayed = 0;
    private ArrayList<String> matchName = new ArrayList<>();


    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
    String date = time.format(format);

    public void setMatchName(){
        name = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score + " "+setAchievementLevel();
        matchesPlayed++;
        matchName.add(name);
    }

    public String getMatchName(){
        return name;
    }

    public void setDate(String date){this.date = date;}
    public String getDate(){return date;}



    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setNumPlayers(int num_players){numPlayers = num_players;}
    public int getNumPlayers(){return numPlayers;}

    public void setScore(int score){Score = score;}
    public int getScore(){return Score;}

    public String setAchievementLevel(){
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
