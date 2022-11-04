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
    Boolean edited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //refreshDisplay();


        findViewById(R.id.saveConfig).setOnClickListener(v-> saveGame());
        //findViewById(R.id.deleteConfig).setOnClickListener(v->deleteGame());
        findViewById(R.id.startGame).setOnClickListener(v-> switchScreen());
    }

//    private void deleteGame() {
//        Intent intent = getIntent();
//        int gameIndex = intent.getIntExtra("game_index", -1);
//
//        if(gameIndex >= 0){
//            gameConfig.deleteGame(gameIndex);
//            System.out.println("Number of games left " + gameConfig.getNumGame());
//        }
//        backToMain();
//
//    }


    private void saveGame() {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        if(!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
            if(!edited){
                Game game = new Game(name.getText().toString(), description.getText().toString());
                gameConfig.addGame(game);
                edited = false;
            } else{
                game.setName(name.getText().toString());
                game.setDescription(description.getText().toString());
            }
            backToMain();

        }
        else{
            Toast.makeText(this, "One or more required items missing", Toast.LENGTH_SHORT).show();
        }

    }

//    private void refreshDisplay() {
//        Intent intent = getIntent();
//        int gameIndex = intent.getIntExtra("game_index", -1);
//        if(gameIndex >= 0){
//            edited = true;
//            game = gameConfig.getGame(gameIndex);
//            EditText name = findViewById(R.id.editTextGameName);
//            EditText description = findViewById(R.id.editTextGameDescription);
//            name.setText(game.getName());
//            description.setText(game.getDescription());
//        }
//    }

    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchScreen() {
        Intent intent = new Intent(this, AddScore.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GameTitle.class);
    }
}