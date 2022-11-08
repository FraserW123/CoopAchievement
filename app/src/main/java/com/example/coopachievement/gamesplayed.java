package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.List;

/**
 * This class describes the list of games played in a specific format under a specific game name
 * it also persists the history and previous games played and also gives users a click to edit the game
 * they want to change
 * also shows the empty state on gamesplayed list view when no game is there
 */
public class gamesplayed extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;
    Boolean edited = false;
    List<String> list;
    ListView lvManager;
    ImageView nogameplayed;
    TextView nogametext;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_gamesplayed);
        refreshDisplay();
        populateList();
        listClick();
        findViewById(R.id.playGame).setOnClickListener(v-> createNewMatch());
        nogameplayed = findViewById(R.id.nogamesplayed);
        nogametext = findViewById(R.id.textView4);
        lvManager.setEmptyView(nogameplayed);
        lvManager.setEmptyView(nogametext);
    }


    private void refreshDisplay()
    {
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && gameConfig.isAccessedMatches())
        {
            gameIndex = gameConfig.getCurrentGameIndex();
        }
        if(gameIndex >= 0)
        {
            edited = true;
            game = gameConfig.getGame(gameIndex);

            EditText name = findViewById(R.id.editTextGameName2);
            EditText description = findViewById(R.id.editTextGameDescription2);
            EditText poor_score = findViewById(R.id.etn_poorScore);
            EditText great_score = findViewById(R.id.etn_greatScore);

            name.setText(game.getName());
            description.setText(game.getDescription());
            poor_score.setText(String.valueOf(game.getPoorScore()));
            great_score.setText(String.valueOf(game.getGreatScore()));
        }
    }

    private void listClick()
    {
        if(differenceOf10()) {
            ListView matchManager = findViewById(R.id.lvMatchView);
            matchManager.setOnItemClickListener(((parent, view, position, id) -> {
                Intent intent = new Intent(this, AddScore.class);
                intent.putExtra("match_index", position);
                game.setCurrentMatch(position);
                startActivity(intent);
            }));
        }
        else{
            Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateList()
    {
        System.out.println("\n\nRunning this function right now\n\n");
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && gameConfig.isAccessedMatches()){
            gameIndex = gameConfig.getCurrentGameIndex();
        }
        //gameIndex = gameConfig.getCurrentGameIndex();

        System.out.println("went here");
        game = gameConfig.getGame(gameIndex);
        List<String> list = game.getMatchesNamesList();

        for(int i = 0; i<list.size(); i++){
            System.out.println("list item "+ (i+1) + " " + list.get(i));
        }
        int matches = game.getNumMatchesPlayed();
        if(game.getNumMatchesPlayed() > 0)
        {
            TextView matchesPlayed = findViewById(R.id.tvGamesPlayed);
            matchesPlayed.setText("Games Played: " + matches);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_matches,list);
        lvManager = findViewById(R.id.lvMatchView);
        lvManager.setAdapter(adapter);
    }

    private void createNewMatch()
    {
        if(differenceOf10()) {
            gameConfig.setAccessedMatches(true);
            game.setCurrentMatch(game.getNumMatchesPlayed());

            Intent intent = new Intent(this, AddScore.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getGameIndex()
    {
        Intent intent = getIntent();
        return intent.getIntExtra("game_index", -1);
    }

    private void deleteGame()
    {
        int gameIndex = gameConfig.getCurrentGameIndex();
        if(gameIndex >= 0){
            for(int i = 0; i<game.getMatchList().size(); i++){
                game.removeMatch(i);
            }
            //storeMatchList();
            gameConfig.deleteGame(gameIndex);
        }
        backToMain();
    }

    private void backToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_delete:
                //Reference from StackOverflow
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_dialog_message)
                        .setTitle(R.string.confirm_dialog_title)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // CONFIRM
                                deleteGame();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // CANCEL
                                //finish();
                            }
                        });
// Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        if(differenceOf10()) {
            EditText name = findViewById(R.id.editTextGameName2);
            EditText desc = findViewById(R.id.editTextGameDescription2);
            EditText poor_score = findViewById(R.id.etn_poorScore);
            EditText great_score = findViewById(R.id.etn_greatScore);

            game.setName(name.getText().toString());
            game.setDescription(desc.getText().toString());

            game.setPoorScore(Integer.parseInt(poor_score.getText().toString()));
            game.setGreatScore(Integer.parseInt(great_score.getText().toString()));
            Intent intent = new Intent(gamesplayed.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean differenceOf10(){
        EditText poorScore = findViewById(R.id.etn_poorScore);
        EditText greatScore = findViewById(R.id.etn_greatScore);

        String st_poor_score = poorScore.getText().toString();
        String st_great_score = greatScore.getText().toString();

        if(!st_poor_score.equals("") && !st_great_score.equals("")) {
            int num_poor_score = Integer.parseInt(st_poor_score);
            int num_great_score = Integer.parseInt(st_great_score);
            return (num_great_score - num_poor_score) >= 9;
        }

        return false;
    }

}