package com.example.coopachievement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class gamesplayed_bargraph extends AppCompatActivity {

    ArrayList<BarEntry> barArraylist;
    BarChart barChart;
    ActionBar ab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameConfig gameConfig = GameConfig.getInstance();
        super.onCreate(savedInstanceState);
        ab2 = getSupportActionBar();
        ab2.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_gamesplayed_bargraph);

        getIntent();
        int gameBarIndex = getIntent().getIntExtra("gamebar_index",0);
        Game game =gameConfig.getGame(gameBarIndex);
        int[] achievementStats = game.getAchievementLevels();

        barChart = findViewById(R.id.barchat);

        barArraylist = new ArrayList<>();
        barArraylist.add(new BarEntry(achievementStats[0],0));
        barArraylist.add(new BarEntry(2,1));
        barArraylist.add(new BarEntry(1,2));
        barArraylist.add(new BarEntry(2,3));
        barArraylist.add(new BarEntry(1,4));
        barArraylist.add(new BarEntry(2,5));
        barArraylist.add(new BarEntry(1,6));
        barArraylist.add(new BarEntry(2,7));
        barArraylist.add(new BarEntry(1,8));
        barArraylist.add(new BarEntry(2,9));
        BarDataSet barDataSet = new BarDataSet(barArraylist,"Achievement levels");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        ArrayList<String> theAchivementslevels = new ArrayList<>();
        for(int i =1;i<=10;i++){
        theAchivementslevels.add(String.valueOf(i));
        }

        BarData barData = new BarData(theAchivementslevels,barDataSet);
        barChart.setData(barData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
