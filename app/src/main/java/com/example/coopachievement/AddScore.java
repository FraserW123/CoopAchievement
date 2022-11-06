package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

public class AddScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Adding a new game!");
        toolbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        EditText et_players = findViewById(R.id.etn_num_players);
        String st_players = et_players.getText().toString();

        EditText et_score = findViewById(R.id.etn_score);
        String st_score = et_score.getText().toString();

        if(!st_players.equals("") && !st_score.equals("")) {
            getMenuInflater().inflate(R.menu.menu_edit_score, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_add_score, menu);
        }
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                alertMessage();
                return true;

            case R.id.action_delete:
                deleteMessageConfirm();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertMessage(){
        EditText et_players = findViewById(R.id.etn_num_players);
        String st_players = et_players.getText().toString();

        EditText et_score = findViewById(R.id.etn_score);
        String st_score = et_score.getText().toString();

        if(!st_players.equals("") && !st_score.equals("") && !st_players.equals("0")){
            int players = Integer.parseInt(st_players);
            int score = Integer.parseInt(st_score);
            ScoreCalculator score_calc = new ScoreCalculator();
//            ScoreCalculator score_calc = ScoreCalculator.getCalculatorInstance();
            score_calc.setNumPlayers(players);
            score_calc.setScore(score);
            score_calc.setAchievementLevel();
            score_calc.setMatchName();

            System.out.println("the name is " + score_calc.getAchievementLevel());
            GameConfig gameConfig = GameConfig.getInstance();
            Game game = gameConfig.getCurrentGame();
            game.addMatch(score_calc);
            game.addMatchesPlayed();

            FragmentManager manager = getSupportFragmentManager();
            AlertMessageFragment alert = new AlertMessageFragment();
            alert.show(manager, "AlertMessage");
        }
        else {
            Toast.makeText(AddScore.this, "Some values are missing or invalid!", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteMessageConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddScore.this);
        builder.setMessage("Are you sure you want to DELETE this match?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                }).setNegativeButton("Cancel", null);
        AlertDialog areYouSure = builder.create();
        areYouSure.show();
    }

}