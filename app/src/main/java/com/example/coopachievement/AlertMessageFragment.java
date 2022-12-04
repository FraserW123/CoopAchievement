package com.example.coopachievement;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

/**
 * This class facilitates the users to view there achievement level after putting in scores
 * it also changes the achievement level on increase of the scores
 */
public class AlertMessageFragment extends AppCompatDialogFragment  {
    GameConfig gameConfig = GameConfig.getInstance();
    AnimationDrawable my_congo_anime;
    ImageView iv_changing_image;
    MediaPlayer player;
    View v;
    ImageView animationcard;
    TextView themeName;
    @NonNull
    //@Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create view
        v = LayoutInflater.from(getActivity())
                .inflate(R.layout.alert_message_layout, null);
        player = MediaPlayer.create(getActivity(), R.raw.congo);
        animationcard = v.findViewById(R.id.animatedView);
        themeName = v.findViewById(R.id.changethemetext);
        playsong();
        okaybutton();
        changethemeButton();
        Game game;
        String message;
        gameConfig.setTheme(getResources().getStringArray(R.array.achievements));
        themeName.setText("Theme: "+gameConfig.getTheme());
        game = gameConfig.getCurrentGame();
        game.getLatestMatch().setAchievementThemeNames(gameConfig.getThemeNames());
        String level = game.getLatestMatch().setAchievementLevel();
        String difficulty = game.getLatestMatch().getDifficulty();
        int score = game.getLatestMatch().getScore();
        message = "You are the " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
        acheivementlevelmessage(level,game);
//        if (gameConfig.getThemeIndex() == 1) {
//            gameConfig.setTheme(getResources().getStringArray(R.array.planets));
//            level = game.getLatestMatch().setAchievementLevel();
//            message = "You reached " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
//        }
//        if (gameConfig.getThemeIndex() == 2) {
//            gameConfig.setTheme(getResources().getStringArray(R.array.greek_gods));
//            level = game.getLatestMatch().setAchievementLevel();
//            message = "You became " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
//        }

        iv_changing_image = v.findViewById(R.id.iv_changing_image);
        change(level);
        back_anime();
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations!")
                .setMessage(message)
                .setView(v)
                .create();
    }

    private void acheivementlevelmessage(String level,Game game) {
        String difficulty = game.getLatestMatch().getDifficulty();
        int score = game.getLatestMatch().getScore();
        String message = "You are the " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
        if (gameConfig.getThemeIndex() == 1) {
            gameConfig.setTheme(getResources().getStringArray(R.array.planets));
            level = game.getLatestMatch().setAchievementLevel();
            message = "You reached " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
        }
        if (gameConfig.getThemeIndex() == 2) {
            gameConfig.setTheme(getResources().getStringArray(R.array.greek_gods));
            level = game.getLatestMatch().setAchievementLevel();
            message = "You became " + level + "!\nScore: " + score + "\nDifficulty: " + difficulty;
        }
    }

    private void changethemeButton() {
        Button button = v.findViewById(R.id.changetheme);
        button.setOnClickListener(w->{
            gameConfig.incrementThemeIndex();
            themeName.setText("Theme: "+gameConfig.getTheme());
        });
    }


    private void back_anime() {
        animationcard.setBackgroundResource(R.drawable.my_congo);
        my_congo_anime =(AnimationDrawable) animationcard.getBackground();
        my_congo_anime.start();
    }

    private void okaybutton() {
        Button button = v.findViewById(R.id.btn_confirmation);
        button.setOnClickListener(w->{
            GameConfig gameConfig = GameConfig.getInstance();
            Intent intent = new Intent(getActivity(), GamesPlayed.class);
            //Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("game_index", gameConfig.getCurrentGameIndex());
            setApplicationTheme(gameConfig);
            player.stop();
            player.setLooping(false);
            my_congo_anime.stop();
            startActivity(intent);
        });
    }

    private void playsong() {
        player.start();
        player.setLooping(true);
    }

    public void change(String achieve_level)
    {
        for(int i = 0; i<gameConfig.getThemeIDs().length; i++){
            if(achieve_level.equals(gameConfig.getThemeNames()[i])){
                iv_changing_image.setImageResource(gameConfig.getThemeIDs()[i]);
            }
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
}
