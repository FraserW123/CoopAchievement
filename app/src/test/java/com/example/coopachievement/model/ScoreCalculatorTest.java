package com.example.coopachievement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
    void getDifficulty() {
    }

    @org.junit.jupiter.api.Test
    void setDifficulty() {
    }

    @org.junit.jupiter.api.Test
    void setPoorScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 10;
        scoreCalculator.setPoorScore(num);
        assertEquals(num,scoreCalculator.getPoorScore());
    }

//    @org.junit.jupiter.api.Test
//    void getPoorScore() {
//    }

    @org.junit.jupiter.api.Test
    void setGreatScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 20;
        scoreCalculator.setGreatScore(num);
        assertEquals(num,scoreCalculator.getGreatScore());
    }

//    @org.junit.jupiter.api.Test
//    void getGreatScore() {
//    }

    @org.junit.jupiter.api.Test
    void setMatchName() {

    }

    @org.junit.jupiter.api.Test
    void getMatchName() {
    }

    @org.junit.jupiter.api.Test
    void getMatchesPlayed() {

    }

    @org.junit.jupiter.api.Test
    void setDate() {
    }

    @org.junit.jupiter.api.Test
    void getDate() {
    }

    @org.junit.jupiter.api.Test
    void setNumPlayers() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(2,50,3,60);
        int num = 20;
        scoreCalculator.setNumPlayers(num);
        assertEquals(num,scoreCalculator.getNumPlayers());
    }

//    @org.junit.jupiter.api.Test
//    void getNumPlayers() {
//    }

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
    }

    @org.junit.jupiter.api.Test
    void setAchievementLevel() {
    }

    @org.junit.jupiter.api.Test
    void fillLevelsList() {
    }

    @Test
    void getPlayerScoresList() {
    }

    @Test
    void setPlayersScore() {
    }

    @Test
    void testEditMatch() {
    }

    @Test
    void testGetDifficulty() {
    }

    @Test
    void testSetDifficulty() {
    }

    @Test
    void testSetPoorScore() {
    }

    @Test
    void testGetPoorScore() {
    }

    @Test
    void testSetGreatScore() {
    }

    @Test
    void testGetGreatScore() {
    }

    @Test
    void testSetMatchName() {
    }

    @Test
    void testGetMatchName() {
    }

    @Test
    void testGetMatchesPlayed() {
    }

    @Test
    void testSetDate() {
    }

    @Test
    void testGetDate() {
    }

    @Test
    void testSetNumPlayers() {
    }

    @Test
    void testGetNumPlayers() {
    }

    @Test
    void testSetScore() {
    }

    @Test
    void testGetScore() {
    }

    @Test
    void testDifficultyMultiplier() {
    }

    @Test
    void testSetAchievementLevel() {
    }

    @Test
    void testFillLevelsList() {
    }
}