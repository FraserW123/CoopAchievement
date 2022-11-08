package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

public class GameTitle extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.startGame).setOnClickListener(v-> createNewMatch());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_score,menu);
        return super.onCreateOptionsMenu(menu);
    }



    private void saveGame() {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        if(!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){

            Game game = new Game(name.getText().toString(), description.getText().toString());
            gameConfig.addGame(game);
            backToMain();

        }
        else{
            Toast.makeText(this, "One or more required items missing", Toast.LENGTH_SHORT).show();
        }

    }


    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createNewMatch() {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        if(!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
            Intent intent = getIntent();
            int gameIndex = intent.getIntExtra("new_game", -1);
            System.out.println("this happened " + gameIndex);
            if(gameIndex >= 0){
                gameConfig.setCurrentGameIndex(gameIndex);
            }
            Game game = new Game(name.getText().toString(), description.getText().toString());
            gameConfig.addGame(game);
            gameConfig.setAccessedMatches(true);
            game.setCurrentMatch(game.getNumMatchesPlayed());
            Intent switching = new Intent(this, AddScore.class);
            startActivity(switching);
        }
        else{
            Toast.makeText(this, "One or more required items missing", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveGame();
        }
        finish();
        return super.onOptionsItemSelected(item);
    }


}