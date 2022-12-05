package com.example.coopachievement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coopachievement.model.GameConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboutScreen extends AppCompatActivity {
    GameConfig gameConfig = GameConfig.getInstance();
    ImageView gamesback;

    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_about_screen);

        gameConfig.setTheme(getResources().getStringArray(R.array.achievements));

        gamesback=findViewById(R.id.backimage_credits);
        themeback();
        String[] link = getResources().getStringArray(R.array.links);
        List<String> links = new ArrayList<String>(Arrays.asList(link));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_matches, links);
        ListView lvLinks = findViewById(R.id.lvPhotoLinks);
        lvLinks.setAdapter(adapter);

    }

    private void themeback() {
        if (gameConfig.getThemeIndex() == 0){
            gamesback.setBackgroundResource(R.drawable.mythback);
        }
        if(gameConfig.getThemeIndex()==1){
            gamesback.setBackgroundResource(R.drawable.planetback);
        }
        if(gameConfig.getThemeIndex()==2){
            gamesback.setBackgroundResource(R.drawable.greekback);
        }
    }
}