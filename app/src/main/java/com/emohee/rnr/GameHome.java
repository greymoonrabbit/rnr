package com.emohee.rnr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GameHome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ActivityResultLauncher<Intent> gameResultLauncher;

    private SoundPool soundPool;
    private int clickSound;
    private int laserSound;


    public GameHome() {
        // Required empty public constructor
    }

    public static GameHome newInstance(String param1, String param2) {
        GameHome fragment = new GameHome();
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

        return inflater.inflate(R.layout.game_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        try {

            soundPool = new SoundPool.Builder().setMaxStreams(3).build();
            clickSound = soundPool.load(getContext(), R.raw.click_game, 1);
            laserSound = soundPool.load(getContext(), R.raw.laser, 1);

            gameResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            //do nothing at the moment
                        }
                    });

            Button launch1PGame = getView().findViewById(R.id.game_home_launch_1p_game);
            launch1PGame.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);

                    Bundle bundle = new Bundle();
                    bundle.putString(AppHelper.GAME_MODE, AppHelper.GAME_MODE1);
                    bundle.putString(AppHelper.SETTINGS_BOARD_TYPE, getCurrentBoardType());
                    bundle.putInt(AppHelper.BOARD_SIZE, 15);
                    Intent intent = new Intent(getContext(), GameBoard1P.class);
                    intent.putExtras(bundle);

                    gameResultLauncher.launch(intent);

                }

            });

            Button launchCoopGame = getView().findViewById(R.id.game_home_launch_coop_game);
            launchCoopGame.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);

                    Bundle bundle = new Bundle();
                    bundle.putString(AppHelper.GAME_MODE, AppHelper.GAME_MODE2);
                    bundle.putString(AppHelper.SETTINGS_BOARD_TYPE, getCurrentBoardType());
                    bundle.putInt(AppHelper.BOARD_SIZE, 15);
                    Intent intent = new Intent(getContext(), GameBoardCoop.class);
                    intent.putExtras(bundle);

                    gameResultLauncher.launch(intent);

                }

            });

            Button launchPvpGame = getView().findViewById(R.id.game_home_launch_pvp_game);
            launchPvpGame.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    soundPool.play(laserSound, 1.0f, 1.0f, 1, 0, 1.0f);

                    Bundle bundle = new Bundle();
                    bundle.putString(AppHelper.GAME_MODE, AppHelper.GAME_MODE3);


                    bundle.putString(AppHelper.SETTINGS_BOARD_TYPE, getCurrentBoardType());
                    bundle.putInt(AppHelper.BOARD_SIZE, 15);
                    Intent intent = new Intent(getContext(), GameBoardPvp.class);
                    intent.putExtras(bundle);

                    gameResultLauncher.launch(intent);

                }

            });

            TextView marqueeMsg = getView().findViewById(R.id.game_home_marquee_msg);
            //marqueeMsg.setSelected(true);

            ImageView metaboyPic = getView().findViewById(R.id.game_home_metaboy_pic);
            Glide.with(this)
                    .load(R.drawable.mb_3815_cropped)
                    .fitCenter()
                    .error(R.drawable.mb_3815_cropped_shown_on_error)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            marqueeMsg.setSelected(true);
                            return false;
                        }
                    })
                    .into(metaboyPic);

            metaboyPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity(), R.style.gameEnd);

                    final View metaboyClickView = getLayoutInflater().inflate(R.layout.game_home_metaboy_click, null);

                    TextView metaboyClickPara1 = metaboyClickView.findViewById(R.id.game_home_metaboy_click_para1);
                    metaboyClickPara1.setText("Please !  Stop with the cloning !");

                    TextView metaboyClickPara2 = metaboyClickView.findViewById(R.id.game_home_metaboy_click_para2);
                    metaboyClickPara2.setText("Bwahahahahah !");

                    ImageView metaboyPic1 = metaboyClickView.findViewById(R.id.game_home_metaboy_click_metaboy1_pic);
                    Glide.with(metaboyClickView)
                            .load(R.drawable.mb_3815_cropped)
                            .fitCenter()
                            .error(R.drawable.mb_3815_cropped_shown_on_error)
                            .into(metaboyPic1);

                    ImageView metaboyPic2 = metaboyClickView.findViewById(R.id.game_home_metaboy_click_metaboy2_pic);
                    Glide.with(metaboyClickView)
                            .load(R.drawable.mb_3032_cropped)
                            .fitCenter()
                            .error(R.drawable.mb_3032_cropped_shown_on_error)
                            .into(metaboyPic2);


                    materialAlertDialogBuilder.setView(metaboyClickView);
                    materialAlertDialogBuilder.setPositiveButton("Poor Kid..", null);
                    materialAlertDialogBuilder.show();

                }
            });


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private String getCurrentBoardType() {

        SharedPreferences sharedPref = getActivity().getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
        String boardType = sharedPref.getString(AppHelper.SETTINGS_BOARD_TYPE, AppHelper.BOARD_TYPE_NORMAL);

        return boardType;

    }

}