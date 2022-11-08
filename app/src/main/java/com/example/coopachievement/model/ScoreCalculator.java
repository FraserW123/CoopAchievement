package com.example.coopachievement.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class ScoreCalculator {
    int numPlayers;
    int Score;
    int poorScore;
    int greatScore;
    String level;
    String name;

    public void setPoorScore(int poor_score){poorScore = poor_score;}
    public int getPoorScore(){return poorScore;}

    public void setGreatScore(int great_score){greatScore = great_score;}
    public int getGreatScore(){return greatScore;}

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

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setNumPlayers(int num_players){numPlayers = num_players;}
    public int getNumPlayers(){return numPlayers;}

    public void setScore(int score){Score = score;}
    public int getScore(){return Score;}

    public String setAchievementLevel(){
        int increment = (greatScore - poorScore) / 8;

        if (Score <= poorScore * numPlayers){
            return "Goofy Goblins!";
        }
        else if (Score <= (poorScore + increment) * numPlayers){
            return "Timid Trolls!";
        }
        else if (Score <= (poorScore + 2 * increment) * numPlayers){
            return "Zippy Zombies!";
        }
        else if (Score <= (poorScore + 3 * increment) * numPlayers){
            return "Spooky Spiders!";
        }
        else if (Score <= (poorScore + 4 * increment) * numPlayers){
            return "Vicious Vampires!";
        }
        else if (Score <= (poorScore + 5 * increment) * numPlayers){
            return "Lucky Lions!";
        }
        else if (Score <= (poorScore + 6 * increment) * numPlayers){
            return "Fantastic Fairies!";
        }
        else if (Score <= (poorScore + 7 * increment) * numPlayers){
            return "Supreme Serpents!";
        }
        else if (Score < (greatScore) * numPlayers){
            return "Dancing Dragons!";
        }
        else{
            return "Ultimate Unicorns!";
        }
    }
}
