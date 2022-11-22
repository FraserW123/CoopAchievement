package com.example.coopachievement;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coopachievement.model.Game;
import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;
import java.util.SortedMap;

public class CalculateAdapter extends ArrayAdapter<Integer> {
    private Context contextmain;
    private int ResourceLayout;
    private ArrayList<Integer> Scores;

    private ScoreCalculator calcScores;

    public CalculateAdapter(Context context, int resourceLayout, ArrayList<Integer> scores) {
        super(context, resourceLayout, scores);
        this.contextmain = context;
        this.ResourceLayout = resourceLayout;
        this.Scores = scores;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Toast.makeText(contextmain, "infinite 1" , Toast.LENGTH_SHORT).show();
        GameConfig gameConfig = GameConfig.getInstance();
        Game game = gameConfig.getCurrentGame();
        View itemView = convertView;
        if(itemView == null) {
            //LayoutInflater inflater = LayoutInflater.from(contextmain);
            itemView = LayoutInflater.from(contextmain).inflate(ResourceLayout, parent, false);

        }
        TextView player_text = itemView.findViewById(R.id.tv_player_num);

        player_text.setText("Player " + (position+1));

        EditText playersScore = itemView.findViewById(R.id.etn_player_score);


        if(!(Scores.get(position) == null)){
            playersScore.setText(Scores.get(position).toString());
        }


        playersScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals('0') && !(s==null) && !(s.toString().isEmpty())) {

                    String st_score = playersScore.getText().toString();
                    int score = Integer.parseInt(st_score);

                    System.out.println("position " + position);
                    Scores.set(position, score);
                    for(int i = 0; i< Scores.size(); i++){
                        System.out.println("Score list " + Scores.get(i));
                    }
                    game.setPlayersScore(Scores);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return itemView;
    }

    public ArrayList<Integer> getCurrentScores(){
        return Scores;
    }
}
