package com.emohee.rnr;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.Duration;

public class ScoresHome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ScoresHome() {
        // Required empty public constructor
    }

    public static ScoresHome newInstance(String param1, String param2) {
        ScoresHome fragment = new ScoresHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).showDefaultActionBar(AppHelper.DEFAULT_ACTION_BAR_TITLE);

        return inflater.inflate(R.layout.scores_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        try {

            SharedPreferences sharedPref = getActivity().getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);

            Long gameMode1ShortestTimeTaken = sharedPref.getLong(AppHelper.GAME_MODE1_SHORTEST_TIME_TAKEN, 0);
            int gameMode1LeastTurnsTaken = sharedPref.getInt(AppHelper.GAME_MODE1_LEAST_TURNS_TAKEN, 0);
            Long gameMode2ShortestTimeTaken = sharedPref.getLong(AppHelper.GAME_MODE2_SHORTEST_TIME_TAKEN, 0);
            int gameMode2LeastTurnsTaken = sharedPref.getInt(AppHelper.GAME_MODE2_LEAST_TURNS_TAKEN, 0);
            int gameMode3HighestScore = sharedPref.getInt(AppHelper.GAME_MODE3_HIGHEST_SCORE, 0);
            int gameMode3EarliestTurnClownPick = sharedPref.getInt(AppHelper.GAME_MODE3_EARLIEST_TURN_CLOWN_PICK, 0);

            TextView score1 = getActivity().findViewById(R.id.scores_home_score1_value);
            if (gameMode1ShortestTimeTaken == 0) {
                score1.setText("N.A.");
            } else {
                score1.setText(AppHelper.getTimeTaken(Duration.ofMillis(gameMode1ShortestTimeTaken)));
            }

            TextView score2 = getActivity().findViewById(R.id.scores_home_score2_value);
            if (gameMode1LeastTurnsTaken == 0) {
                score2.setText("N.A.");
            } else {
                score2.setText(Integer.toString(gameMode1LeastTurnsTaken) + " turns");
            }

            TextView score3 = getActivity().findViewById(R.id.scores_home_score3_value);
            if (gameMode2ShortestTimeTaken == 0) {
                score3.setText("N.A.");
            } else {
                score3.setText(AppHelper.getTimeTaken(Duration.ofMillis(gameMode2ShortestTimeTaken)));
            }

            TextView score4 = getActivity().findViewById(R.id.scores_home_score4_value);
            if (gameMode2LeastTurnsTaken == 0) {
                score4.setText("N.A.");
            } else {
                score4.setText(Integer.toString(gameMode2LeastTurnsTaken)  + " turns");
            }

            TextView score5 = getActivity().findViewById(R.id.scores_home_score5_value);
            if (gameMode3HighestScore == 0) {
                score5.setText("N.A.");
            } else {
                score5.setText(Integer.toString(gameMode3HighestScore) + " points");
            }

            TextView score6 = getActivity().findViewById(R.id.scores_home_score6_value);
            if (gameMode3EarliestTurnClownPick == 0) {
                score6.setText("N.A.");
            } else {
                score6.setText("Turn " + Integer.toString(gameMode3EarliestTurnClownPick));
            }

            ImageButton deleteButton = getActivity().findViewById(R.id.scores_home_delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(AppHelper.GAME_MODE1_SHORTEST_TIME_TAKEN);
                    editor.remove(AppHelper.GAME_MODE1_LEAST_TURNS_TAKEN);
                    editor.remove(AppHelper.GAME_MODE2_SHORTEST_TIME_TAKEN);
                    editor.remove(AppHelper.GAME_MODE2_LEAST_TURNS_TAKEN);
                    editor.remove(AppHelper.GAME_MODE3_HIGHEST_SCORE);
                    editor.remove(AppHelper.GAME_MODE3_EARLIEST_TURN_CLOWN_PICK);
                    editor.commit();
                    score1.setText("N.A.");
                    score2.setText("N.A.");
                    score3.setText("N.A.");
                    score4.setText("N.A.");
                    score5.setText("N.A.");
                    score6.setText("N.A.");
                }
            });

            ImageView metaboyPic = getView().findViewById(R.id.scores_home_metaboy_pic);
            Glide.with(this)
                    .load(R.drawable.mb_9919_cropped)
                    .fitCenter()
                    .error(R.drawable.mb_9919_cropped_shown_on_error)
                    .into(metaboyPic);

            metaboyPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity(), R.style.gameEnd);

                    final View metaboyClickView = getLayoutInflater().inflate(R.layout.scores_home_metaboy_click, null);

                    TextView metaboyClickPara1 = metaboyClickView.findViewById(R.id.scores_home_metaboy_click_para1);
                    metaboyClickPara1.setText("Clones ?\nNo worries..with crystal-clear memory and great sharp-shooting, they don't stand a chance.");

                    ImageView metaboyPic1 = metaboyClickView.findViewById(R.id.scores_home_metaboy_click_metaboy1_pic);
                    Glide.with(metaboyClickView)
                            .load(R.drawable.mb_9919_cropped)
                            .fitCenter()
                            .error(R.drawable.mb_9919_cropped_shown_on_error)
                            .into(metaboyPic1);

                    materialAlertDialogBuilder.setView(metaboyClickView);
                    materialAlertDialogBuilder.setPositiveButton("Ok !", null);
                    materialAlertDialogBuilder.show();

                }
            });

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
