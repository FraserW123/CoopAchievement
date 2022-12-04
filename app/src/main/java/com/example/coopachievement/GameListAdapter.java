package com.example.coopachievement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coopachievement.model.Game;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<Game> {
    private Context mContext;
    private int mResource;

    public GameListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Game> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        ImageView imageView = convertView.findViewById(R.id.iv_listBoxImage);
        TextView textView = convertView.findViewById(R.id.tv_listGameTitle);

        if(getItem(position).getBoxImage() != null){
            imageView.setImageBitmap(getItem(position).getBoxImage());
            //getItem(position).recycleImage();
        }else{
            imageView.setImageResource(R.drawable.noboard);
        }

        textView.setText(getItem(position).getName());

        return convertView;
    }
}
