package com.emohee.rnr;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class SanctuaryHome extends Fragment implements View.OnClickListener {

    private ArrayList<ImageView> pics;
    private ArrayList<TextView> picMsgs;
    private int roundingRadius = 30;
    private ActivityResultLauncher<Intent> webActivityResultLauncher;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SoundPool soundPool;
    private int clickSound;


    public SanctuaryHome() {
        // Required empty public constructor
    }

    public static SanctuaryHome newInstance(String param1, String param2) {
        SanctuaryHome fragment = new SanctuaryHome();
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

        ((MainActivity)getActivity()).darkenActionBar("A Peaceful Meta-Sanctuary", R.color.deeppurple100);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sanctuary_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        try {

            soundPool = new SoundPool.Builder().setMaxStreams(3).build();
            clickSound = soundPool.load(getContext(), R.raw.click_mb, 1);

            pics = new ArrayList<>();
            pics.add(getView().findViewById(R.id.sanctuary_pic1));
            pics.add(getView().findViewById(R.id.sanctuary_pic2));
            pics.add(getView().findViewById(R.id.sanctuary_pic3));
            pics.add(getView().findViewById(R.id.sanctuary_pic4));
            pics.add(getView().findViewById(R.id.sanctuary_pic5));
            pics.add(getView().findViewById(R.id.sanctuary_pic6));
            pics.add(getView().findViewById(R.id.sanctuary_pic7));
            pics.add(getView().findViewById(R.id.sanctuary_pic8));
            pics.add(getView().findViewById(R.id.sanctuary_pic9));
            pics.add(getView().findViewById(R.id.sanctuary_pic10));
            pics.add(getView().findViewById(R.id.sanctuary_pic11));
            pics.add(getView().findViewById(R.id.sanctuary_pic12));

            picMsgs = new ArrayList<>();
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic1_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic2_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic3_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic4_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic5_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic6_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic7_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic8_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic9_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic10_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic11_msg));
            picMsgs.add(getView().findViewById(R.id.sanctuary_pic12_msg));


            for (int i=0; i<pics.size(); i++) {

                Glide.with(this)
                        .load(AppHelper.PIC_IDENTIFIERS_POOL.get(i))
                        .fitCenter()
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(roundingRadius)))
                        .into(pics.get(i));

                pics.get(i).setOnClickListener(this);
                pics.get(i).setTag(i);
                picMsgs.get(i).setText(AppHelper.PIC_MSGS.get(i));

            }

            webActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            //nothing at the moment
                        }
                    });

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onClick(View view) {

        soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);

        int picTag = (int) view.getTag();
        String url = AppHelper.getUrl(picTag);

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity(), R.style.sanctuaryClick);

        final View gameEndView = getLayoutInflater().inflate(R.layout.sanctuary_click, null);

        TextView sanctuaryClickTitle = gameEndView.findViewById(R.id.sanctuary_click_title);
        sanctuaryClickTitle.setText("Hello !");

        TextView sanctuaryClickPara1 = gameEndView.findViewById(R.id.sanctuary_click_para1);
        sanctuaryClickPara1.setText("This digital art is created by MetaBoy, a talented team whose work is available on the GameStop and Gamma NFT marketplaces.");

        TextView sanctuaryClickPara2 = gameEndView.findViewById(R.id.sanctuary_click_para2);
        sanctuaryClickPara2.setText("Navigate to the respective marketplace to find out more?");

        TextView sanctuaryClickPara3 = gameEndView.findViewById(R.id.sanctuary_click_para3);
        sanctuaryClickPara3.setVisibility(View.INVISIBLE);

        materialAlertDialogBuilder.setView(gameEndView);
        materialAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                webActivityResultLauncher.launch(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        materialAlertDialogBuilder.setNegativeButton("Cancel", null);
        materialAlertDialogBuilder.show();

    }



}