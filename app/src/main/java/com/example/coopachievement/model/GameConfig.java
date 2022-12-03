package com.example.coopachievement.model;

import android.util.Log;

import com.example.coopachievement.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class describes/creates the structure for game configuration which gives the opportunity to add,
 *  delete,getcurrent games and matches. also provides the gamelist, it is a singleton class.
 */
public class GameConfig {
    private List<Game> gameName = new ArrayList<>();
    private int numGames = 0;
    private String[] themes = {"Mythical", "Planet", "Greek Gods"};
    int [] mythicIDs = {R.drawable.mythic_goblin,R.drawable.mythic_troll, R.drawable.mythic_zombies,
            R.drawable.mythic_phoenix, R.drawable.mythic_vampires,R.drawable.mythic_griffin, R.drawable.mythic_fairies,
            R.drawable.mythic_serpent,R.drawable.mythic_dragon,R.drawable.mythic_unicorn};


    int [] planetIDS ={R.drawable.planet_moon,R.drawable.planet_venus,R.drawable.planet_mars,R.drawable.planet_mercury,
            R.drawable.planet_jupiter, R.drawable.planet_saturn,R.drawable.planet_uranus,R.drawable.planet_neptune,
            R.drawable.planet_pluto,R.drawable.planet_galaxy};


    int[] godIDs = {R.drawable.god_dionysus, R.drawable.god_hermes, R.drawable.god_hephaestus, R.drawable.god_artemis,
            R.drawable.god_athena, R.drawable.god_apollo, R.drawable.god_ares, R.drawable.god_poseidon,
            R.drawable.god_hera,R.drawable.god_zeus};

    private String[] themeNames;
    private String gameTheme;
    private int themeIndex = 0;
    int currentGameIndex;
    private boolean isDelete;
    private boolean accessedMatches;

    private static GameConfig instance;
    private GameConfig()
    {

    }

    public static GameConfig getInstance()
    {
        if(instance == null)
        {
            instance = new GameConfig();
        }
        return instance;
    }

    public void setTheme(String[] theme) {
        this.themeNames = theme;
    }

    public String[] getThemeNames() {
        return themeNames;
    }

    public int[] getThemeIDs(){
        if(getThemeIndex() == 1){
            return planetIDS;
        }
        else if(getThemeIndex() == 2){
            return godIDs;
        }
        return mythicIDs;
    }

    public void setThemeIndex(int themeIndex){
        this.themeIndex = themeIndex;
    }

    public void incrementThemeIndex(){
        themeIndex++;
    }
    public int getThemeIndex(){// 0 = mythic, 1 = planets, 2 = gods
        return themeIndex%3;
    }
    public int getThemeOG(){return themeIndex;}


    public String getTheme(){
        System.out.println("theme index " + themeIndex);
        gameTheme = themes[getThemeIndex()];
        return gameTheme;
    }


    public boolean isAccessedMatches() {
        return accessedMatches;
    }

    public void setAccessedMatches(boolean accessedMatches)
    {
        this.accessedMatches = accessedMatches;
    }

    public void addGame(Game game)
    {
        gameName.add(game);
        numGames++;
        Log.i("games added", "added "+game.getName() + " games: " + getNumGame());
    }

    public void deleteGame(int pos)
    {
        Log.i("before","games before " + getNumGame());
        gameName.remove(pos);
        numGames--;
        isDelete = true;
        Log.i("after","games after " + getNumGame());
    }


    public Game getCurrentGame() {
        System.out.println("current game index " + currentGameIndex);
        return gameName.get(currentGameIndex);
    }

    public void setCurrentGameIndex(int currentGameIndex)
    {
        this.currentGameIndex = currentGameIndex;
    }

    public int getCurrentGameIndex() {
        return currentGameIndex;
    }

    public boolean getisDelete(){return isDelete;}

    public void setisDelete(){ isDelete=false;}

    public int getNumGame(){
        return numGames;
    }

    public Game getGame(int pos){return gameName.get(pos);}

    public void setGameList(ArrayList<Game> list) { gameName = list;}

    public List<Game> getGameList(){
        return gameName;
    }

    public List<String> getGamesNameList()
    {
        List<String> gameNameString = new ArrayList<>();
        numGames = gameName.size();
        for(int i = 0; i<numGames; i++)
        {
            gameNameString.add(gameName.get(i).getName());
        }
        return gameNameString;
    }
}
