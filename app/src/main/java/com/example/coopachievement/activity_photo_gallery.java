package com.example.coopachievement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

public class activity_photo_gallery extends ArrayAdapter<ScoreCalculator> {

    private Context mContext;
    private int mResource;

    public activity_photo_gallery(@NonNull Context context, int resource, @NonNull List<ScoreCalculator> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        ImageView imageView = convertView.findViewById(R.id.iv_listBoxImage2);
        TextView textView = convertView.findViewById(R.id.tv_listGameTitle2);

        if(getItem(position).getBoxImage() != null){
            imageView.setImageBitmap(getItem(position).getBoxImage());
            System.out.println("This is a test");
        }else{
            imageView.setImageResource(R.drawable.noboard);
            System.out.println("This is a test");
        }

        textView.setText(getItem(position).getMatchName());

        return convertView;
    }
}