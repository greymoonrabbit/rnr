package com.emohee.rnr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    GameHome gameHome;
    ScoresHome scoresHome;
    SanctuaryHome sanctuaryHome;
    ExoPlayer exoPlayer = null;
    boolean musicOnOrOff;
    ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.main_activity);

            showDefaultActionBar(AppHelper.DEFAULT_ACTION_BAR_TITLE);

            gameHome = GameHome.newInstance("g1", "g2");
            scoresHome = ScoresHome.newInstance("s1", "s2");
            sanctuaryHome = SanctuaryHome.newInstance("mg1", "mg2");

            bottomNavigationView = findViewById(R.id.bottom_nav_view);
            bottomNavigationView.setOnItemSelectedListener(this);
            bottomNavigationView.setSelectedItemId(R.id.home_menu);


            SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
            musicOnOrOff = sharedPref.getBoolean(AppHelper.SETTINGS_MUSIC_ON, true);


            exoPlayer = new ExoPlayer.Builder(this).build();
            exoPlayer.setMediaItem(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(R.raw.komiku_skate)));
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
            exoPlayer.prepare();

            if (musicOnOrOff) {
                exoPlayer.setPlayWhenReady(true);
            } else {
                exoPlayer.setPlayWhenReady(false);
            }

            activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            if (result.getResultCode() == AppHelper.MUSIC_ON) {
                                musicOnOrOff = true;
                                startPlayer(exoPlayer);
                            } else if (result.getResultCode() == AppHelper.MUSIC_OFF) {
                                musicOnOrOff = false;
                                pausePlayer(exoPlayer);
                            }

                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.home_menu) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_fragment, gameHome).commit();
            return true;

        } else if (item.getItemId() == R.id.high_scores_menu) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_fragment, scoresHome).commit();
            return true;

        } else if (item.getItemId() == R.id.meta_gallery_menu) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_fragment, sanctuaryHome).commit();
            return true;

        } else {

            return false;

        }

    }

    @Override
    public void onBackPressed() {

        if (bottomNavigationView.getSelectedItemId() == R.id.home_menu) {
            super.onBackPressed();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.home_menu);
        }

    }

    public void darkenActionBar(String newTitle, int colourId) {

        SpannableString spannableString = new SpannableString(newTitle);
        spannableString.setSpan(new ForegroundColorSpan(getColor(colourId)), 0, newTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gray900_bkground));

    }

    public void showDefaultActionBar(String defaultTitle) {

        SpannableString spannableString = new SpannableString(defaultTitle);
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.deeporange50)), 0, defaultTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.deeppurple800_bkground));
        getSupportActionBar().show();

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
            exoPlayer.seekTo(0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);

        MenuBuilder menuBuilder = (MenuBuilder) menu;
        menuBuilder.setOptionalIconsVisible(true);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle = new Bundle();
        Intent intent;

        if (item.getItemId() == R.id.settings_menu) {

            intent = new Intent(this, Settings.class);
            intent.putExtras(bundle);
            activityResultLauncher.launch(intent);

            return true;

        } else if (item.getItemId() == R.id.acknowledgements_menu) {

            intent = new Intent(this, Acknowledgements.class);
            intent.putExtras(bundle);
            activityResultLauncher.launch(intent);

            return true;

        } else if (item.getItemId() == R.id.about_menu) {

            intent = new Intent(this, About.class);
            intent.putExtras(bundle);
            activityResultLauncher.launch(intent);

            return true;

        } else {

            return super.onOptionsItemSelected(item);

        }

    }

}
