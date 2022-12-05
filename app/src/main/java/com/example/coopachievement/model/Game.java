package com.example.coopachievement.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * This class describes/creates the structure for games played under the specific game name and description
 * also sets the name,description. also provides players to add delete, save matches and delete game/save
 */
public class Game {

    private String name;
    private String description;

    private int poor_score;
    private int great_score;
    private int numMatchesPlayed = 0;
    private int currentMatch = 0;

    //private Bitmap boxImage;
    private String  boxImage;
    private int themeIndexSave = 0;
    private ArrayList<ScoreCalculator> matchesPlayed = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> getPlayers_score() {
        return players_score;
    }

    private ArrayList<ArrayList<Integer>> players_score = new ArrayList<>();


    public Game(String name, String description, int poorScore, int greatScore)
    {
        this.name = name;
        this.description = description;
        this.poor_score = poorScore;
        this.great_score = greatScore;
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

//    public void recycleImage(){
//        boxImage.recycle();
//    }


    public ArrayList<Integer> getPlayersScore(int index) {return players_score.get(index);}

    public void addPlayerScore(ArrayList<Integer> PlayerScores){this.players_score.add(PlayerScores);}
    public void setPlayersScore(ArrayList<Integer> PlayerScores, int index) {this.players_score.set(index,PlayerScores);}

    public int getThemeIndexSave() {
        return themeIndexSave;
    }

    public void setThemeIndexSave(int themeIndexSave) {
        this.themeIndexSave = themeIndexSave;
    }

    public void addMatch(ScoreCalculator match)
    {
        matchesPlayed.add(match);
        numMatchesPlayed++;
    }

    public void removeMatch(int index)
    {
        matchesPlayed.remove(index);
        numMatchesPlayed--;
        for(int i = 0; i<numMatchesPlayed; i++)
        {
            System.out.println(getMatchesNamesList().get(i));
        }
    }
    public List<ScoreCalculator> getMatchList(){
        return matchesPlayed;
    }

    public ScoreCalculator getLatestMatch(){
        return matchesPlayed.get(currentMatch);
    }

    public ScoreCalculator getMatch(int index){return matchesPlayed.get(index);}

    public void setCurrentMatch(int index){currentMatch = index;}
    public int getCurrentMatch(){return currentMatch;}

    public int getNumMatchesPlayed()
    {
        return numMatchesPlayed;
    }

    public List<String> getMatchesNamesList()
    {
        ArrayList<String> matchList = new ArrayList<>();
        System.out.println("current number of matches is " + matchesPlayed.size());
        for(int i = 0; i<numMatchesPlayed; i++)
        {
            System.out.println("adding " + i +"'th games to the list");
            matchList.add(matchesPlayed.get(i).getMatchName());
        }
        return matchList;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setPoorScore(int poorScore)
    {
        this.poor_score = poorScore;
    }
    public int getPoorScore()
    {
        return poor_score;
    }

    public void setGreatScore(int greatScore)
    {
        this.great_score = greatScore;
    }
    public int getGreatScore()
    {
        return great_score;
    }

}
