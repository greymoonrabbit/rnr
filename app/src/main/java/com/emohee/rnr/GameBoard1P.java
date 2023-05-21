package com.emohee.rnr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


public class GameBoard1P extends AppCompatActivity {

    private String gameMode;
    private String boardType;
    private int boardSize;
    private ArrayList<ImageView> pics;
    private ArrayList<Integer> picIdentifiers;
    private ArrayList<TextView> picCovers;
    float displayMetricsDensity;
    private int roundingRadius = 33;
    private int openPositions;
    private GameTurns gameTurns;
    private int clownPosition;
    private TextView msgToPlayer;

    private SoundPool soundPool;
    private int flipPicSound;
    private int clownSound;
    private int matchSound;

    ExoPlayer exoPlayer = null;
    boolean musicOnOrOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        gameMode = bundle.getString(AppHelper.GAME_MODE);
        boardType = bundle.getString(AppHelper.SETTINGS_BOARD_TYPE);
        boardSize = bundle.getInt(AppHelper.BOARD_SIZE);

        getSupportActionBar().hide();
        setContentView(R.layout.game_board_1p);

        initBoard(gameMode, boardType, boardSize, R.drawable.mb_stx_5033);

        setListeners();

        msgToPlayer = findViewById(R.id.game_board_1p_msg_to_player);

        showMessageToPlayer("Let's Go!");

        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        flipPicSound = soundPool.load(this, R.raw.flip_pic, 1);
        clownSound = soundPool.load(this, R.raw.clown, 2);
        matchSound = soundPool.load(this, R.raw.match, 3);

        SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
        musicOnOrOff = sharedPref.getBoolean(AppHelper.SETTINGS_MUSIC_ON, true);
        exoPlayer = new ExoPlayer.Builder(this).build();
        exoPlayer.setMediaItem(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(R.raw.komiku_school)));
        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        exoPlayer.prepare();

        if (musicOnOrOff) {
            exoPlayer.setPlayWhenReady(true);
        } else {
            exoPlayer.setPlayWhenReady(false);
        }

    }

    private void initBoard(String gameMode, String boardType, int boardSize, int clownPicIdentifier) {

        pics = new ArrayList<>();
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic1));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic2));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic3));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic4));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic5));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic6));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic7));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic8));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic9));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic10));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic11));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic12));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic13));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic14));
        pics.add(findViewById(R.id.game_board_1p_metaboy_pic15));

        picIdentifiers = AppHelper.getRandomPicIdentifiers(boardType, boardSize, AppHelper.SHUFFLE_PIC_POSITIONS);

        picCovers = new ArrayList<>();
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover1));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover2));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover3));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover4));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover5));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover6));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover7));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover8));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover9));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover10));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover11));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover12));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover13));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover14));
        picCovers.add(findViewById(R.id.game_board_1p_metaboy_pic_cover15));

        displayMetricsDensity = getResources().getDisplayMetrics().density;

        openPositions = boardSize;

        for (int i=0; i<picIdentifiers.size(); i++) {
            if (picIdentifiers.get(i) == clownPicIdentifier) {
                clownPosition = i;
            }
        }

        gameTurns = new GameTurns(gameMode);

        if (AppHelper.REVEAL_ALL) {
            for (int i=0; i<boardSize; i++) {
                picCovers.get(i).setVisibility(View.INVISIBLE);
                Glide.with(this)
                        .load(picIdentifiers.get(i))
                        .fitCenter()
                        .into(pics.get(i));
            }
        }

    }

    private void flipPick(int position, Instant pickTime) {

        try {

            picCovers.get(position).setEnabled(false);
            pics.get(position).setEnabled(false);

            soundPool.play(flipPicSound, 1.0f, 1.0f, 1, 0, 1.0f);

            picCovers.get(position).setCameraDistance(13000 * displayMetricsDensity);
            pics.get(position).setCameraDistance(13000 * displayMetricsDensity);
            Animator frontAnim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip);
            frontAnim.setTarget(picCovers.get(position));
            frontAnim.start();
            frontAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    if (openPositions == 1) {

                        //only the clown remains and it's the last pick
                        gameTurns.addClownAsFinalPick("", position, picIdentifiers.get(position), pickTime);

                        soundPool.play(clownSound, 1.0f, 1.0f, 1, 0, 1.0f);

                        openPositions = openPositions - 1;
                        pics.get(position).setVisibility(View.INVISIBLE);
                        picCovers.get(position).setVisibility(View.INVISIBLE);

                        gameEnds();

                    } else if (gameTurns.addPick("", position, picIdentifiers.get(position), pickTime, clownPosition)) {

                        //turn has ended

                        ArrayList<Integer> pickPositionsInLatestTurn = gameTurns.getPickPositionsInLatestTurn();

                        //check for successful match
                        if (gameTurns.successfulMatchInLatestTurn()) {

                            //successful match

                            soundPool.play(matchSound, 1.0f, 1.0f, 1, 0, 1.0f);

                            //update openPositions and remove positions from board
                            for (int i = 0; i < pickPositionsInLatestTurn.size(); i++) {

                                int tempPosition = pickPositionsInLatestTurn.get(i);
                                openPositions = openPositions - 1;
                                pics.get(tempPosition).setVisibility(View.INVISIBLE);
                                picCovers.get(tempPosition).setVisibility(View.INVISIBLE);

                            }

                            if (openPositions == 0) {
                                gameEnds();
                            }

                        } else {

                            //unsuccessful match

                            int tempPosition;

                            for (int i = 0; i < pickPositionsInLatestTurn.size(); i++) {

                                tempPosition = pickPositionsInLatestTurn.get(i);

                                //check if a clown was in one of the picks in this turn
                                if (tempPosition == clownPosition) {

                                    soundPool.play(clownSound, 1.0f, 1.0f, 1, 0, 1.0f);

                                    //remove clown position
                                    openPositions = openPositions - 1;
                                    pics.get(clownPosition).setVisibility(View.INVISIBLE);
                                    picCovers.get(clownPosition).setVisibility(View.INVISIBLE);

                                    //msg to user on clown popping up and disappearing
                                    showMessageToPlayer("The strange clown disappeared!");

                                } else {

                                    //flip back to picCovers
                                    reverseFlipPick(tempPosition);

                                    //re-enable position
                                    picCovers.get(tempPosition).setEnabled(true);
                                    pics.get(tempPosition).setEnabled(true);

                                }

                            }

                        }



                    } else {

                        //do nothing, turn has not ended

                    }

                    //gameTurns.printDetails();

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            Animator backAnim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.reverse_flip);
            backAnim.setTarget(pics.get(position));
            backAnim.start();


        } catch (Exception e) {

            e.printStackTrace();
            finish();

        }

    }

    private void gameEnds() {

        //ensure no visual discrepancies
        for (int i=0; i<boardSize; i++) {
            pics.get(i).setVisibility(View.INVISIBLE);
            picCovers.get(i).setVisibility(View.INVISIBLE);
        }

        //gameTurns.printDetails();

        Duration duration = gameTurns.getGameDuration();
        Long timeInMillis = duration.toMillis();
        String timeTaken = AppHelper.getTimeTaken(duration);
        boolean timeTakenIsRecord = false;
        int turnsTaken = gameTurns.getTurnsTaken();
        boolean turnsTakenIsRecord = false;


        SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        Long shortestTimeTaken = sharedPref.getLong(AppHelper.GAME_MODE1_SHORTEST_TIME_TAKEN, 0);
        int leastTurnsTaken = sharedPref.getInt(AppHelper.GAME_MODE1_LEAST_TURNS_TAKEN, 0);

        if ((shortestTimeTaken == 0) || (timeInMillis < shortestTimeTaken)) {
            editor.putLong(AppHelper.GAME_MODE1_SHORTEST_TIME_TAKEN, timeInMillis);
            editor.apply();
            timeTakenIsRecord = true;
        }

        if ((leastTurnsTaken == 0) || (turnsTaken < leastTurnsTaken)) {
            editor.putInt(AppHelper.GAME_MODE1_LEAST_TURNS_TAKEN, turnsTaken);
            editor.apply();
            turnsTakenIsRecord = true;
        }

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this, R.style.gameEnd);

        final View gameEndView = getLayoutInflater().inflate(R.layout.game_end, null);

        TextView gameEndTitle = gameEndView.findViewById(R.id.game_end_title);
        gameEndTitle.setText("Awesome !");

        TextView gameEndPara1 = gameEndView.findViewById(R.id.game_end_para1);
        SpannableStringBuilder para1 = new SpannableStringBuilder();
        para1.append("Time taken: " + timeTaken);

        if (timeTakenIsRecord) {
            para1.append("\n");
            para1.append(AppHelper.getNewRecordMsg(getColor(AppHelper.HIGH_SCORE_MSG_COLOUR)));
        }

        gameEndPara1.setText(para1);

        TextView gameEndPara2 = gameEndView.findViewById(R.id.game_end_para2);
        SpannableStringBuilder para2 = new SpannableStringBuilder();
        para2.append("Turns taken: " + turnsTaken);

        if (turnsTakenIsRecord) {
            para2.append("\n");
            para2.append(AppHelper.getNewRecordMsg(getColor(AppHelper.HIGH_SCORE_MSG_COLOUR)));
        }

        gameEndPara2.setText(para2);


        ImageView metaboyPic = gameEndView.findViewById(R.id.game_end_metaboy_pic);
        Glide.with(gameEndView)
            .load(R.drawable.mb_3815_cropped)
            .fitCenter()
            .error(R.drawable.mb_3815_cropped_shown_on_error)
            .into(metaboyPic);

        ((ConstraintLayout.LayoutParams) metaboyPic.getLayoutParams()).topToBottom = R.id.game_end_para2;
        metaboyPic.requestLayout();

        TextView gameEndPara4 = gameEndView.findViewById(R.id.game_end_para4);
        gameEndPara4.setText("Have A Great Day !");


        materialAlertDialogBuilder.setView(gameEndView);
        materialAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(100);
                finish();
            }
        });
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.show();


    }


    private void setListeners() {

        for (int i=0; i<picCovers.size(); i++) {

            final int position = i;

            picCovers.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    loadPic(position, Instant.now());
                }

            });

        }

    }

    private void loadPic(int position, Instant pickTime) {

        Glide.with(this)
                .load(picIdentifiers.get(position))
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        flipPick(position, pickTime);
                        return false;
                    }

                })
                .into(pics.get(position));

    }

    private void reverseFlipPick(int position) {

        try {

            picCovers.get(position).setCameraDistance(13000 * displayMetricsDensity);
            pics.get(position).setCameraDistance(13000 * displayMetricsDensity);
            Animator front_anim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.reverse_flip);
            front_anim.setTarget(picCovers.get(position));
            front_anim.start();
            front_anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            Animator back_anim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip);
            back_anim.setTarget(pics.get(position));
            back_anim.start();

        } catch (Exception e) {

            e.printStackTrace();
            finish();

        }

    }

    private void showMessageToPlayer(String message) {

        msgToPlayer.setText(message);

        Animator fadeIn = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.fade_in);
        Animator fadeOut = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.fade_out);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                fadeIn,
                fadeOut
        );
        animatorSet.setTarget(msgToPlayer);
        animatorSet.start();

    }

    @Override
    protected void onPause() {

        super.onPause();
        pausePlayer(exoPlayer);

    }

    @Override
    protected void onStop() {

        super.onStop();
        pausePlayer(exoPlayer);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        releaseExoPlayer(exoPlayer);

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (musicOnOrOff) {
            startPlayer(exoPlayer);
        }

    }

    public static void startPlayer(ExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(true);
        }

    }

    public static void pausePlayer(ExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
        }

    }

    public static void releaseExoPlayer(ExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.release();
        }

    }

}