package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.coopachievement.model.GameConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            startActivity(intent);
        });

        populateListView();

    }

    private void populateListView() {
        String [] myItems = {"blue", "green", "yellow", "red"};
        GameConfig gameConfig = GameConfig.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.list_game_config, gameConfig.getGamesList());

        ListView lvManager = findViewById(R.id.ListofGames);
        lvManager.setAdapter(adapter);




    }

}