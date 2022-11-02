package com.example.coopachievement.model;

public class Game {

    String name;
    String description;
    int numMatchesPlayed;

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void addMatchesPlayed(){
        numMatchesPlayed++;
    }
}
