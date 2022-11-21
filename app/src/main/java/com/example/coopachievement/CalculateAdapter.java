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

import com.example.coopachievement.model.GameConfig;
import com.example.coopachievement.model.ScoreCalculator;

import java.util.ArrayList;

public class CalculateAdapter extends ArrayAdapter<Integer> {
    private Context contextmain;
    private int ResourceLayout;
    private ArrayList<Integer> Scores;
    private ScoreCalculator calcScores;

    public CalculateAdapter(Context context, int resourceLayout, ArrayList<Integer> scores, ScoreCalculator calculatingScores) {
        super(context, resourceLayout, scores);
        this.contextmain = context;
        this.ResourceLayout = resourceLayout;
        this.Scores = scores;
        this.calcScores = calculatingScores;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Toast.makeText(contextmain, "infinite 1" , Toast.LENGTH_SHORT).show();

        View itemView = convertView;
        if(itemView == null) {
            //LayoutInflater inflater = LayoutInflater.from(contextmain);
            itemView = LayoutInflater.from(contextmain).inflate(ResourceLayout, parent, false);

        }
        TextView player_text = itemView.findViewById(R.id.tv_player_num);

        player_text.setText("Player " + (position+1));

        String testingplayer = player_text.getText().toString();


        EditText playersScore = itemView.findViewById(R.id.etn_player_score);

        playersScore.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                    String st_score = playersScore.getText().toString();
                                                    int score = Integer.parseInt(st_score);
                                                    Scores.set(position, score);
                                                    GameConfig.getInstance().getCurrentGame().setPlayersScore(Scores);

                                                    calcScores.setPlayersScore(Scores);
                                                    for(int i = 0; i < 3; i++) {
                                                        Toast.makeText(contextmain, "testing calc " + calcScores.getPlayerScoresList().get(i), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {

                                                }
                                            });


                //Integer currentScore =
        return itemView;
    }

    public ArrayList<Integer> getCurrentScores(){
        return Scores;
    }
}
