package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean startup = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            startActivity(intent);
        });


        listClick();
        populateListView();
        storeGameList();

    }

    private void listClick() {
        ListView lvManager = findViewById(R.id.ListofGames);
        lvManager.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, "entering game", Toast.LENGTH_SHORT).show();
            SwitchActivity(position);
        });
    }

    private void populateListView() {
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

        String[] gameInfo = extractedText.split(",");
        //System.out.println("length " +gameInfo.length);

        if(!extractedText.equals("") && !gameConfig.getisDelete()){
            for(int i = 0; i<gameInfo.length; i+=2){
                //System.out.println("\n\nAdding game with name " + gameInfo[i]+ " and description " +gameInfo[i+1]);
                Game game = new Game(gameInfo[i], gameInfo[i+1]);
                gameConfig.addGame(game);
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
        GameConfig gameConfig = GameConfig.getInstance();
        List<Game> gameList = gameConfig.getGameList();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<gameList.size(); i++){
            stringBuilder.append(gameList.get(i).getName());
            stringBuilder.append(",");
            stringBuilder.append(gameList.get(i).getDescription());
            stringBuilder.append(",");
        }

        //System.out.println("the result is "+ stringBuilder);
        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StartGameList", stringBuilder.toString());
        editor.apply();

    }

    public void SwitchActivity(int position) {
        Intent intent = new Intent(this, GameTitle.class);
        intent.putExtra("game_index", position);
        startActivity(intent);
    }

}