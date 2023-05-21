package com.emohee.rnr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Acknowledgements extends AppCompatActivity {

    private ActivityResultLauncher<Intent> webActivityResultLauncher;

    private SoundPool soundPool;
    private int clickSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.acknowledgements);

        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        clickSound = soundPool.load(this, R.raw.click_mb, 1);

        webActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //nothing at the moment
                    }
                });

        TextView music = findViewById(R.id.acknowledgements_music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);
                webActivityResultLauncher.launch(new Intent(Intent.ACTION_VIEW, Uri.parse(AppHelper.MUSIC_URL)));
            }
        });

        TextView sfx = findViewById(R.id.acknowledgements_sfx);
        sfx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);
                webActivityResultLauncher.launch(new Intent(Intent.ACTION_VIEW, Uri.parse(AppHelper.SFX_URL)));
            }
        });

        TextView metaboy = findViewById(R.id.acknowledgements_metaboy);
        metaboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);
                webActivityResultLauncher.launch(new Intent(Intent.ACTION_VIEW, Uri.parse(AppHelper.METABOY_RUN_URL)));
            }
        });


    }
}