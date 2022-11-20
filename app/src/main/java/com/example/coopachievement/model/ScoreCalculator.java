package com.example.coopachievement.model;

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
    String[] achievementMythicNames = {"Goofy Goblins!","Timid Trolls!","Zippy Zombies!","Spooky Spiders!","Vicious Vampires!","Lucky Lions!","Fantastic Fairies!","Supreme Serpents!","Dancing Dragons!","Ultimate Unicorns!"};


    String difficulty = "";
    String name;

    List<String> levels = new ArrayList<>();

    int matchesPlayed = 0;
    private ArrayList<String> matchName = new ArrayList<>();

    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
    String date = time.format(format);


    public ScoreCalculator(){

    }

    public ScoreCalculator(int numPlayers, int Score, int poorScore, int greatScore){
        this.numPlayers = numPlayers;
        this.Score = Score;
        this.poorScore = poorScore;
        this.greatScore = greatScore;
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



    public void setMatchName()
    {
        String threshold = difficulty;
        if(threshold.equals("")){
            threshold = "Normal";
        }
        name = "Date: " + date+" Players: "+numPlayers + " Total score: " +Score + " "+setAchievementLevel() + " Difficulty " + threshold;
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
                    return 1.25;
                default:
                    return 1.0;
            }

        }
        return 1.0;

    }

    public String setAchievementLevel()
    {

        increment = (greatScore - poorScore) / 8;

        int length = achievementMythicNames.length;
        for(int i = 0; i<length-2; i++){
            if(Score <= ((poorScore + ((i)*increment)) * numPlayers)*difficultyMultiplier()){
                return achievementMythicNames[i];
            }
        }
        if(Score <= greatScore*numPlayers -1){
            return achievementMythicNames[length-2];
        }

        return achievementMythicNames[length-1];

    }


    public List<String> fillLevelsList(){
        increment = (greatScore-poorScore) / 8;


        levels.add(achievementMythicNames[0] + " Score: "+ 0 + " - " + (poorScore)*difficultyMultiplier()*numPlayers);
        int length = achievementMythicNames.length;
        System.out.println("length " + achievementMythicNames.length);
        for(int i = 1; i<length-1; i++){
            double minScore = ((poorScore + ((i-1)*increment)) * numPlayers)*difficultyMultiplier();
            double maxScore = (((poorScore + (i*increment)) * numPlayers)*difficultyMultiplier());
            if(i == length-2){ // second last case
                maxScore = (greatScore*numPlayers)*difficultyMultiplier();
            }
            levels.add(achievementMythicNames[i] + " Score: " + minScore + " - " + maxScore);
            System.out.println("increment " + i + " size of levels " + levels.size());

        }
        levels.add(achievementMythicNames[length-1] + " Score: ≥ " + ((greatScore*numPlayers))*difficultyMultiplier());

        System.out.println("length of this from calculator " + levels.size());
        return levels;
    }
}
