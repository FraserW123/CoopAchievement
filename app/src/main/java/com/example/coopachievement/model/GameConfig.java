package com.example.coopachievement.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class GameConfig {
    private List<Game> gameName = new ArrayList<>();
    private int numGames = 0;
    private boolean isDelete;

    private static GameConfig instance;
    private GameConfig(){

    }

    public static GameConfig getInstance(){
        if(instance == null){
            instance = new GameConfig();
        }
        return instance;
    }

    public void addGame(Game game){
        gameName.add(game);
        numGames++;
        Log.i("games added", "added "+game.getName() + " games: " + getNumGame());
    }

    public void deleteGame(int pos){
        Log.i("before","games before " + getNumGame());
        gameName.remove(pos);
        numGames--;
        isDelete = true;
        Log.i("after","games after " + getNumGame());
    }

    public boolean getisDelete(){return isDelete;}
    public void setisDelete(){ isDelete=false;}
    public int getNumGame(){
        return numGames;
    }
    public Game getGame(int pos){return gameName.get(pos);}


    public List<Game> getGameList(){
        return gameName;
    }

    public List<String> getGamesNameList(){
        List<String> gameNameString = new ArrayList<>();
        for(int i = 0; i<numGames; i++){
            gameNameString.add(gameName.get(i).getName());
        }
        return gameNameString;
    }
}
