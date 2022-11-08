package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.List;

/**
 * This class give opportunity to players to add a new game score
 * as well as edit the previous game score
 */
public class AddScore extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game = gameConfig.getCurrentGame();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
        refreshDisplay();
        ActionBar toolbar = getSupportActionBar();
        if(getMatchIndex() == -1)
        {
            toolbar.setTitle("Adding a new match!");
        }else
        {
            toolbar.setTitle("Editing match");
        }
        toolbar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_display_levels).setOnClickListener(v->displayLevels());
    }

    private void displayLevels() {
        EditText display_level_players = findViewById(R.id.etn_enter_num_players);
        String dl_players = display_level_players.getText().toString();

        if(!dl_players.equals("")) {
            int players = Integer.parseInt(dl_players);
            if (players > 0) {
                ScoreCalculator scoreCalc = new ScoreCalculator();
                scoreCalc.setPoorScore(game.getPoorScore());
                scoreCalc.setGreatScore(game.getGreatScore());

                scoreCalc.setNumPlayers(players);

                List<String> list = scoreCalc.fillLevelsList();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this, R.layout.list_matches, list);
                ListView lvLevelManager = findViewById(R.id.lv_display_levels);
                lvLevelManager.setAdapter(adapter);
            }
            else{
                Toast.makeText(AddScore.this, "Please enter a valid number of players!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(AddScore.this, "Please enter a valid number of players!", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshDisplay()
    {
        int matchIndex = getMatchIndex();
        if(matchIndex >= 0 )
        {
            EditText players = findViewById(R.id.etn_num_players);
            EditText score  = findViewById(R.id.etn_score);
            players.setText(String.valueOf(game.getMatch(matchIndex).getNumPlayers()));
            score.setText(String.valueOf(game.getMatch(matchIndex).getScore()));
        }
    }

    private int getMatchIndex()
    {
        Intent intent = getIntent();
        return intent.getIntExtra("match_index", -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        refreshDisplay();
        EditText et_players = findViewById(R.id.etn_num_players);
        String st_players = et_players.getText().toString();
        EditText et_score = findViewById(R.id.etn_score);
        String st_score = et_score.getText().toString();

        if(!st_players.equals("") && !st_score.equals(""))
        {
            getMenuInflater().inflate(R.menu.menu_edit_score, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_add_score, menu);
        }
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_save:
                alertMessage();
                return true;

            case R.id.action_delete:
                deleteMessageConfirm();
                return true;

            case android.R.id.home:
                GameConfig gameConfig = GameConfig.getInstance();
                Intent intent = new Intent(this, gamesplayed.class);
                intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                startActivity(intent);

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void alertMessage()
    {
        EditText et_players = findViewById(R.id.etn_num_players);
        String st_players = et_players.getText().toString();
        EditText et_score = findViewById(R.id.etn_score);
        String st_score = et_score.getText().toString();

        if(!st_players.equals("") && !st_score.equals("") && !st_players.equals("0"))
        {
            int players = Integer.parseInt(st_players);
            int score = Integer.parseInt(st_score);
            int matchIndex = getMatchIndex();
            ScoreCalculator score_calc;

            if(matchIndex == -1)
            {
                score_calc = new ScoreCalculator();
                score_calc.setPoorScore(game.getPoorScore());
                score_calc.setGreatScore(game.getGreatScore());
                score_calc.setNumPlayers(players);
                score_calc.setScore(score);
                score_calc.setAchievementLevel();
                score_calc.setMatchName();
                game.addMatch(score_calc);
            }
            else
            {
                score_calc = game.getMatch(matchIndex);
                score_calc.setPoorScore(game.getPoorScore());
                //System.out.println("poor is "+score_calc.getPoorScore());
                score_calc.setGreatScore(game.getGreatScore());
                //System.out.println("great is "+score_calc.getGreatScore());
                score_calc.setNumPlayers(players);
                score_calc.setScore(score);
                score_calc.setAchievementLevel();
                score_calc.setMatchName();
            }
            //System.out.println("the name is " + score_calc.getAchievementLevel());

            FragmentManager manager = getSupportFragmentManager();
            AlertMessageFragment alert = new AlertMessageFragment();
            alert.show(manager, "AlertMessage");
        }
        else
        {
            Toast.makeText(AddScore.this, "Some values are missing or invalid!", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteMessageConfirm()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddScore.this);
        builder.setMessage("Are you sure you want to DELETE this match?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        System.out.println("removing match at index " +getMatchIndex());
                        game.removeMatch(getMatchIndex());
                        Intent intent = new Intent(AddScore.this, gamesplayed.class);
                        intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", null);
        AlertDialog areYouSure = builder.create();
        areYouSure.show();
    }

}