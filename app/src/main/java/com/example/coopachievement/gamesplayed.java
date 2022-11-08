package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.List;

public class gamesplayed extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;
    Boolean edited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_gamesplayed);
        refreshDisplay();
        populateList();
        listClick();
        storeMatchList();
        findViewById(R.id.playGame).setOnClickListener(v-> createNewMatch());
    }

    private void storeMatchList() {

        List<ScoreCalculator> matchList = game.getMatchList();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<matchList.size(); i++){
            stringBuilder.append(matchList.get(i).getNumPlayers());
            stringBuilder.append(",");
            stringBuilder.append(matchList.get(i).getScore());
            stringBuilder.append(",");
            stringBuilder.append(matchList.get(i).getDate());
            stringBuilder.append(",");
            stringBuilder.append(gameConfig.getCurrentGameIndex());
            stringBuilder.append(",");
        }
        SharedPreferences prefs = getSharedPreferences("matches_list", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MatchesList",stringBuilder.toString());
        editor.apply();


    }

    private void loadMatch() {
        System.out.println("Loading previous data");
        int gameIndex = getGameIndex();
        game = gameConfig.getGame(gameIndex);
        SharedPreferences prefs = getSharedPreferences("matches_list", MODE_PRIVATE);
        String extractedText = prefs.getString("MatchesList","");
        System.out.println("should be stuff here "+extractedText);
        String[] matchInfo = extractedText.split(",");

        if(!extractedText.equals("") && matchInfo.length >= 4){
            System.out.println("look at this thing " + matchInfo[3]);
            if(Integer.parseInt(matchInfo[3]) == gameConfig.getCurrentGameIndex()){
                for(int i = 0; i<matchInfo.length; i+=4){
                    ScoreCalculator scoreCalculator = new ScoreCalculator();
                    scoreCalculator.setNumPlayers(Integer.parseInt(matchInfo[i]));
                    scoreCalculator.setScore(Integer.parseInt(matchInfo[i+1]));
                    scoreCalculator.setDate(matchInfo[i+2]);
                    scoreCalculator.setMatchName();
                    game.addMatch(scoreCalculator);
                }
            }
        }
    }

    private void refreshDisplay() {
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && !gameConfig.isAccessedMatches()){
            gameIndex = gameConfig.getCurrentGameIndex();
        }
        if(gameIndex >= 0){
            edited = true;
            game = gameConfig.getGame(gameIndex);

            EditText name = findViewById(R.id.editTextGameName2);
            EditText description = findViewById(R.id.editTextGameDescription2);
            name.setText(game.getName());
            description.setText(game.getDescription());
        }
    }

    private void listClick(){
        ListView matchManager = findViewById(R.id.lvMatchView);
        matchManager.setOnItemClickListener(((parent, view, position, id) -> {
            Intent intent = new Intent(this, AddScore.class);
            intent.putExtra("match_index", position);
            game.setCurrentMatch(position);
            startActivity(intent);
        }));
    }

    private void populateList() {
        System.out.println("\n\nRunning this function right now\n\n");
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && !gameConfig.isAccessedMatches()){
            gameIndex = gameConfig.getCurrentGameIndex();
        }
        game = gameConfig.getGame(gameIndex);
        List<String> list = game.getMatchesNamesList();

        if(list.isEmpty()){
            System.out.println("going through here");
            loadMatch();
            list = game.getMatchesNamesList();
        }
        for(int i = 0; i<list.size(); i++){
            System.out.println("list item "+ (i+1) + " " + list.get(i));
        }
        int matches = game.getNumMatchesPlayed();
        if(game.getNumMatchesPlayed() > 0){
            TextView matchesPlayed = findViewById(R.id.tvGamesPlayed);
            matchesPlayed.setText("Games Played: " + matches);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_matches,list);
        ListView lvManager = findViewById(R.id.lvMatchView);
        lvManager.setAdapter(adapter);
    }

    private void createNewMatch() {
        gameConfig.setAccessedMatches(true);
        game.setCurrentMatch(game.getNumMatchesPlayed());

        Intent intent = new Intent(this, AddScore.class);
        startActivity(intent);
    }

    private int getGameIndex() {
        Intent intent = getIntent();
        return intent.getIntExtra("game_index", -1);
    }

    private void deleteGame() {
        int gameIndex = gameConfig.getCurrentGameIndex();
        if(gameIndex >= 0){
            for(int i = 0; i<game.getMatchList().size(); i++){
                game.removeMatch(i);
            }
            storeMatchList();
            gameConfig.deleteGame(gameIndex);
        }
        backToMain();

    }
    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                //Reference from StackOverflow
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_dialog_message)
                        .setTitle(R.string.confirm_dialog_title)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CONFIRM
                                deleteGame();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL
                                //finish();
                            }
                        });
// Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                EditText name = findViewById(R.id.editTextGameName2);
                EditText desc = findViewById(R.id.editTextGameDescription2);
                game.setName(name.getText().toString());
                game.setDescription(desc.getText().toString());
                return super.onOptionsItemSelected(item);
        }
    }



}