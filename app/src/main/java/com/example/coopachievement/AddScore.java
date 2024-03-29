package com.example.coopachievement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class give opportunity to players to add a new game score
 * as well as edit the previous game score
 */
public class AddScore extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game = gameConfig.getCurrentGame();
    ScoreCalculator score_calc;
    boolean difficultySelected = false;
    boolean unsaved = true;
    boolean matchExists = false;
    String difficultyLevel = "Normal";
    ImageView gamesback2;
    ActionBar toolbar;
    Bitmap bitmap;
    boolean is_edit_screen = false;
    ImageView testImage;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
        refreshDisplay();
        //displayImageTaken();
        createDifficultyButtons();
        watchFields();
        displayLevels();
        gamesback2 = findViewById(R.id.backimage2);
        themeback();
        toolbar = getSupportActionBar();
        if (gameConfig.getThemeIndex() == 0) {
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#023020")));
        }
        if (gameConfig.getThemeIndex() == 1) {
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A020F0")));
        }
        if (gameConfig.getThemeIndex() == 2) {
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1493")));
        }
        if (getMatchIndex() == -1) {
            toolbar.setTitle("Adding a new match!");
        } else {
            toolbar.setTitle("Editing match");
        }
        toolbar.setDisplayHomeAsUpEnabled(true);
        displayImageTaken();
        //findViewById(R.id.btn_display_levels).setOnClickListener(v->displayLevels());
        EditText num_players_input = findViewById(R.id.etn_num_players);

        num_players_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals('0') && !s.toString().isEmpty()) {

                    int matchIndex = getMatchIndex();
                    int players = Integer.parseInt(s.toString());
                    ScoreCalculator score_calc;

                    if (matchIndex == -1 && unsaved) {
                        System.out.println("new scores");
                        String[] numPlayers = new String[players];
                        ArrayList<Integer> players_score = new ArrayList<>();
                        for (int i = 0; i < players; i++) {
                            players_score.add(null);
                            numPlayers[i] = "Player " + (i + 1);
                        }
                        int currentMatchIndex = game.getCurrentMatch();
                        if(game.getPlayers_score().size() > 0){
                            if(game.getPlayersScore(currentMatchIndex).size() >0){
                                ArrayList<Integer> scoreList = game.getPlayersScore(currentMatchIndex);
                                System.out.println("not empty");
                                int size = players;
                                if(players > scoreList.size()){
                                    size = scoreList.size();
                                }
                                for(int i = 0; i<size; i++){
                                    System.out.println(i);
                                    players_score.set(i,scoreList.get(i));
                                }
                            }else{
                                System.out.println("empty");
                            }

                        }



                        CalculateAdapter calculatorAdapter = new CalculateAdapter(AddScore.this,
                                R.layout.list_row, players_score, numPlayers);
                        ListView list = findViewById(R.id.lv_player_scores);
                        list.setAdapter(calculatorAdapter);
                    } else { //edit
                        System.out.println("editing the scores");
                        score_calc = game.getMatch(matchIndex);
                        int theScore = getTheScore();
                        System.out.println("The score " + theScore);

                        for (int i = 0; i < score_calc.getPlayerScoresList().size(); i++) {
                            System.out.println("Look " + score_calc.getPlayerScoresList().get(i));
                        }
                        String[] numPlayers = new String[players];
                        ArrayList<Integer> players_score = new ArrayList<>();
                        for (int i = 0; i < players; i++) {
                            if (i >= score_calc.getPlayerScoresList().size()) {
                                players_score.add(null);
                            } else {
                                players_score.add(score_calc.getPlayerScoresList().get(i));
                            }
                            numPlayers[i] = "Player " + (i + 1);
                        }

                        //score_calc.editMatch(players, theScore, game.getPoorScore(), game.getGreatScore());

                        CalculateAdapter calculatorAdapter = new CalculateAdapter(AddScore.this,
                                R.layout.list_row, players_score, numPlayers);
                        ListView list = findViewById(R.id.lv_player_scores);
                        list.setAdapter(calculatorAdapter);
                    }
                    //refreshDisplay();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int getTheScore() {
        int theScore = 0;
        int checked = 0;
        int currentMatchIndex = game.getCurrentMatch();
        EditText numPlayers = findViewById(R.id.etn_num_players);
        int players = Integer.parseInt(numPlayers.getText().toString());
        System.out.println("match index " + currentMatchIndex);
        if (currentMatchIndex > -1) {
            System.out.println("Number of players is " + players);
            if(game.getPlayersScore(currentMatchIndex).size() < players){
                System.out.println(game.getPlayersScore(currentMatchIndex).size());
                players = game.getPlayersScore(currentMatchIndex).size();
            }
            System.out.println("Number of players is " + players);
            for (int i = 0; i < players; i++) {
                if (game.getPlayersScore(currentMatchIndex).get(i) != null) {
                    checked++;
                }
            }
            System.out.println("checked " + checked);
            if (checked == players) {
                for (int i = 0; i < players; i++) {
                    theScore += game.getPlayersScore(currentMatchIndex).get(i);
                }

            }

        }
        return theScore;
    }

        private void themeback () {
            if (gameConfig.getThemeIndex() == 0) {
                gamesback2.setBackgroundResource(R.drawable.background_mythic);
            }
            if (gameConfig.getThemeIndex() == 1) {
                gamesback2.setBackgroundResource(R.drawable.background_planet);
            }
            if (gameConfig.getThemeIndex() == 2) {
                gamesback2.setBackgroundResource(R.drawable.background_greek);
            }
        }

        private void watchFields () {
            EditText players = findViewById(R.id.etn_num_players);

            players.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    displayLevels();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        private void createDifficultyButtons () {
            RadioGroup group = findViewById(R.id.rgDifficulty);
            String[] difficultyOptions = getResources().getStringArray(R.array.Difficulty_Options);

            //creating the buttons
            for (String difficulty : difficultyOptions) {
                RadioButton button = new RadioButton(this);
                button.setText(difficulty);
                button.setPadding(0, 0, 8, 0);

                button.setOnClickListener(v -> {
                    difficultyLevel = difficulty;
                    if (matchExists) {
                        score_calc.setDifficulty(difficulty);
                    }
                    difficultySelected = true;
                    displayLevels();
                });

                group.addView(button);
                System.out.println("Difficulty " + difficultyLevel);
                if (matchExists && Objects.equals(difficulty, score_calc.getDifficulty())) {
                    button.setChecked(true);
                    displayLevels();
                } else {
                    System.out.println("this didnt happen " + difficultyLevel + " vs " + difficulty);
                }
            }
        }

        private void displayLevels () {
            //EditText display_level_players = findViewById(R.id.etn_enter_num_players);
            EditText display_level_players = findViewById(R.id.etn_num_players);
            String dl_players = display_level_players.getText().toString();
            int players = 0;
            if (!dl_players.equals("")) {
                players = Integer.parseInt(dl_players);
            }

            if (players > 0) {

                ScoreCalculator score_calc = new ScoreCalculator();
                score_calc.setPoorScore(game.getPoorScore());
                score_calc.setGreatScore(game.getGreatScore());
                score_calc.setDifficulty(difficultyLevel);
                score_calc.setNumPlayers(players);


                List<String> list = score_calc.fillLevelsList(gameConfig.getThemeNames());
                System.out.println("length of this is " + list.size());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this, R.layout.list_matches, list);
                ListView lvLevelManager = findViewById(R.id.lv_display_levels);
                lvLevelManager.setAdapter(adapter);
            } else {
                Toast.makeText(AddScore.this, "Please enter a valid number of players!", Toast.LENGTH_LONG).show();
            }


        }

        private void refreshDisplay ()
        {
            int matchIndex = getMatchIndex();
            System.out.println("match index " + matchIndex);

            if (matchIndex >= 0) {
                score_calc = game.getMatch(matchIndex);
                EditText players = findViewById(R.id.etn_num_players);
                TextView score = findViewById(R.id.tv_scoreTotal);
                players.setText(String.valueOf(game.getMatch(matchIndex).getNumPlayers()));
                score.setText(String.valueOf(getTheScore()));

                int numberPlayers = Integer.parseInt(players.getText().toString());
                String[] numPlayers = new String[numberPlayers];
                for (int i = 0; i < numberPlayers; i++) {
                    numPlayers[i] = "Player " + (i + 1);
                }

                ScoreCalculator score_calc = game.getMatch(matchIndex);
                ArrayList<Integer> displayPreviousScores = new ArrayList<>();
                int size = numberPlayers;
                if(size > score_calc.getPlayerScoresList().size()){
                    size = score_calc.getPlayerScoresList().size();
                }
                for (int i = 0; i < size; i++) {
                    System.out.println("this is" + score_calc.getPlayerScoresList().get(i));
                    displayPreviousScores.add(score_calc.getPlayerScoresList().get(i));
                }

                CalculateAdapter calculatorAdapter = new CalculateAdapter(AddScore.this,
                        R.layout.list_row, displayPreviousScores, numPlayers);
                ListView list = findViewById(R.id.lv_player_scores);
                list.setAdapter(calculatorAdapter);
                matchExists = true;
            } else { //Creation of a new match defaults to showing 4 player fields
                EditText num_players = findViewById(R.id.etn_num_players);
                num_players.setText("4");
                score_calc = new ScoreCalculator();
                ArrayList<Integer> blank = new ArrayList<>();
                String[] numPlayers = new String[4];
                for (int i = 0; i < 4; i++) {
                    numPlayers[i] = "Player " + (i + 1);
                    blank.add(null);
                }
                score_calc.setPlayersScore(blank);
                CalculateAdapter calculatorAdapter = new CalculateAdapter(AddScore.this,
                        R.layout.list_row, score_calc.getPlayerScoresList(), numPlayers);
                ListView list = (ListView) findViewById(R.id.lv_player_scores);
                list.setAdapter(calculatorAdapter);

            }
        }

        private int getMatchIndex ()
        {
            Intent intent = getIntent();
            return intent.getIntExtra("match_index", -1);
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu)
        {
            refreshDisplay();
            EditText et_players = findViewById(R.id.etn_num_players);
            String st_players = et_players.getText().toString();
            TextView et_score = findViewById(R.id.tv_scoreTotal);
            String st_score = et_score.getText().toString();

            if (!st_players.equals("") && !st_score.equals("")) {
                getMenuInflater().inflate(R.menu.menu_edit_score, menu);
                is_edit_screen = true;
            } else {
                getMenuInflater().inflate(R.menu.menu_add_score, menu);
            }
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item)
        {
            switch (item.getItemId()) {
                case R.id.action_save:
                    if (unsaved) {

                        //openCamera();
                        alertMessage();

                    } else {
                        Toast.makeText(this, "match already saved", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                case R.id.action_delete:
                    deleteMessageConfirm();
                    return true;

                case R.id.action_camera:
                    if(is_edit_screen){
                        Intent intent_retake = new Intent(this, RetakeMatchPhoto.class);
                        startActivity(intent_retake);
                    }
                    else {
                        openCamera();
                    }
                    return true;

                case android.R.id.home:
                    GameConfig gameConfig = GameConfig.getInstance();
                    int currentGameIndex = game.getCurrentMatch();
                    if(game.getPlayersScore(currentGameIndex) != null && !unsaved){
                        game.getPlayersScore(currentGameIndex).clear();
                    }
                    Intent intent = new Intent(this, GamesPlayed.class);
                    intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                    startActivity(intent);
                    finish();
                    return true;

                default:

                    return super.onOptionsItemSelected(item);
            }
        }

    private void openCamera() { //DO NOT MERGE WITH OTHER PHOTO STUFF FROM PHOTO BRANCH
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }

    private void displayImageTaken() { //DO NOT MERGE WITH OTHER PHOTO STUFF FROM PHOTO BRANCH
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle bundle = result.getData().getExtras();
                        bitmap = (Bitmap) bundle.get("data");
                        //boxImage.setImageBitmap(bitmap);

                    }
                });
    }

    private void alertMessage ()
        {
            EditText et_players = findViewById(R.id.etn_num_players);
            String st_players = et_players.getText().toString();
            TextView et_score = findViewById(R.id.tv_scoreTotal);
            String st_score = et_score.getText().toString();
            boolean playersEntered = false;
            int theScore = getTheScore();
            if (theScore > 0) {
                System.out.println("true");
                playersEntered = true;
            }
            System.out.println("The score " + theScore);

            if (!st_players.equals("") && playersEntered) {
                int players = Integer.parseInt(st_players);
                //int score = Integer.parseInt(st_score);
                int score = theScore;
                int matchIndex = getMatchIndex();
                int currentMatchIndex = game.getCurrentMatch();
                ScoreCalculator score_calc;

                if (matchIndex == -1 && unsaved) {
                    score_calc = new ScoreCalculator(players, score, game.getPoorScore(), game.getGreatScore());
                    score_calc.setPlayersScore(game.getPlayersScore(currentMatchIndex));
                    score_calc.setDifficulty(difficultyLevel);
                    score_calc.setAchievementLevel(gameConfig.getThemeNames());
                    score_calc.setMatchName(gameConfig.getThemeNames());

                    game.addMatch(score_calc);
                    game.incrementAchievementLevels(game.getLatestMatch().getIcons(),gameConfig.getThemeNames());

                } else {
                    score_calc = game.getMatch(matchIndex);
                    score_calc.setPlayersScore(game.getPlayersScore(game.getCurrentMatch()));
                    game.decrementAchievementLevels(game.getLatestMatch().getIcons(), gameConfig.getThemeNames());
                    score_calc.editMatch(players, score, game.getPoorScore(), game.getGreatScore());
                    score_calc.setDifficulty(difficultyLevel);
                    score_calc.setAchievementLevel(gameConfig.getThemeNames());
                    score_calc.setMatchName(gameConfig.getThemeNames());
                    game.incrementAchievementLevels(score_calc.getIcons(),gameConfig.getThemeNames());
                }
                unsaved = false;
                if(bitmap != null) {
                    score_calc.setBoxImage(bitmap);
                }

                FragmentManager manager = getSupportFragmentManager();
                AlertMessageFragment alert = new AlertMessageFragment();
                alert.show(manager, "AlertMessage");

            } else {

                Toast.makeText(AddScore.this, "Some values are missing or invalid!", Toast.LENGTH_LONG).show();
            }
        }

        private void deleteMessageConfirm(){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddScore.this);
            builder.setMessage("Are you sure you want to DELETE this match?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        System.out.println("removing match at index " + getMatchIndex());
                        game.decrementAchievementLevels(game.getLatestMatch().getIcons(), gameConfig.getThemeNames());
                        game.removeMatch(getMatchIndex());
                        Intent intent = new Intent(AddScore.this, GamesPlayed.class);
                        intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
                        startActivity(intent);
                        finish();
                    }).setNegativeButton("Cancel", null);
            AlertDialog areYouSure = builder.create();
            areYouSure.show();
        }

    }

