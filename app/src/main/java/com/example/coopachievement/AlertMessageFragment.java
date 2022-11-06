package com.example.coopachievement;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;

public class AlertMessageFragment extends AppCompatDialogFragment  {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.alert_message_layout, null);

        //Create a button listener
        Button button = v.findViewById(R.id.btn_confirmation);
        button.setOnClickListener(w->{
            Intent intent = new Intent(getActivity(), gamesplayed.class);
            startActivity(intent);
        });
        GameConfig gameConfig = GameConfig.getInstance();
        Game game = gameConfig.getCurrentGame();
        String level = game.getMatch().getAchievementLevel();
        System.out.println(game.getNumMatchesPlayed());
        System.out.println(game.getMatchList().get(0));


        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations!")
                .setMessage("You are the " + level)
                .setView(v)
                .create();
    }
}
