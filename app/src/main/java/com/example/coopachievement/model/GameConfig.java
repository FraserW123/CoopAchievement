package com.example.coopachievement.model;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private List<String> gameList = new ArrayList<>();

    private static GameConfig instance;
    private GameConfig(){

    }

    public static GameConfig getInstance(){
        if(instance == null){
            instance = new GameConfig();
        }
        return instance;
    }

    public void addGame(String game){
        gameList.add(game);
        System.out.println("added "+game);
    }

    public List<String> getGamesList(){
        return gameList;
    }
}
