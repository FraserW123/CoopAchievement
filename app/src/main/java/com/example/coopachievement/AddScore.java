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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.List;
import java.util.Objects;

/**
 * This class give opportunity to players to add a new game score
 * as well as edit the previous game score
 */
public class AddScore extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game = gameConfig.getCurrentGame();
    boolean unsaved = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
        refreshDisplay();
        createDifficultyButtons();
        ActionBar toolbar = getSupportActionBar();
        if(getMatchIndex() == -1)
        {
            toolbar.setTitle("Adding a new match!");
        }else
        {
            toolbar.setTitle("Editing match");
        }
        toolbar.setDisplayHomeAsUpEnabled(true);

        //findViewById(R.id.btn_display_levels).setOnClickListener(v->displayLevels());
    }

    private void createDifficultyButtons() {
        RadioGroup group = findViewById(R.id.rgDifficulty);
        String[] difficultyOptions = getResources().getStringArray(R.array.Difficulty_Options);

        //creating the buttons
        for(int i = 0; i<difficultyOptions.length; i++){
            String difficulty = difficultyOptions[i];
            RadioButton button = new RadioButton(this);
            button.setText(difficulty);

            button.setOnClickListener(v->{
                game.setDifficulty(difficulty);
                displayLevels();
            });

            group.addView(button);
            if(Objects.equals(difficulty, game.getDifficulty())){
                button.setChecked(true);
                displayLevels();
            }else{
                System.out.println("this didnt happen " + game.getDifficulty() + " vs " + difficulty);
            }
        }
    }

    private void displayLevels() {
        //EditText display_level_players = findViewById(R.id.etn_enter_num_players);
        EditText display_level_players = findViewById(R.id.etn_num_players);
        String dl_players = display_level_players.getText().toString();
        int players = 0;
        if(!dl_players.equals("")){
            players = Integer.parseInt(dl_players);
        }

        if (players > 0) {
            ScoreCalculator scoreCalc = new ScoreCalculator();
            scoreCalc.setPoorScore(game.getPoorScore());
            scoreCalc.setGreatScore(game.getGreatScore());
            scoreCalc.setDifficulty(game.getDifficulty());
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

    private void refreshDisplay()
    {
        int matchIndex = getMatchIndex();
        System.out.println("match index " + matchIndex);
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
                if(unsaved){
                    alertMessage();
                    unsaved = false;
                }else{
                    Toast.makeText(this,"match already saved",Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.action_delete:
                deleteMessageConfirm();
                return true;

            case android.R.id.home:
                GameConfig gameConfig = GameConfig.getInstance();
                Intent intent = new Intent(this, GamesPlayed.class);
                intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                startActivity(intent);
                return true;

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

            if(matchIndex == -1 && unsaved)
            {
                score_calc = new ScoreCalculator(players,score,game.getPoorScore(),game.getGreatScore());
                score_calc.setDifficulty(game.getDifficulty());
                score_calc.setAchievementLevel();
                score_calc.setMatchName();
                game.addMatch(score_calc);
            }
            else
            {
                score_calc = game.getMatch(matchIndex);
                score_calc.editMatch(players,score,game.getPoorScore(),game.getGreatScore());
                score_calc.setDifficulty(game.getDifficulty());
                score_calc.setAchievementLevel();
                score_calc.setMatchName();
            }

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
                        Intent intent = new Intent(AddScore.this, GamesPlayed.class);
                        intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", null);
        AlertDialog areYouSure = builder.create();
        areYouSure.show();
    }

}