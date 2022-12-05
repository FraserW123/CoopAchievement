package com.example.coopachievement;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

import java.util.List;

/**
 * This class describes the list of games played in a specific format under a specific game name
 * it also persists the history and previous games played and also gives users a click to edit the game
 * they want to change
 * also shows the empty state on gamesplayed list view when no game is there
 */
public class GamesPlayed extends AppCompatActivity {

    GameConfig gameConfig = GameConfig.getInstance();
    Game game;
    Boolean edited = false;
    ListView lvManager;
    ImageView nogameplayed;
    TextView nogametext;
    ImageView nogames;
    ImageView nolist;
    ImageView gamesback;
    Bitmap bitmap;
    ActionBar ab;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_gamesplayed);
        refreshDisplay();
        populateList();
        listClick();
        seebarchartbutton();
        findViewById(R.id.playGame).setOnClickListener(v-> createNewMatch());
        nogameplayed = findViewById(R.id.nogamesplayed);
        nogametext = findViewById(R.id.textView4);
        lvManager.setEmptyView(nogameplayed);
        lvManager.setEmptyView(nogametext);
        gamesback=findViewById(R.id.backimage);

        themeback();
        displayImageTaken();
    }

    private void seebarchartbutton() {
        Button button = findViewById(R.id.bargraphbutton);
        button.setOnClickListener(w->{
            //Intent intent = new Intent(this, gamesplayed_bargraph.class);
            //startActivity(intent);
        });

    }


    private void themeback() {
        if (gameConfig.getThemeIndex() == 0){
            gamesback.setBackgroundResource(R.drawable.background_mythic);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#023020")));

        }
        if(gameConfig.getThemeIndex()==1){
            gamesback.setBackgroundResource(R.drawable.background_planet);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A020F0")));

        }
        if(gameConfig.getThemeIndex()==2){
            gamesback.setBackgroundResource(R.drawable.background_greek);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1493")));

        }
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
            ImageView boxImage = findViewById(R.id.iv_gameBoxImage);


            name.setText(game.getName());
            description.setText(game.getDescription());
            poor_score.setText(String.valueOf(game.getPoorScore()));
            great_score.setText(String.valueOf(game.getGreatScore()));
            //boxImage.setImageBitmap(game.getBoxImage());

        }
    }

    private void listClick()
    {
            ListView matchManager = findViewById(R.id.lvMatchView);
            matchManager.setOnItemClickListener(((parent, view, position, id) -> {
                if(differenceOf10()){
                    Intent intent = new Intent(this, AddScore.class);
                    intent.putExtra("match_index", position);
                    EditText poor_score = findViewById(R.id.etn_poorScore);
                    EditText great_score = findViewById(R.id.etn_greatScore);
                    game.setCurrentMatch(position);
                    game.setPoorScore(Integer.parseInt(poor_score.getText().toString()));
                    game.setGreatScore(Integer.parseInt(great_score.getText().toString()));
                    //System.out.println("difficulty " + game.getMatchDifficulty());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "One or more required items are missing or invalid!", Toast.LENGTH_SHORT).show();
                }
            }));

    }

    private void populateList()
    {
        System.out.println("\n\nRunning this function right now\n\n");
        int gameIndex = getGameIndex();
        if(gameIndex == -1 && gameConfig.isAccessedMatches()){
            gameIndex = gameConfig.getCurrentGameIndex();
        }

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
       /* ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_matches,list);
        lvManager = findViewById(R.id.lvMatchView);
        lvManager.setAdapter(adapter);*/

        activity_photo_gallery photo_gallery = new activity_photo_gallery(this,R.layout.activity_photo_gallery, game.getMatchList());
        lvManager = findViewById(R.id.lvMatchView);
        lvManager.setAdapter(photo_gallery);
    }

    private void createNewMatch()
    {
        if(validInputFields()) {
            EditText poor_score = findViewById(R.id.etn_poorScore);
            EditText great_score = findViewById(R.id.etn_greatScore);

            game.setPoorScore(Integer.parseInt(poor_score.getText().toString()));
            game.setGreatScore(Integer.parseInt(great_score.getText().toString()));
            gameConfig.setAccessedMatches(true);
            game.setCurrentMatch(game.getNumMatchesPlayed());

            Intent intent = new Intent(this, AddScore.class);
            startActivity(intent);
            finish();

        }

    }

    //Fields cannot be empty
    private boolean validInputFields(){
        EditText name = findViewById(R.id.editTextGameName2);
        EditText description = findViewById(R.id.editTextGameDescription2);
        String gameName = name.getText().toString();
        String gameDesc = description.getText().toString();
        if(gameName.isEmpty() || gameDesc.isEmpty() || !differenceOf10()){
            Toast.makeText(this,"One or more fields missing or invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    private void displayImageTaken() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        ImageView boxImage = findViewById(R.id.iv_gameBoxImage);
                        Bundle bundle = result.getData().getExtras();
                        bitmap =(Bitmap) bundle.get("data");
                        //boxImage.setImageBitmap(bitmap);


                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_edit_score, menu);
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
            case R.id.action_camera:
              //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //activityResultLauncher.launch(intent);
                Intent intent_retake = new Intent(this, RetakeGameBoardPhoto.class);
                startActivity(intent_retake);

                return true;

            case R.id.action_save:
            case android.R.id.home:
                this.onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        if(validInputFields()) {
            EditText name = findViewById(R.id.editTextGameName2);
            EditText desc = findViewById(R.id.editTextGameDescription2);
            EditText poor_score = findViewById(R.id.etn_poorScore);
            EditText great_score = findViewById(R.id.etn_greatScore);

            game.setName(name.getText().toString());
            game.setDescription(desc.getText().toString());

            game.setPoorScore(Integer.parseInt(poor_score.getText().toString()));
            game.setGreatScore(Integer.parseInt(great_score.getText().toString()));
            if(bitmap != null){
                game.setBoxImage(bitmap);
            }
            Intent intent = new Intent(GamesPlayed.this, MainActivity.class);
            startActivity(intent);
            System.out.println("got here");
            finish();

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
            System.out.println(" this is " + (num_great_score - num_poor_score));
            return (num_great_score - num_poor_score) >= 9;
        }
        return false;
    }

}