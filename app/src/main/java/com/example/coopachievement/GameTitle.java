package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.coopachievement.model.GameConfig;

public class GameTitle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);

        findViewById(R.id.saveConfig).setOnClickListener(v->{
            GameConfig gameConfig = GameConfig.getInstance();
            EditText name = findViewById(R.id.editTextGameName);
            gameConfig.addGame(name.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.startGame).setOnClickListener(v->{
            Intent intent = new Intent(this, ScoreCalculator.class);
            startActivity(intent);
        });
    }
}