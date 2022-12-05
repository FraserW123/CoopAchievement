package com.example.coopachievement.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * This class describes the calculation of scores and assign setAchievementLevel according to the scores
 * also set up the worst/poor/good/great/okay/low scores with respect to number of players.
 */
public class ScoreCalculator {
    private int numPlayers;
    private int Score;
    private int poorScore;
    private int greatScore;
    private int increment;
    private String icons ;
    private String nextachievementicon;
    private double nextchievementscore;
    private int nextachievementscore;
    //private String[] achievementThemeNames = gameConfig.getThemeNames();
    private String[] achievementThemeNames = {"Goofy Goblins!","Timid Trolls!","Zippy Zombies!","Prideful Phoenixes!",
            "Vicious Vampires!","Glorious Griffins!","Fantastic Fairies!","Supreme Serpents!","Dancing Dragons!","Ultimate Unicorns!"};
    private ArrayList<Integer> players_score = new ArrayList<>();
    private String difficulty = "";
    private String name;
    private String  boxImage;




    public void setAchievementThemeNames(String[] achievementThemeNames){this.achievementThemeNames = achievementThemeNames;}
    private List<String> levels = new ArrayList<>();

    private int matchesPlayed = 0;
    private ArrayList<String> matchName = new ArrayList<>();
    private String date = "";

    public ScoreCalculator(){

    }

    public ArrayList<Integer> getPlayerScoresList(){return players_score;}

    public void setPlayersScore(ArrayList<Integer> PlayerScores) {this.players_score = PlayerScores;}

    public ScoreCalculator(int numPlayers, int Score, int poorScore, int greatScore){
        this.numPlayers = numPlayers;
        this.Score = Score;
        this.poorScore = poorScore;
        this.greatScore = greatScore;

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
        date = time.format(format);
    }

    public Bitmap getBoxImage() {
        if(boxImage != null){
            byte[] imageAsBytes = Base64.getDecoder().decode(boxImage.getBytes(StandardCharsets.UTF_8));
            return BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length);
        }
        return null;
    }

    public void setBoxImage(Bitmap boxImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boxImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b  = baos.toByteArray();
        this.boxImage = Base64.getEncoder().encodeToString(b);

    }


    public void editMatch(int numPlayers, int Score, int poorScore, int greatScore){
        this.numPlayers = numPlayers;
        this.Score = Score;
        this.poorScore = poorScore;
        this.greatScore = greatScore;
    }


    public String getDifficulty() {
        if(difficulty.equals("")){
            difficulty = "Normal";
        }
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public void setPoorScore(int poor_score){poorScore = poor_score;}
    public int getPoorScore(){return poorScore;}

    public void setGreatScore(int great_score){greatScore = great_score;}
    public int getGreatScore(){return greatScore;}


    public void setMatchName(String[] achievementThemeNames)
    {

        this.achievementThemeNames = achievementThemeNames;
        setAchievementLevel(achievementThemeNames);
        name = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score + " "+icons + " Difficulty " + getDifficulty();
        matchesPlayed++;
        matchName.add(name);
    }

    public String getMatchName()
    {
        return name;
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
                    return 1.25;
                default:
                    return 1.0;
            }

        }
        return 1.0;

    }

    public String setAchievementLevel(String achievementarray[])
    {

        this.achievementThemeNames = achievementarray;
        increment = (greatScore - poorScore) / 8;

        int length = achievementThemeNames.length;


        for(int i = 0; i<length-2; i++){
            if(Score <= ((poorScore + ((i)*increment)) * numPlayers)*difficultyMultiplier()){
                icons = achievementThemeNames[i];
                nextachievementicon = achievementThemeNames[i+1];
                return achievementThemeNames[i];
            }
        }
        if(Score <= greatScore*numPlayers -1){
            icons=achievementThemeNames[length-2];
            nextachievementicon = achievementThemeNames[length-1];
            return achievementThemeNames[length-2];
        }
        icons = achievementThemeNames[length-1];
        nextachievementicon = "the highest level";
        return achievementThemeNames[length-1];


    }

    public String nextAchievementlevelscore(String achievementarray[]){
        double minScore;
        for(int i=0;i<achievementarray.length;i++){
            if(i== achievementarray.length-1){
                minScore = (greatScore*numPlayers)*difficultyMultiplier();
                nextchievementscore = minScore;
            }else{
                minScore = ((poorScore + ((i-1)*increment)) * numPlayers)*difficultyMultiplier();
                nextchievementscore = minScore;
            }
            if(achievementarray[i]==nextachievementicon){
                double needed =nextchievementscore-getScore();
                return "U need "+needed+" To reach level "+nextachievementicon;
            }
            if(achievementarray[achievementarray.length-2] == nextachievementicon){
                double needed =nextchievementscore-getScore();
                return "U need "+needed+" To reach level "+nextachievementicon;
            }

        }
        return " U reached "+ nextachievementicon;

    }


    public List<String> fillLevelsList(String[] achievementNames){
        increment = (greatScore-poorScore) / 8;
        this.achievementThemeNames = achievementNames;

        levels.add(achievementThemeNames[0] + " Score: "+ 0 + " - " + (poorScore)*difficultyMultiplier()*numPlayers);
        int length = achievementThemeNames.length;
        for(int i = 1; i<length-1; i++){
            double minScore = ((poorScore + ((i-1)*increment)) * numPlayers)*difficultyMultiplier();
            double maxScore = (((poorScore + (i*increment)) * numPlayers)*difficultyMultiplier());
            if(i == length-2){ // second last case
                maxScore = (greatScore*numPlayers)*difficultyMultiplier();
            }
            levels.add(achievementThemeNames[i] + " Score: " + minScore + " - " + maxScore);

        }
        levels.add(achievementThemeNames[length-1] + " Score: ≥ " + ((greatScore*numPlayers))*difficultyMultiplier());

        return levels;
    }
}
