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
    private String[] numPlayers;

    public CalculateAdapter(Context context, int resourceLayout, ArrayList<Integer> scores, String[] numPlayers) {
        super(context, resourceLayout, scores);
        this.contextmain = context;
        this.ResourceLayout = resourceLayout;
        this.Scores = scores;
        this.numPlayers = numPlayers;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        GameConfig gameConfig = GameConfig.getInstance();
        Game game = gameConfig.getCurrentGame();
        int currentMatchIndex = game.getCurrentMatch();
        View itemView = convertView;
        if(itemView == null) {
            itemView = LayoutInflater.from(contextmain).inflate(ResourceLayout, parent, false);

        }

        TextView player_text = itemView.findViewById(R.id.tv_player_num);
        player_text.setText(numPlayers[position]);
        EditText playersScore = itemView.findViewById(R.id.etn_player_score);

        if(!(Scores.get(position) == null)){
            playersScore.setText(Scores.get(position).toString());
        }
        else{
            playersScore.setText("");
        }



        playersScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String[] words = player_text.getText().toString().split(" ");
                if(!s.equals('0') && !(s==null) && !(s.toString().isEmpty())) {

                    String st_score = playersScore.getText().toString();
                    int score = Integer.parseInt(st_score);
                    ArrayList<Integer> temp = new ArrayList<>();
                    game.addPlayerScore(temp);
                    Scores.set((Integer.parseInt(words[1])-1), score);
                    for(int i = 0; i< Scores.size(); i++){
                        System.out.println("Score list " + Scores.get(i));
                    }
                    game.setPlayersScore(Scores, currentMatchIndex);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return itemView;
    }

}
