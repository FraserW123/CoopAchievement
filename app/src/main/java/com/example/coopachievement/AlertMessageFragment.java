package com.example.coopachievement;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.coopachievement.model.ScoreCalculator;

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
            Intent intent = new Intent(getActivity(), GameTitle.class);
            startActivity(intent);
        });

        ScoreCalculator score_calc = ScoreCalculator.getCalculatorInstatnce();
        String level = score_calc.achievementLevel();

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations!")
                .setMessage("You are the " + level)
                .setView(v)
                .create();
    }
}
