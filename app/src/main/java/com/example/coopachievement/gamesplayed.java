package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

@RequiresApi(api = Build.VERSION_CODES.O)
public class gamesplayed extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    ScoreCalculator scoreCalculator = ScoreCalculator.getCalculatorInstance();
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
        findViewById(R.id.playGame).setOnClickListener(v-> switchScreen());
    }

    private void refreshDisplay() {
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && scoreCalculator.isAccessed()){
            gameIndex = scoreCalculator.getGameIndex();
        }
        if(gameIndex >= 0){
            edited = true;
            game = gameConfig.getGame(gameIndex);
            EditText name = findViewById(R.id.editTextGameName2);
            EditText description = findViewById(R.id.editTextGameDescription2);
            name.setText(game.getName());
            description.setText(game.getDescription());
            scoreCalculator.accessed(false);
        }
    }


    private void populateList() {
        List<String> list = scoreCalculator.getMatches();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_matches,list);
        ListView lvManager = findViewById(R.id.lvMatchView);
        lvManager.setAdapter(adapter);
    }

    private int getGameIndex() {
        Intent intent = getIntent();
        return intent.getIntExtra("game_index", -1);
    }

    private void deleteGame() {
        int gameIndex = getGameIndex();
        if(gameIndex >= 0){
            gameConfig.deleteGame(gameIndex);
            System.out.println("Number of games left " + gameConfig.getNumGame());
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
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL
                                finish();
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


    private void switchScreen() {
        int gameIndex = getGameIndex();
        ScoreCalculator scoreCalculator = ScoreCalculator.getCalculatorInstance();
        scoreCalculator.accessed(true);
        Intent intent = new Intent(this, AddScore.class);
        intent.getIntExtra("game_index", gameIndex);
        startActivity(intent);
    }
}