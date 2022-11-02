package com.example.coopachievement.model;

import java.util.ArrayList;
import java.util.List;


public class GameConfig {
    private List<Game> gameName = new ArrayList<>();
    private int numGames = 0;

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
        System.out.println("added "+game.getName());
    }

    public void deleteGame(int pos){
        gameName.remove(pos);
        numGames--;
    }

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
