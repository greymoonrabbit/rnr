package com.emohee.rnr;

import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class AppHelper {

    public static final String APP_DATA = "APP_DATA";

    public static final String DEFAULT_ACTION_BAR_TITLE = "Welcome to Run and Recall";

    public static final String GAME_MODE = "GAME_MODE";
    public static final String BOARD_SIZE = "BOARD_SIZE";
    public static final String GAME_MODE1 = "1P";
    public static final String GAME_MODE1_SHORTEST_TIME_TAKEN = "GAME_MODE1_SHORTEST_TIME_TAKEN";
    public static final String GAME_MODE1_LEAST_TURNS_TAKEN = "GAME_MODE1_LEAST_TURNS_TAKEN";
    public static final String GAME_MODE2 = "COOP";
    public static final String GAME_MODE2_SHORTEST_TIME_TAKEN = "GAME_MODE2_SHORTEST_TIME_TAKEN";
    public static final String GAME_MODE2_LEAST_TURNS_TAKEN = "GAME_MODE2_LEAST_TURNS_TAKEN";
    public static final String GAME_MODE3 = "COOP";
    public static final String GAME_MODE3_HIGHEST_SCORE = "GAME_MODE3_HIGHEST_SCORE";
    public static final String GAME_MODE3_EARLIEST_TURN_CLOWN_PICK = "GAME_MODE3_EARLIEST_TURN_CLOWN_PICK";

    public static final String PLAYER1 = "One";
    public static final String PLAYER2 = "Two";

    public static final int GAME_MODE3_PTS_FOR_VANILLA_MATCH = 10;
    public static final int GAME_MODE3_PTS_FOR_CLOWN_PICK = 30;

    public static final String NEW_RECORD_MSG = "*** New Record ***";

    public static final ArrayList<Integer> PIC_IDENTIFIERS_POOL;
    public static final ArrayList<Integer> PIC_IDENTIFIERS_POOL_ARCTIC;
    public static final ArrayList<String> PIC_URLS;
    public static final ArrayList<String> PIC_URLS_ARCTIC;
    public static final ArrayList<String> PIC_MSGS;
    public static final ArrayList<String> PIC_MSGS_ARCTIC;

    public static final String METABOY_GME_URL = "https://nft.gamestop.com/collection/MetaBoy";
    public static final String METABOY_STX_URL = "https://gamma.io/collections/metaboy";
    public static final String METABOY_RUN_URL = "https://metaboy.run";
    public static final String MUSIC_URL = "https://www.chosic.com/free-music/all/";
    public static final String SFX_URL = "https://sfxr.me";


    public static final int HIGH_SCORE_MSG_COLOUR = R.color.deeporange600;

    public static final boolean REVEAL_ALL = false;
    public static final boolean SHUFFLE_PIC_POSITIONS = true;

    public static final String SETTINGS_MUSIC_ON = "SETTINGS_MUSIC_ON";
    public static final int MUSIC_ON = 1001;
    public static final int MUSIC_OFF = 1000;

    public static final String SETTINGS_BOARD_TYPE = "SETTINGS_BOARD_TYPE";
    public static final String BOARD_TYPE_NORMAL = "NORMAL";
    public static final String BOARD_TYPE_ARCTIC = "ARCTIC";

    static {

        PIC_IDENTIFIERS_POOL = new ArrayList<>();
        PIC_URLS = new ArrayList<>();
        PIC_MSGS = new ArrayList<>();

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_3815);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running under\nthe Northern Lights!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_1843);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running under\nMt Fuji!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_3412);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running on\nfloating islands!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_stx_1319);
        PIC_URLS.add(METABOY_STX_URL);
        PIC_MSGS.add("Pew pew!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_4498);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running in\na jungle!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_7271);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Racing in\na stadium!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_stx_3528);
        PIC_URLS.add(METABOY_STX_URL);
        PIC_MSGS.add("Running on\nthe moon!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_stx_800);
        PIC_URLS.add(METABOY_STX_URL);
        PIC_MSGS.add("Running with\na banana!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_stx_6712);
        PIC_URLS.add(METABOY_STX_URL);
        PIC_MSGS.add("Running in a\nstrange world!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_8063);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running in a\nspace colony!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_9311);
        PIC_URLS.add(METABOY_GME_URL);
        PIC_MSGS.add("Running in a\nspooky place!");

        PIC_IDENTIFIERS_POOL.add(R.drawable.mb_stx_5033);       //clown
        PIC_URLS.add(METABOY_STX_URL);
        PIC_MSGS.add("Running like\na clown!");

        //arctic set
        PIC_IDENTIFIERS_POOL_ARCTIC = new ArrayList<>();
        PIC_URLS_ARCTIC = new ArrayList<>();
        PIC_MSGS_ARCTIC = new ArrayList<>();

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_stx_3855);
        PIC_URLS_ARCTIC.add(METABOY_STX_URL);
        PIC_MSGS_ARCTIC.add("Running under\nthe Northern Lights!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_5357);
        PIC_URLS_ARCTIC.add(METABOY_GME_URL);
        PIC_MSGS_ARCTIC.add("Running under\nMt Fuji!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_2771);
        PIC_URLS_ARCTIC.add(METABOY_GME_URL);
        PIC_MSGS_ARCTIC.add("Running on\nfloating islands!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_stx_5027);
        PIC_URLS_ARCTIC.add(METABOY_STX_URL);
        PIC_MSGS_ARCTIC.add("Racing in\na stadium!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_1675);
        PIC_URLS_ARCTIC.add(METABOY_GME_URL);
        PIC_MSGS_ARCTIC.add("Running in\na jungle!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_stx_564);
        PIC_URLS_ARCTIC.add(METABOY_STX_URL);
        PIC_MSGS_ARCTIC.add("Pew pew!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_stx_4958);
        PIC_URLS_ARCTIC.add(METABOY_STX_URL);
        PIC_MSGS_ARCTIC.add("Running on\nthe moon!");

        PIC_IDENTIFIERS_POOL_ARCTIC.add(R.drawable.mb_stx_5033);       //clown
        PIC_URLS_ARCTIC.add(METABOY_STX_URL);
        PIC_MSGS_ARCTIC.add("Running like\na clown!");



    }

    public static String getTimeTaken(Duration duration) {

        String result = "";

        long minutes = duration.toMinutes();
        if (minutes != 0) {

            if (minutes == 1) {
                result = result + minutes + " minute ";
            } else {
                result = result + minutes + " minutes ";
            }

            duration = duration.minusMinutes(minutes);

        }

        long seconds = duration.getSeconds();

        if (seconds != 0) {

            if (seconds == 1) {
                result = result + seconds + " second";
            } else {
                result = result + seconds + " seconds";
            }

        }

        return result;

    }

    public static String getUrl(int picPosition) {

        String result = "";

        for (int i=0; i<PIC_IDENTIFIERS_POOL.size(); i++) {
            if (PIC_IDENTIFIERS_POOL.get(i) == PIC_IDENTIFIERS_POOL.get(picPosition)) {
                result = PIC_URLS.get(i);
            }
        }

        return result;

    }

    public static ArrayList<Integer> getRandomPicIdentifiers(String boardType, int boardSize, boolean toShuffle) {

        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> temp;

        if (boardType.equals(AppHelper.BOARD_TYPE_ARCTIC)) {
            temp = new ArrayList<Integer>(PIC_IDENTIFIERS_POOL_ARCTIC);
        } else {
            temp = new ArrayList<Integer>(PIC_IDENTIFIERS_POOL);
        }

        //remove clown
        temp.remove(temp.size() - 1);

        //shuffle to prep for random picks
        Collections.shuffle(temp);

        int uniquePicsRequired = (boardSize - 1) / 2;

        temp = new ArrayList<>(temp.subList(0, uniquePicsRequired));

        //add random picks twice to result to form pairs
        result.addAll(temp);
        result.addAll(temp);

        //add clown
        result.add(PIC_IDENTIFIERS_POOL.get(PIC_IDENTIFIERS_POOL.size() - 1));

        //check whether to shuffle result
        if (toShuffle) { Collections.shuffle(result); }

        return result;

    }

    public static SpannableString getNewRecordMsg(int colorId) {

        SpannableString result;
        result = new SpannableString(AppHelper.NEW_RECORD_MSG);
        result.setSpan(new ForegroundColorSpan(colorId), 0, AppHelper.NEW_RECORD_MSG.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return result;

    }

    public static SpannableString getEnlargedCentredSpannableString(String stringToEnlarge, float proportion) {

        SpannableString spannableString = new SpannableString(stringToEnlarge);
        spannableString.setSpan(new RelativeSizeSpan(proportion), 0, stringToEnlarge.length(), 0);
        spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, stringToEnlarge.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;

    }


}
