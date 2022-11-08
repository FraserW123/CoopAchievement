package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GameConfig gameConfig = GameConfig.getInstance();
    boolean startup = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            startActivity(intent);
        });

        populateListView();
        listClick();
        storeGameList();

    }

    private void listClick() {
        ListView lvManager = findViewById(R.id.ListofGames);
        lvManager.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = (TextView) view;
            String message = "You clicked # " + position + ", which is game: " + textView.getText().toString();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            SwitchActivity(position);
        });
    }

    private void populateListView() {
        System.out.println("doing this");
        GameConfig gameConfig = GameConfig.getInstance();
        List<String> list = gameConfig.getGamesNameList();
        if((gameConfig.getGamesNameList().isEmpty() && !gameConfig.getisDelete())){
            list = getGameList();
            gameConfig.setisDelete();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_game_config, list);
        ListView lvManager = findViewById(R.id.ListofGames);
        lvManager.setAdapter(adapter);
    }


    private List<String> getGameList(){
        GameConfig gameConfig = GameConfig.getInstance();
        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        String extractedText = prefs.getString("StartGameList","");
        System.out.println(extractedText);

        String[] gameInfo = new String[0];

        for(int i = 0; extractedText.charAt(i) != ':' ; i++){
            if(extractedText.charAt(i) != ','){
                gameInfo[i] += extractedText.charAt(i);
            }
            if(extractedText.charAt(i) == ';'){

            }
        }








        for(int i = 0; i< gameInfo.length; i++){
            System.out.println("game info " + gameInfo[i]);
        }

        if(!extractedText.equals("") && !gameConfig.getisDelete()){
            for(int i = 0; i<gameInfo.length; i++){
                for(int j = 0; j<gameInfo[i].length(); i++){
                    Game game = new Game(gameInfo[j], gameInfo[i+1]);

                    ScoreCalculator scoreCalculator = new ScoreCalculator();


                    gameConfig.addGame(game);
                }



            }
        }
        List<String> items = new ArrayList<>();
        for(int i = 0; i<gameInfo.length; i+=2){
            if(!gameInfo[i].equals("") && !gameConfig.getisDelete())
                items.add(gameInfo[i]);
        }
        return items;
    }

    private void storeGameList(){

        List<Game> gameList = gameConfig.getGameList();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<gameList.size(); i++){
            Game game = gameList.get(i);

            stringBuilder.append(game.getName());
            stringBuilder.append(",");
            stringBuilder.append(game.getDescription());
            stringBuilder.append(",");


            List<ScoreCalculator> matches = game.getMatchList();
            for(int j = 0; j<matches.size(); j++){
                stringBuilder.append(matches.get(j).getNumPlayers());
                stringBuilder.append(",");
                stringBuilder.append(matches.get(j).getScore());
                stringBuilder.append(",");
                stringBuilder.append(matches.get(j).getDate());
                stringBuilder.append(",");
            }
            stringBuilder.append(";");

        }
        stringBuilder.append(":");

        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StartGameList", stringBuilder.toString());
        editor.apply();

    }

    public void SwitchActivity(int position) {
        Intent intent = new Intent(this, gamesplayed.class);
        gameConfig.setCurrentGameIndex(position);
        intent.putExtra("game_index", position);
        startActivity(intent);
    }


}