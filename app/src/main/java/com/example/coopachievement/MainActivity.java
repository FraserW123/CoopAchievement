package com.example.coopachievement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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
import com.example.coopachievement.model.ScoreCalculator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    TextView themeName;
    AnimationDrawable my_background_anime;
    ImageView testImage;
    int themeNum = 0;
    ImageView animationbackground;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);
        resumeGame();
        findViewById(R.id.addGameConfig).setOnClickListener(v->{
            Intent intent = new Intent(this, GameTitle.class);
            startActivity(intent);
        });
        testImage = findViewById(R.id.test_image);
        animationbackground = findViewById(R.id.animatedmainView);
        nogames = findViewById(R.id.nogames);
        nolist = findViewById(R.id.nolist);

        themeName = findViewById(R.id.tvTheme);


        displayImageTaken();
        useCamera();


        back_anime();

        gameConfig.setTheme(getResources().getStringArray(R.array.achievements));
        themeName.setText("Theme: "+gameConfig.getTheme());
        populateListView();
        listClick();
        storeData();
//        storeGameList();


    }

    private void resumeGame() {
//        Intent intent = getIntent();
//        int gameIndex = intent.getIntExtra("game_index", -1);
//        if(gameIndex != -1){
//
//        }
    }

    private void useCamera() {
        Button camera = findViewById(R.id.btn_camera);
        camera.setOnClickListener(v->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intent);

        });
    }

    private void displayImageTaken() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle bundle = result.getData().getExtras();
                        Bitmap bitmap =(Bitmap) bundle.get("data");
                        testImage.setImageBitmap(bitmap);

                    }
                });
    }


    private void back_anime() {
        animationbackground.setBackgroundResource(R.drawable.gradient);
        my_background_anime =(AnimationDrawable) animationbackground.getBackground();
        my_background_anime.start();
    }

    private void listClick()
    {
        ListView lvManager1 = findViewById(R.id.ListofGames);
        lvManager1.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = (TextView) view;
            String message = "You clicked # " + position + ", which is game: " + textView.getText().toString();
            gameConfig.setAccessedMatches(false);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            my_background_anime.stop();
            SwitchActivity(position);
        });
    }

    private void populateListView()
    {
        System.out.println("doing this");
        GameConfig gameConfig = GameConfig.getInstance();
        List<String> list = gameConfig.getGamesNameList();
        System.out.println("got here");

        if(gameConfig.getGamesNameList().isEmpty() && !gameConfig.getisDelete())
        {

            loadData();
            System.out.println("\n\nthis is not empty " + gameConfig.getGameList().size());
            list = gameConfig.getGamesNameList();
            System.out.println("NEW got here too " + list.size());
//            list = getGameList();

            gameConfig.setisDelete();
            System.out.println("Theme index and num games " + gameConfig.getThemeOG() + " g " + gameConfig.getNumGame());
            themeName.setText("Theme: " + gameConfig.getTheme());
        }

        System.out.println("Still going");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_game_config, list);
        lvManager = findViewById(R.id.ListofGames);
        lvManager.setAdapter(adapter);
        lvManager.setEmptyView(nolist);
        lvManager.setEmptyView(nogames);
    }

    private void storeData(){
        System.out.println("finally got here");
        GameConfig gameConfig = GameConfig.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json;

        if(gameConfig.getGameList() == null){
            ArrayList<Game> thing = new ArrayList<>();
            json = gson.toJson(thing);
        }else{
            if(gameConfig.getGameList().size() > 0){
                gameConfig.getGame(0).setThemeIndexSave(gameConfig.getThemeIndex());
            }
            json = gson.toJson(gameConfig.getGameList());

        }

        System.out.println("what about here??");

        editor.putString("game list", json);
        editor.apply();

    }

    private void loadData(){
        GameConfig gameConfig = GameConfig.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("game list", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        gameConfig.setGameList(gson.fromJson(json, type));
        //System.out.println("theme index from loading " +gameConfig.getGame(0).getThemeIndexSave());

        setApplicationTheme(gameConfig);
        if(gameConfig.getGameList() == null){
            gameConfig.setGameList(new ArrayList<>());
        }
        System.out.println("\n\nlist thing "  + gameConfig.getGameList().size());
        if(gameConfig.getGameList().size() > 0){
            gameConfig.setThemeIndex(gameConfig.getGame(0).getThemeIndexSave());
            System.out.println("this is here "+gameConfig.getGameList().get(0).getName());
        }


    }

    public void SwitchActivity(int position)
    {
        Intent intent = new Intent(this, GamesPlayed.class);
        gameConfig.setCurrentGameIndex(position);
        System.out.println("index " + gameConfig.getThemeIndex());
        setApplicationTheme(gameConfig);

        storeData();
        my_background_anime.stop();
        intent.putExtra("game_index", position);
        startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_Theme:
                gameConfig.incrementThemeIndex();
                themeName.setText("Theme: "+gameConfig.getTheme());
//                if(gameConfig.getThemeOG() % 3 == 0){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//
//                }else if(gameConfig.getThemeOG() % 3 == 1){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }else{
//                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#023020")));
//                }
                System.out.println("theme before increment " + themeNum);



                Toast.makeText(this, "Changing theme", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home:
                this.onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}