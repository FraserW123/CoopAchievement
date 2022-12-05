package com.example.coopachievement.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.coopachievement.R;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ScoreCalculatorTest {

    @org.junit.jupiter.api.Test
    void editMatch() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        scoreCalculator.editMatch(4,90,14,90);

        int expectedPlayers = 4;
        int expectedScore = 90;
        int expectedPoorScore = 14;
        int expectedGreatScore = 90;

        int resultPlayers = scoreCalculator.getNumPlayers();
        int resultScore = scoreCalculator.getScore();
        int resultPoorScore = scoreCalculator.getPoorScore();
        int resultGreatScore = scoreCalculator.getGreatScore();
        assertEquals(expectedPlayers, resultPlayers);
        assertEquals(expectedScore, resultScore);
        assertEquals(expectedPoorScore, resultPoorScore);
        assertEquals(expectedGreatScore, resultGreatScore);

    }



    @org.junit.jupiter.api.Test
    void setDifficulty() {
        String expectedDifficulty = "Normal";
        String empty = "";
        ScoreCalculator scoreCalculator= new ScoreCalculator(2,50,3,60);
        scoreCalculator.setDifficulty(empty);
        assertEquals(expectedDifficulty, scoreCalculator.getDifficulty());
    }

    @org.junit.jupiter.api.Test
    void setPoorScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 10;
        scoreCalculator.setPoorScore(num);
        assertEquals(num,scoreCalculator.getPoorScore());
    }



    @org.junit.jupiter.api.Test
    void setGreatScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 20;
        scoreCalculator.setGreatScore(num);
        assertEquals(num,scoreCalculator.getGreatScore());
    }


    @org.junit.jupiter.api.Test
    void setDate() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm a");
        String date = time.format(format);


        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        scoreCalculator.setDate(date);
        String actualDate = scoreCalculator.getDate();
        assertEquals(date, actualDate);

    }


    @org.junit.jupiter.api.Test
    void setNumPlayers() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 20;
        scoreCalculator.setNumPlayers(num);
        assertEquals(num,scoreCalculator.getNumPlayers());
    }



    @org.junit.jupiter.api.Test
    void setScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 20;
        scoreCalculator.setScore(num);
        assertEquals(num,scoreCalculator.getScore());
    }

//    @org.junit.jupiter.api.Test
//    void getScore() {
//    }

    @org.junit.jupiter.api.Test
    void difficultyMultiplier() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        scoreCalculator.setDifficulty("Hard");
        double actual = scoreCalculator.difficultyMultiplier();
        double expectedMultiplier = 1.25;
        assertEquals(expectedMultiplier, actual);

        scoreCalculator.setDifficulty("");
        actual = scoreCalculator.difficultyMultiplier();
        expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, actual );
    }

    @org.junit.jupiter.api.Test
    void setAchievementLevel() {
        GameConfig gameConfig = GameConfig.getInstance();
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        String[] achievementNames = {"Goofy Goblins!", "Timid Trolls!","Zippy Zombies!","Prideful Phoenixes!", "Vicious Vampires!",
                "Glorious Griffins!","Fantastic Fairies!","Supreme Serpents!","Dancing Dragons!","Ultimate Unicorns!"};
        gameConfig.setTheme(achievementNames);
        //String actualNames = scoreCalculator.setAchievementLevel();
        String expectedName = "Vicious Vampires!";

        //assertEquals(expectedName, actualNames);

    }

    @org.junit.jupiter.api.Test
    void fillLevelsList() {
    }


}