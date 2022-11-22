package com.example.coopachievement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 *This main activity class shows the list of games and also persists the game list
 * it also persists the previous and history of games in the listview
 */
public class MainActivity extends AppCompatActivity {
    GameConfig gameConfig = GameConfig.getInstance();
    ListView lvManager;
    ImageView nogames;
    ImageView nolist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            startActivity(intent);
        });
        nogames = findViewById(R.id.nogames);
        nolist = findViewById(R.id.nolist);
        populateListView();
        listClick();
        storeGameList();

    }

    private void listClick()
    {
        ListView lvManager1 = findViewById(R.id.ListofGames);
        lvManager1.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = (TextView) view;
            String message = "You clicked # " + position + ", which is game: " + textView.getText().toString();
            gameConfig.setAccessedMatches(false);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            SwitchActivity(position);
        });
    }

    private void populateListView()
    {
        System.out.println("doing this");
        GameConfig gameConfig = GameConfig.getInstance();
        List<String> list = gameConfig.getGamesNameList();
        if((gameConfig.getGamesNameList().isEmpty() && !gameConfig.getisDelete()))
        {
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


    private List<String> getGameList()
    {
        GameConfig gameConfig = GameConfig.getInstance();
        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        String extractedText = prefs.getString("StartGameList","");
        System.out.println(extractedText);

        String[] gameInfo = extractedText.split(",");

        for(int i = 0; i<gameInfo.length; i++){ //debugging
            System.out.println("this one " + gameInfo[i]);
        }

        int gameFields = 5;
        int matchFields = 5;
        if(!extractedText.equals("") && !gameConfig.getisDelete()){
            for(int i = 0; i<gameInfo.length; i+=gameFields){
                Game game = new Game(gameInfo[i], gameInfo[i+1], Integer.parseInt(gameInfo[i+2]), Integer.parseInt(gameInfo[i+3]));
                if(gameInfo.length - i >= gameFields){
                    String[] matches = gameInfo[i+4].split(";");
                    if(!matches[0].equals("|")){
                        for(int j = 0; j<matches.length; j+=matchFields){
                            ScoreCalculator scoreCalculator = new ScoreCalculator(Integer.parseInt(matches[j])
                                    ,Integer.parseInt(matches[j+1]),Integer.parseInt(gameInfo[i+2]),Integer.parseInt(gameInfo[i+3]));
                            scoreCalculator.setDate(matches[j+2]);
                            game.setDifficulty(matches[j+3]);
                            scoreCalculator.setDifficulty(matches[j+3]);
                            String[] playerScores = matches[j+4].split("#");
                            ArrayList<Integer> scores = new ArrayList<>();
                            for(int k = 0; k<playerScores.length; k++){
                                scores.add(Integer.parseInt(playerScores[k]));
                            }
                            scoreCalculator.setPlayersScore(scores);
                            scoreCalculator.setMatchName();
                            game.addMatch(scoreCalculator);
                        }
                    }

                }

                gameConfig.addGame(game);
            }
        }
        List<String> items = new ArrayList<>();
        for(int i = 0; i<gameInfo.length; i+=gameFields){
            if(!gameInfo[i].equals("") && !gameConfig.getisDelete())
            {
                items.add(gameInfo[i]);
            }
        }
        return items;
    }

    public void storeGameList()
    {
        List<Game> gameList = gameConfig.getGameList();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<gameList.size(); i++){
            Game game = gameList.get(i);
            stringBuilder.append(game.getName());
            stringBuilder.append(",");
            stringBuilder.append(game.getDescription());
            stringBuilder.append(",");
            stringBuilder.append(game.getPoorScore());
            stringBuilder.append(",");
            stringBuilder.append(game.getGreatScore());
            stringBuilder.append(",");

            List<ScoreCalculator> matchList = game.getMatchList();
            storeMatchData(stringBuilder, matchList);

        }

        SharedPreferences prefs = getSharedPreferences("games_list", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StartGameList", stringBuilder.toString());
        editor.apply();
    }

    private static void storeMatchData(StringBuilder stringBuilder, List<ScoreCalculator> matchList) {
        StringBuilder matchString = new StringBuilder();
        if(!matchList.isEmpty()){

            for(int j = 0; j<matchList.size(); j++){
                ScoreCalculator matches = matchList.get(j);

                matchString.append(matches.getNumPlayers());
                matchString.append(";");
                matchString.append(matches.getScore());
                matchString.append(";");
                matchString.append(matches.getDate());
                matchString.append(";");
                matchString.append(matches.getDifficulty());
                matchString.append(";");
                ArrayList<Integer> playerScore = matches.getPlayerScoresList();
                StringBuilder scoreString = new StringBuilder();
                if(!playerScore.isEmpty()){
                    for(int k = 0; k<playerScore.size(); k++){
                        scoreString.append(playerScore.get(k));
                        scoreString.append("#");
                    }
                }
                matchString.append(scoreString);
                matchString.append(";");

            }

        }else{
            matchString.append("|");
        }
        stringBuilder.append(matchString);
        stringBuilder.append(",");

    }


    public void SwitchActivity(int position)
    {
        Intent intent = new Intent(this, GamesPlayed.class);
        gameConfig.setCurrentGameIndex(position);
        storeGameList();
        intent.putExtra("game_index", position);
        startActivity(intent);
    }

}