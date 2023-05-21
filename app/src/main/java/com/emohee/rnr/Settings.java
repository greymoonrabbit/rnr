package com.emohee.rnr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Settings extends AppCompatActivity {

    ImageButton settingsOnButton;
    ImageButton settingsOffButton;
    ImageButton normalBoardTypeOnButton;
    ImageButton arcticBoardTypeOnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {

            getSupportActionBar().hide();

            setContentView(R.layout.settings);

            SharedPreferences sharedPref = getSharedPreferences(AppHelper.APP_DATA, android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            settingsOnButton = findViewById(R.id.settings_music_on_button);
            settingsOffButton = findViewById(R.id.settings_music_off_button);

            boolean musicOn = sharedPref.getBoolean(AppHelper.SETTINGS_MUSIC_ON, true);

            toggleMusicOnOff(musicOn);

            normalBoardTypeOnButton = findViewById(R.id.settings_board_type_normal_button);
            arcticBoardTypeOnButton = findViewById(R.id.settings_board_type_arctic_button);

            String boardType = sharedPref.getString(AppHelper.SETTINGS_BOARD_TYPE, AppHelper.BOARD_TYPE_NORMAL);

            toggleBoardType(boardType);



            settingsOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editor.putBoolean(AppHelper.SETTINGS_MUSIC_ON, false);
                    editor.commit();

                    setResult(AppHelper.MUSIC_OFF);

                    toggleMusicOnOff(false);

                    Snackbar.make(view, AppHelper.getEnlargedCentredSpannableString("Music is set to Off", 1.8f), Snackbar.LENGTH_SHORT).show();

                }
            });

            settingsOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editor.putBoolean(AppHelper.SETTINGS_MUSIC_ON, true);
                    editor.commit();

                    setResult(AppHelper.MUSIC_ON);

                    toggleMusicOnOff(true);

                    Snackbar.make(view, AppHelper.getEnlargedCentredSpannableString("Music is set to On", 1.8f), Snackbar.LENGTH_SHORT).show();

                }
            });


            normalBoardTypeOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editor.putString(AppHelper.SETTINGS_BOARD_TYPE,  AppHelper.BOARD_TYPE_ARCTIC);
                    editor.commit();

                    toggleBoardType(AppHelper.BOARD_TYPE_ARCTIC);

                    Snackbar.make(view, AppHelper.getEnlargedCentredSpannableString("Board is set to Arctic", 1.8f), Snackbar.LENGTH_SHORT).show();

                }
            });

            arcticBoardTypeOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editor.putString(AppHelper.SETTINGS_BOARD_TYPE, AppHelper.BOARD_TYPE_NORMAL);
                    editor.commit();

                    toggleBoardType(AppHelper.BOARD_TYPE_NORMAL);

                    Snackbar.make(view, AppHelper.getEnlargedCentredSpannableString("Board is set to Normal", 1.8f), Snackbar.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            finish();

        }

    }

    private void toggleMusicOnOff(boolean musicIsOn) {

        if (musicIsOn) {
            settingsOnButton.setEnabled(true);
            settingsOffButton.setEnabled(false);
            settingsOnButton.setVisibility(View.VISIBLE);
            settingsOffButton.setVisibility(View.INVISIBLE);
        } else {
            settingsOnButton.setEnabled(false);
            settingsOffButton.setEnabled(true);
            settingsOnButton.setVisibility(View.INVISIBLE);
            settingsOffButton.setVisibility(View.VISIBLE);
        }

    }

    private void toggleBoardType(String boardType) {

        if (boardType.equals(AppHelper.BOARD_TYPE_ARCTIC)) {
            normalBoardTypeOnButton.setEnabled(false);
            arcticBoardTypeOnButton.setEnabled(true);
            normalBoardTypeOnButton.setVisibility(View.INVISIBLE);
            arcticBoardTypeOnButton.setVisibility(View.VISIBLE);
        } else {
            normalBoardTypeOnButton.setEnabled(true);
            arcticBoardTypeOnButton.setEnabled(false);
            normalBoardTypeOnButton.setVisibility(View.VISIBLE);
            arcticBoardTypeOnButton.setVisibility(View.INVISIBLE);
        }

    }


}