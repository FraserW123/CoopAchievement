package com.example.coopachievement;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.coopachievement.model.ScoreCalculator;

public class AlertMessageFragment extends AppCompatDialogFragment  {
    ImageView iv_changing_image;

    @NonNull
    //@Override
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

        ScoreCalculator score_calc = ScoreCalculator.getCalculatorInstatnce();
        String level = score_calc.achievementLevel();

        iv_changing_image = v.findViewById(R.id.iv_changing_image);
        change(level);

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations!")
                .setMessage("You are the " + level)
                .setView(v)
                .create();
    }

    public void change(String achieve_level) {
        if (achieve_level.equals("Goofy Goblins!")){
            iv_changing_image.setImageResource(R.drawable.goblin);
        }
        else if (achieve_level.equals("Timid Trolls!")){
            iv_changing_image.setImageResource(R.drawable.troll);
        }
        else if (achieve_level.equals("Zippy Zombies!")){
            iv_changing_image.setImageResource(R.drawable.zombies);
        }
        else if (achieve_level.equals("Spooky Spiders!")){
            iv_changing_image.setImageResource(R.drawable.spiders);
        }
        else if (achieve_level.equals("Vicious Vampires!")){
            iv_changing_image.setImageResource(R.drawable.vampires);
        }
        else if (achieve_level.equals("Lucky Lions!")){
            iv_changing_image.setImageResource(R.drawable.lions);
        }
        else if (achieve_level.equals("Fantastic Fairies!")){
            iv_changing_image.setImageResource(R.drawable.fairies);
        }
        else if (achieve_level.equals("Supreme Serpents!")){
            iv_changing_image.setImageResource(R.drawable.serpent);
        }
        else if (achieve_level.equals("Dancing Dragons!")){
            iv_changing_image.setImageResource(R.drawable.dragon);
        }
        else{
            iv_changing_image.setImageResource(R.drawable.unicorn);
        }
    }
}
