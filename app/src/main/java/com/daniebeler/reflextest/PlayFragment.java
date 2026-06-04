package com.daniebeler.reflextest;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class PlayFragment extends Fragment {

    public PlayFragment() {
        // Required empty public constructor
    }

    ConstraintLayout rlRelativePlay;
    Handler handlerc;
    SharedPreferences.Editor speScore;
    TextView tvWait;
    Boolean bClicked = false;
    long lStart = 0L, lTime = 0L;
    boolean bTimeIsRunning = false;
    Context context;

    final Runnable updateTimerThreat = new Runnable() {
        @Override
        public void run() {
            if (bTimeIsRunning){
                lTime = System.currentTimeMillis() - lStart;
                handlerc.postDelayed(this, 0);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.anim_shake);

        rlRelativePlay = view.findViewById(R.id.idLayoutPlay);
        tvWait = view.findViewById(R.id.idWFTBC);
        handlerc = new Handler();
        speScore = context.getApplicationContext().getSharedPreferences("score", 0).edit();

        ObjectAnimator.ofObject(tvWait, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(500).start();

        new Handler().postDelayed(() -> {
            rlRelativePlay.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            bTimeIsRunning = true;
            lStart = System.currentTimeMillis();
            tvWait.setText(R.string.tap);
            tvWait.setTextSize(50);
            tvWait.startAnimation(animShake);
            handlerc.postDelayed(updateTimerThreat, 0);
        }, (new Random().nextInt(100 - 1) + 1) * 100);

        rlRelativePlay.setOnTouchListener((view1, motionEvent) -> {
            if (!bClicked) {
                bClicked = true;
                bTimeIsRunning = false;
                rlRelativePlay.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
                speScore.putLong("score", lTime).apply();
                tvWait.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
                ((MainActivity) context).loadResult();
            }
            return false;
        });
    }
}