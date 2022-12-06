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
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

public class RetakeGameBoardPhoto extends AppCompatActivity {
    Bitmap bitmap;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView gamesback;
    ActionBar ab;
    GameConfig gameConfig = GameConfig.getInstance();
    Game game = gameConfig.getCurrentGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retake_game_board_photo);

        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ImageView boxImage = findViewById(R.id.iv_view_photo);


        boxImage.setImageBitmap(game.getBoxImage());

        gamesback=findViewById(R.id.backimage);

        displayImageTaken();
        useCamera();
        savePhoto();

        themeback();
    }

    private void savePhoto() {
        Button savePhoto = findViewById(R.id.btn_save_photo);
        savePhoto.setOnClickListener(v->{
            game.setBoxImage(bitmap);
            backGamesScreen();
        });
    }

    private void themeback() {
        Button retakePhoto = findViewById(R.id.btn_retake);
        Button savePhoto = findViewById(R.id.btn_save_photo);
        if (gameConfig.getThemeIndex() == 0){
            gamesback.setBackgroundResource(R.drawable.background_mythic);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#023020")));
            retakePhoto.setBackgroundColor(Color.parseColor("#023020"));
            savePhoto.setBackgroundColor(Color.parseColor("#023020"));

        }
        if(gameConfig.getThemeIndex()==1){
            gamesback.setBackgroundResource(R.drawable.background_planet);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A020F0")));
            retakePhoto.setBackgroundColor(Color.parseColor("#A020F0"));
            savePhoto.setBackgroundColor(Color.parseColor("#A020F0"));

        }
        if(gameConfig.getThemeIndex()==2){
            gamesback.setBackgroundResource(R.drawable.background_greek);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1493")));
            retakePhoto.setBackgroundColor(Color.parseColor("#FF1493"));
            savePhoto.setBackgroundColor(Color.parseColor("#FF1493"));

        }
    }

    private void useCamera() {
        Button camera = findViewById(R.id.btn_retake);
        camera.setOnClickListener(v->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intent);

        });
    }

    private void displayImageTaken() { //DO NOT MERGE WITH OTHER PHOTO STUFF FROM PHOTO BRANCH
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        ImageView boxImage = findViewById(R.id.iv_view_photo);
                        Bundle bundle = result.getData().getExtras();
                        bitmap = (Bitmap) bundle.get("data");
                        boxImage.setImageBitmap(bitmap);
                        //game.setBoxImage(bitmap);

                    }
                });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                backGamesScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backGamesScreen(){
        Intent intent = new Intent(this, GamesPlayed.class);
        intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
        startActivity(intent);
    }

}