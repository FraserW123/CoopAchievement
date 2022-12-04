package com.example.coopachievement;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;


/**
 *This class describes the game title i.e. name and description and also let users to save it
 * poor and good score should have difference of 10
 */
public class GameTitle extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;
    ImageView gamesback3;
    Bitmap bitmap;
    ActionBar toolbar;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        toolbar=getSupportActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this, "the theme is " + gameConfig.getTheme(), Toast.LENGTH_SHORT).show();
        findViewById(R.id.startGame).setOnClickListener(v-> createNewMatch());
        gamesback3=findViewById(R.id.backimage3);
        if (gameConfig.getThemeIndex() == 0) {
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#023020")));
        }
        if(gameConfig.getThemeIndex()==1){
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A020F0")));
        }
        if(gameConfig.getThemeIndex()==2){
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1493")));
        }
        themeback();


        //ImageView boxImage = findViewById(R.id.iv_testBox);
        displayImageTaken();
        useCamera();
    }

    private void useCamera() {
        Button camera = findViewById(R.id.btn_BoxCamera);
        camera.setOnClickListener(v->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intent);

        });
    }

    private void displayImageTaken() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        ImageView boxImage = findViewById(R.id.iv_testBox);
                        Bundle bundle = result.getData().getExtras();
                        bitmap =(Bitmap) bundle.get("data");
                        boxImage.setImageBitmap(bitmap);


                    }
                });
    }

    private void themeback() {
        if (gameConfig.getThemeIndex() == 0){
            gamesback3.setBackgroundResource(R.drawable.background_mythic);
        }
        if(gameConfig.getThemeIndex()==1){
            gamesback3.setBackgroundResource(R.drawable.background_planet);
        }
        if(gameConfig.getThemeIndex()==2){
            gamesback3.setBackgroundResource(R.drawable.background_greek);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_score,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveGame()
    {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);

        if (validInputFields()) {
            EditText poorScore = findViewById(R.id.etn_poor_score);
            EditText greatScore = findViewById(R.id.etn_great_score);

            int num_poor_score = Integer.parseInt(poorScore.getText().toString());
            int num_great_score = Integer.parseInt(greatScore.getText().toString());

            Game game = new Game(name.getText().toString(), description.getText().toString(), num_poor_score, num_great_score);
            if(bitmap != null){
                game.setBoxImage(bitmap);
                //bitmap.recycle();
            }

            gameConfig.addGame(game);
            backToMain();
        }

    }

    // Fields cannot be empty
    private boolean validInputFields(){
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        String gameName = name.getText().toString();
        String gameDesc = description.getText().toString();
        if(gameName.isEmpty() || gameDesc.isEmpty() || !differenceOf10()){
            Toast.makeText(this,"One or more fields missing or invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean differenceOf10(){
        EditText poorScore = findViewById(R.id.etn_poor_score);
        EditText greatScore = findViewById(R.id.etn_great_score);

        String st_poor_score = poorScore.getText().toString();
        String st_great_score = greatScore.getText().toString();

        if(!st_poor_score.equals("") && !st_great_score.equals("")) {
            int num_poor_score = Integer.parseInt(st_poor_score);
            int num_great_score = Integer.parseInt(st_great_score);
            return (num_great_score - num_poor_score) >= 9;
        }

        return false;
    }

    private void backToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createNewMatch()
    {
        EditText name = findViewById(R.id.editTextGameName);
        EditText description = findViewById(R.id.editTextGameDescription);
        if(validInputFields()){
            EditText poorScore = findViewById(R.id.etn_poor_score);
            EditText greatScore = findViewById(R.id.etn_great_score);

            int num_poor_score = Integer.parseInt(poorScore.getText().toString());
            int num_great_score = Integer.parseInt(greatScore.getText().toString());
            int gamesPlayed = gameConfig.getNumGame();

            Game game = new Game(name.getText().toString(), description.getText().toString(), num_poor_score, num_great_score);
            gameConfig.setCurrentGameIndex(gamesPlayed);
            gameConfig.addGame(game);
            gameConfig.setAccessedMatches(true);
            setApplicationTheme(gameConfig);
            game.setCurrentMatch(game.getNumMatchesPlayed());
            Intent switching = new Intent(this, AddScore.class);
            startActivity(switching);
        }
    }

    private void setApplicationTheme(GameConfig gameConfig) {
        if (gameConfig.getThemeIndex() == 0) {
            gameConfig.setTheme(getResources().getStringArray(R.array.achievements));
        } else if(gameConfig.getThemeIndex() == 1) {
            gameConfig.setTheme(getResources().getStringArray(R.array.planets));
        }else{
            gameConfig.setTheme(getResources().getStringArray(R.array.greek_gods));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.action_save)
        {
            saveGame();
        }
        //finish();
        return super.onOptionsItemSelected(item);
    }
}