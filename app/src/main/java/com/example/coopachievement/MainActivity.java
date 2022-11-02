package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

import java.util.ArrayList;
import java.util.List;

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
        listClick();
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
        if(gameConfig.getGamesNameList().isEmpty() && !getGameList().isEmpty()){

            list = getGameList();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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
        String[] gameName = new String[gameInfo.length/2];
        String[] gameDescription = new String[gameInfo.length/2];
        //Figure this out

//        if(extractedText != ""){
//            for(int i = 0; i<gameName.length;i++){
//                gameName[i] = gameInfo[(i+2)];
//                gameDescription[i] = gameInfo[i];
//
//            }
//            for(int i = 0; i<gameInfo.length; i++){
//                Game game = new Game(gameName[i], gameDescription[i]);
//                gameConfig.addGame(game);
//            }
//        }


        List<String> items = new ArrayList<>();
        for(int i = 0; i<gameName.length; i++){
            if(!gameInfo[i].equals(""))
                items.add(gameInfo[i]);
        }

        return items;
    }

    private void storeGameList(){
        GameConfig gameConfig = GameConfig.getInstance();
        List<Game> gameList = gameConfig.getGameList();
        StringBuilder stringBuilder = new StringBuilder();
//        for(String s: gameList){
//            stringBuilder.append(s);
//            stringBuilder.append(",");
//        }
        for(int i = 0; i<gameList.size(); i++){
            stringBuilder.append(gameList.get(i).getName());
            stringBuilder.append(",");
            stringBuilder.append(gameList.get(i).getDescription());
            stringBuilder.append(",");
        }
        System.out.println("the result is "+ stringBuilder.toString());
        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StartGameList", stringBuilder.toString());
        editor.commit();

    }

    public void SwitchActivity(int position) {
        Intent intent = new Intent(this, GameTitle.class);
        intent.putExtra("game_index", position);
        startActivity(intent);
    }

}