package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

/**
 *This class describes the game title i.e. name and description and also let users to save it
 * poor and good score should have difference of 10
 */
public class GameTitle extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createDifficultyButtons();
        findViewById(R.id.startGame).setOnClickListener(v-> createNewMatch());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_score,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveGame()
    {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);

        if (!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && differenceOf10()) {
            EditText poorScore = findViewById(R.id.etn_poor_score);
            EditText greatScore = findViewById(R.id.etn_great_score);

            int num_poor_score = Integer.parseInt(poorScore.getText().toString());
            int num_great_score = Integer.parseInt(greatScore.getText().toString());

            System.out.println("poor: " + num_poor_score);

            Game game = new Game(name.getText().toString(), description.getText().toString(), num_poor_score, num_great_score);
            num_poor_score = game.getPoorScore();
            System.out.println("poor: " + num_poor_score);
            gameConfig.addGame(game);
            backToMain();

        } else {
            Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
        }

    }

    private void createDifficultyButtons() {
        RadioGroup group = findViewById(R.id.rgNewDifficulty);
        String[] difficultyOptions = getResources().getStringArray(R.array.Difficulty_Options);

        //creating the buttons
        for(int i = 0; i<difficultyOptions.length; i++){
            String difficulty = difficultyOptions[i];
            RadioButton button = new RadioButton(this);
            button.setText(difficulty);

            button.setOnClickListener(v->{
                game.setDifficulty(difficulty);
            });

            group.addView(button);

        }
    }

    private boolean differenceOf10(){
        EditText poorScore = findViewById(R.id.etn_poor_score);
        EditText greatScore = findViewById(R.id.etn_great_score);

        String st_poor_score = poorScore.getText().toString();
        String st_great_score = greatScore.getText().toString();

        if(!st_poor_score.equals("") && !st_great_score.equals("")) {
            int num_poor_score = Integer.parseInt(st_poor_score);
            int num_great_score = Integer.parseInt(st_great_score);
            return (num_great_score - num_poor_score) >= 9;
        }

        return false;
    }

    private void backToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createNewMatch()
    {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        if(!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && differenceOf10()){
            EditText poorScore = findViewById(R.id.etn_poor_score);
            EditText greatScore = findViewById(R.id.etn_great_score);

            int num_poor_score = Integer.parseInt(poorScore.getText().toString());
            int num_great_score = Integer.parseInt(greatScore.getText().toString());

            Intent intent = getIntent();
            int gameIndex = intent.getIntExtra("new_game", -1);
            System.out.println("this happened " + gameIndex);
            if(gameIndex >= 0)
            {
                gameConfig.setCurrentGameIndex(gameIndex);
            }

            Game game = new Game(name.getText().toString(), description.getText().toString(), num_poor_score, num_great_score);
            gameConfig.addGame(game);
            gameConfig.setAccessedMatches(true);
            game.setCurrentMatch(game.getNumMatchesPlayed());
            Intent switching = new Intent(this, AddScore.class);
            startActivity(switching);
        }
        else{
            Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.action_save)
        {
            saveGame();
        }
        //finish();
        return super.onOptionsItemSelected(item);
    }
}