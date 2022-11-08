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
    String level;
    String name;

    List<String> levels = new ArrayList<>();

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

    public String setAchievementLevel()
    {
        int increment = (greatScore - poorScore) / 8;

        if (Score < poorScore * numPlayers){
            return "Goofy Goblins!";
        }
        else if (Score < (poorScore + increment) * numPlayers){
            return "Timid Trolls!";
        }
        else if (Score < (poorScore + 2 * increment) * numPlayers){
            return "Zippy Zombies!";
        }
        else if (Score < (poorScore + 3 * increment) * numPlayers){
            return "Spooky Spiders!";
        }
        else if (Score < (poorScore + 4 * increment) * numPlayers){
            return "Vicious Vampires!";
        }
        else if (Score < (poorScore + 5 * increment) * numPlayers){
            return "Lucky Lions!";
        }
        else if (Score < (poorScore + 6 * increment) * numPlayers){
            return "Fantastic Fairies!";
        }
        else if (Score < (poorScore + 7 * increment) * numPlayers){
            return "Supreme Serpents!";
        }
        else if (Score < (greatScore) * numPlayers){
            return "Dancing Dragons!";
        }
        else{
            return "Ultimate Unicorns!";
        }
    }

    public List<String> fillLevelsList(){
        int increment = (greatScore - poorScore) / 8;

        levels.add("Goofy Goblins! is equal to and greater than " + 0);
        levels.add("Timid Trolls! is equal to and greater than " + (poorScore));
        levels.add("Zippy Zombies! is equal to and greater than " + ((poorScore + increment) * numPlayers));
        levels.add("Spooky Spiders! is equal to and greater than " + ((poorScore + 2 * increment) * numPlayers));
        levels.add("Vicious Vampires! is equal to and greater than " + ((poorScore + 3 * increment) * numPlayers));
        levels.add("Lucky Lions! is equal to and greater than " + ((poorScore + 4 * increment) * numPlayers));
        levels.add("Fantastic Fairies! is equal to and greater than " + ((poorScore + 5 * increment) * numPlayers));
        levels.add("Supreme Serpents! is equal to and greater than " + ((poorScore + 6 * increment) * numPlayers));
        levels.add("Dancing Dragons! is equal to and greater than " +((poorScore + 7 * increment) * numPlayers));
        levels.add("Ultimate Unicorns! is equal to and greater than " + (greatScore * numPlayers - 1));

        return levels;
    }
}
