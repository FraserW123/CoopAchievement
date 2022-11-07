package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *This main activity class shows the list of games and also persists the game list
 * it also persists the previous and history of games in the listview
 */
public class MainActivity extends AppCompatActivity {
    GameConfig gameConfig = GameConfig.getInstance();
    boolean startup = true;
    ListView lvManager;
    ImageView nogames;
    ImageView nolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            System.out.println("game played so far "+gameConfig.getNumGame());
            intent.putExtra("new_game", gameConfig.getNumGame());
            startActivity(intent);
        });
        nogames = findViewById(R.id.nogames);
        nolist = findViewById(R.id.nolist);
        populateListView();
        listClick();
        storeGameList();

    }

    private void listClick() {
        ListView lvManager1 = findViewById(R.id.ListofGames);
        lvManager1.setOnItemClickListener((parent, view, position, id) -> {
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
        lvManager = findViewById(R.id.ListofGames);
        lvManager.setAdapter(adapter);
        lvManager.setEmptyView(nolist);
        lvManager.setEmptyView(nogames);
    }


    private List<String> getGameList(){
        GameConfig gameConfig = GameConfig.getInstance();
        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        String extractedText = prefs.getString("StartGameList","");
        System.out.println(extractedText);

        String[] gameInfo = extractedText.split(",");

        if(!extractedText.equals("") && !gameConfig.getisDelete()){
            for(int i = 0; i<gameInfo.length; i+=2){
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

        List<Game> gameList = gameConfig.getGameList();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<gameList.size(); i++){
            stringBuilder.append(gameList.get(i).getName());
            stringBuilder.append(",");
            stringBuilder.append(gameList.get(i).getDescription());
            stringBuilder.append(",");
        }

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