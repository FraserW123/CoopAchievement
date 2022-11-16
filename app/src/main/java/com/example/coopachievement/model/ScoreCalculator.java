package com.example.coopachievement.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the calculation of scores and assign setAchievementLevel according to the scores
 * also set up the worst/poor/good/great/okay/low scores with respect to number of players.
 */
public class ScoreCalculator {
    int numPlayers;
    int Score;
    int poorScore;
    int greatScore;
    int increment;
    String[] achievementNames = {"Goofy Goblins!","Timid Trolls!","Zippy Zombies!","Spooky Spiders!","Vicious Vampires!","Lucky Lions!","Fantastic Fairies!","Supreme Serpents!","Dancing Dragons!","Ultimate Unicorns!"};



    String difficulty = "";
    String name;

    List<String> levels = new ArrayList<>();


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public void setPoorScore(int poor_score){poorScore = poor_score;}
    public int getPoorScore(){return poorScore;}

    public void setGreatScore(int great_score){greatScore = great_score;}
    public int getGreatScore(){return greatScore;}

    int matchesPlayed = 0;
    private ArrayList<String> matchName = new ArrayList<>();

    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
    String date = time.format(format);

    public void setMatchName()
    {
        name = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score + " "+setAchievementLevel();
        matchesPlayed++;
        matchName.add(name);
    }

    public String getMatchName()
    {
        return name;
    }

    public int getMatchesPlayed() 
    {
        return matchesPlayed;
    }

    public void setDate(String time){date = time;}
    public String getDate(){return date;}

    public void setNumPlayers(int num_players){numPlayers = num_players;}

    public int getNumPlayers(){return numPlayers;}

    public void setScore(int score){Score = score;}

    public int getScore(){return Score;}

    public double difficultyMultiplier(){
        if(!difficulty.equals("")){
            switch (difficulty) {
                case "Easy":
                    return 0.75;
                case "Hard":
                    return 2.0;
                default:
                    return 1.0;
            }

        }
        return 1.0;

    }

    public String setAchievementLevel()
    {

        increment = (greatScore - poorScore) / 8;
        int length = achievementNames.length;
        for(int i = 0; i<length; i++){
            if(Score < (poorScore + (i*increment) * numPlayers)*difficultyMultiplier()){
                return achievementNames[i];
            }
        }
        return achievementNames[length-1];

    }


    public List<String> fillLevelsList(){
        increment = (greatScore - poorScore) / 8;

        levels.add(achievementNames[0] + " Score: "+ 0 + " - " + (poorScore)*difficultyMultiplier());
        int length = achievementNames.length;
        for(int i = 1; i<length-2; i++){
            levels.add(achievementNames[i] + " Score: " + ((poorScore+(i*increment))*difficultyMultiplier()) + " - " + ((poorScore+(i*increment))*difficultyMultiplier()*numPlayers));
        }
        levels.add(achievementNames[length-1] + " Score: > " + ((greatScore*numPlayers))*difficultyMultiplier());


        return levels;
    }
}
