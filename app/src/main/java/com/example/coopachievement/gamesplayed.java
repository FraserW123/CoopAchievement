package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.coopachievement.model.GameConfig;

public class gamesplayed extends AppCompatActivity {
    GameConfig gameconfig;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_gamesplayed);
        gameconfig= GameConfig.getInstance();
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

                                gameconfig.remove(index);
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
                return super.onOptionsItemSelected(item);
        }
    }
}