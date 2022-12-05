package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.coopachievement.model.GameConfig;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

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
        barChart = findViewById(R.id.barchat);
        barArraylist = new ArrayList<>();
        barArraylist.add(new BarEntry(1f,0));
        barArraylist.add(new BarEntry(2f,1));
        barArraylist.add(new BarEntry(1f,2));
        barArraylist.add(new BarEntry(2f,3));
        barArraylist.add(new BarEntry(1f,4));
        barArraylist.add(new BarEntry(2f,5));
        barArraylist.add(new BarEntry(1f,6));
        barArraylist.add(new BarEntry(2f,7));
        barArraylist.add(new BarEntry(1f,8));
        barArraylist.add(new BarEntry(2f,9));
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
