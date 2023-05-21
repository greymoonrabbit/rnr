package com.emohee.rnr;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.time.Instant;
import java.util.ArrayList;

public class GameBoardPvp extends AppCompatActivity {

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
    private TextView msgToPlayer1;
    private TextView msgToPlayer2;
    private String currentPlayer = AppHelper.PLAYER1;
    private int player1Score = 0;
    private int player2Score = 0;
    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;
    private TextView player1TurnIndicator1;
    private TextView player2TurnIndicator1;
    private ImageView player1TurnIndicator2;
    private ImageView player2TurnIndicator2;
    private String winnerMsg;

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
        setContentView(R.layout.game_board_pvp);

        initBoard(gameMode, boardType, boardSize, R.drawable.mb_stx_5033);

        setListeners();

        msgToPlayer1 = findViewById(R.id.game_board_pvp_msg_to_player1);
        msgToPlayer2 = findViewById(R.id.game_board_pvp_msg_to_player2);
        msgToPlayer2.setRotation(180f);

        player1ScoreTextView = findViewById(R.id.game_board_pvp_player1_score);
        player1ScoreTextView.setText("Points: " + player1Score);
        player2ScoreTextView = findViewById(R.id.game_board_pvp_player2_score);
        player2ScoreTextView.setText("Points: " + player2Score);
        player2ScoreTextView.setRotation(180f);

        player1TurnIndicator2 = findViewById(R.id.game_board_pvp_player1_turn_indicator2);
        player1TurnIndicator1 = findViewById(R.id.game_board_pvp_player1_turn_indicator1);
        player2TurnIndicator2 = findViewById(R.id.game_board_pvp_player2_turn_indicator2);
        player2TurnIndicator2.setRotation(180f);
        player2TurnIndicator1 = findViewById(R.id.game_board_pvp_player2_turn_indicator1);
        player2TurnIndicator1.setRotation(180f);

        showMessageToPlayer(msgToPlayer1, "Player One, Let's Go!");
        player1TurnIndicator1.setVisibility(View.VISIBLE);
        player1TurnIndicator2.setVisibility(View.VISIBLE);

        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        flipPicSound = soundPool.load(this, R.raw.flip_pic, 1);
        clownSound = soundPool.load(this, R.raw.clown, 2);
        matchSound = soundPool.load(this, R.raw.match, 3);

        SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
        musicOnOrOff = sharedPref.getBoolean(AppHelper.SETTINGS_MUSIC_ON, true);
        exoPlayer = new ExoPlayer.Builder(this).build();
        exoPlayer.setMediaItem(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(R.raw.komiku_battle_of_pogs3)));
        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        exoPlayer.prepare();

    }

    private void initBoard(String gameMode, String boardType, int boardSize, int clownPicIdentifier) {

        pics = new ArrayList<>();
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic1));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic2));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic3));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic4));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic5));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic6));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic7));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic8));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic9));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic10));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic11));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic12));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic13));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic14));
        pics.add(findViewById(R.id.game_board_pvp_metaboy_pic15));

        picIdentifiers = AppHelper.getRandomPicIdentifiers(boardType, boardSize, AppHelper.SHUFFLE_PIC_POSITIONS);

        picCovers = new ArrayList<>();
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover1));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover2));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover3));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover4));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover5));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover6));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover7));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover8));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover9));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover10));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover11));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover12));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover13));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover14));
        picCovers.add(findViewById(R.id.game_board_pvp_metaboy_pic_cover15));

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

            for (int i=0; i<boardSize; i++) {
                if (currentPlayer.equals(AppHelper.PLAYER1)) {
                    pics.get(i).setRotation(0f);
                } else {
                    pics.get(i).setRotation(180f);
                }
            }

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
                        gameTurns.addClownAsFinalPick(currentPlayer, position, picIdentifiers.get(position), pickTime);

                        soundPool.play(clownSound, 1.0f, 1.0f, 1, 0, 1.0f);

                        openPositions = openPositions - 1;
                        pics.get(position).setVisibility(View.INVISIBLE);
                        picCovers.get(position).setVisibility(View.INVISIBLE);

                        addToScore(currentPlayer, AppHelper.GAME_MODE3_PTS_FOR_CLOWN_PICK);

                        gameEnds();

                    } else if (gameTurns.addPick(currentPlayer, position, picIdentifiers.get(position), pickTime, clownPosition)) {

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

                            addToScore(currentPlayer, AppHelper.GAME_MODE3_PTS_FOR_VANILLA_MATCH);

                            if (openPositions == 0) {
                                gameEnds();
                            } else {
                                changePlayerTurn();
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

                                    addToScore(currentPlayer, AppHelper.GAME_MODE3_PTS_FOR_CLOWN_PICK);

                                    //msg to user on clown popping up and disappearing
                                    if (currentPlayer.equals(AppHelper.PLAYER1)) {
                                        showMessageToPlayer(msgToPlayer1, "The strange clown disappeared!");
                                    } else {
                                        showMessageToPlayer(msgToPlayer2, "The strange clown disappeared!");
                                    }

                                } else {

                                    //flip back to picCovers
                                    reverseFlipPick(tempPosition);

                                    //re-enable position
                                    picCovers.get(tempPosition).setEnabled(true);
                                    pics.get(tempPosition).setEnabled(true);

                                }

                            }

                            changePlayerTurn();

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

    private void addToScore(String currentPlayer, int score) {

        if (currentPlayer.equals(AppHelper.PLAYER1)) {
            player1Score = player1Score + score;
            player1ScoreTextView.setText("Points: " + player1Score);
        } else {
            player2Score = player2Score + score;
            player2ScoreTextView.setText("Points: " + player2Score);
        }

    }

    private void gameEnds() {

        //ensure no visual discrepancies
        for (int i=0; i<boardSize; i++) {
            pics.get(i).setVisibility(View.INVISIBLE);
            picCovers.get(i).setVisibility(View.INVISIBLE);
        }

        player1TurnIndicator1.setVisibility(View.INVISIBLE);
        player1TurnIndicator2.setVisibility(View.INVISIBLE);
        player2TurnIndicator1.setVisibility(View.INVISIBLE);
        player2TurnIndicator2.setVisibility(View.INVISIBLE);


        //gameTurns.printDetails();

        if (player1Score > player2Score) {
            winnerMsg = "Player " + AppHelper.PLAYER1 + " is the Winner !";
        } else if (player1Score < player2Score) {
            winnerMsg = "Player " + AppHelper.PLAYER2 + " is the Winner !";
        } else {
            winnerMsg = "It's a Tie !";
        }

        int higherScore = Math.max(player1Score, player2Score);
        boolean scoreIsRecord = false;

        int clownPickTurn = gameTurns.getClownPickTurn();
        boolean clownPickTurnIsRecord = false;

        SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        int highestScore = sharedPref.getInt(AppHelper.GAME_MODE3_HIGHEST_SCORE, 0);
        int earliestTurnClownPick = sharedPref.getInt(AppHelper.GAME_MODE3_EARLIEST_TURN_CLOWN_PICK, 0);

        if ((highestScore == 0) || (higherScore > highestScore)) {
            editor.putInt(AppHelper.GAME_MODE3_HIGHEST_SCORE, higherScore);
            editor.apply();
            scoreIsRecord = true;
        }

        if ((earliestTurnClownPick == 0) || (clownPickTurn < earliestTurnClownPick)) {
            editor.putInt(AppHelper.GAME_MODE3_EARLIEST_TURN_CLOWN_PICK, clownPickTurn);
            editor.apply();
            clownPickTurnIsRecord = true;
        }

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this, R.style.gameEnd);

        final View gameEndView = getLayoutInflater().inflate(R.layout.game_end, null);

        TextView gameEndTitle = gameEndView.findViewById(R.id.game_end_title);
        gameEndTitle.setText("Congratulations !");

        TextView gameEndPara1 = gameEndView.findViewById(R.id.game_end_para1);
        gameEndPara1.setText(winnerMsg);

        TextView gameEndPara2 = gameEndView.findViewById(R.id.game_end_para2);
        SpannableStringBuilder para2 = new SpannableStringBuilder();
        para2.append("The winning score is " + higherScore + " !");

        if (scoreIsRecord) {
            para2.append("\n");
            para2.append(AppHelper.getNewRecordMsg(getColor(AppHelper.HIGH_SCORE_MSG_COLOUR)));
        }

        gameEndPara2.setText(para2);

        TextView gameEndPara3 = gameEndView.findViewById(R.id.game_end_para3);
        SpannableStringBuilder para3 = new SpannableStringBuilder();
        para3.append("The Clown was picked by Player " + gameTurns.getClownPickPlayer() + " in Turn " + clownPickTurn + " !");

        if (clownPickTurnIsRecord) {
            para3.append("\n");
            para3.append(AppHelper.getNewRecordMsg(getColor(AppHelper.HIGH_SCORE_MSG_COLOUR)));
        }

        gameEndPara3.setText(para3);


        ImageView metaboyPic = gameEndView.findViewById(R.id.game_end_metaboy_pic);
        Glide.with(gameEndView)
                .load(R.drawable.mb_3815_cropped)
                .fitCenter()
                .error(R.drawable.mb_3815_cropped_shown_on_error)
                .into(metaboyPic);

        TextView gameEndPara4 = gameEndView.findViewById(R.id.game_end_para4);
        gameEndPara4.setText("Have A Great Day !");


        materialAlertDialogBuilder.setView(gameEndView);
        materialAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(300);
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
            Animator frontAnim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.reverse_flip);
            frontAnim.setTarget(picCovers.get(position));
            frontAnim.start();
            frontAnim.addListener(new Animator.AnimatorListener() {
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

            Animator backAnim = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip);
            backAnim.setTarget(pics.get(position));
            backAnim.start();

        } catch (Exception e) {

            e.printStackTrace();
            finish();

        }

    }

    private void showMessageToPlayer(TextView textView, String message) {

        textView.setText(message);

        Animator fadeIn = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.fade_in);
        Animator fadeOut = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.fade_out);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                fadeIn,
                fadeOut
        );
        animatorSet.setTarget(textView);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        animatorSet.start();

    }

    private void changePlayerTurn() {

        if (currentPlayer.equals(AppHelper.PLAYER1)) {

            player1TurnIndicator1.setVisibility(View.INVISIBLE);
            player1TurnIndicator2.setVisibility(View.INVISIBLE);

            currentPlayer = AppHelper.PLAYER2;

            player2TurnIndicator1.setVisibility(View.VISIBLE);
            player2TurnIndicator2.setVisibility(View.VISIBLE);

        } else {

            player2TurnIndicator1.setVisibility(View.INVISIBLE);
            player2TurnIndicator2.setVisibility(View.INVISIBLE);

            currentPlayer = AppHelper.PLAYER1;

            player1TurnIndicator1.setVisibility(View.VISIBLE);
            player1TurnIndicator2.setVisibility(View.VISIBLE);

        }

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