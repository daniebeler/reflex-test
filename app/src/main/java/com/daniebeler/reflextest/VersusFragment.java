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
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class VersusFragment extends Fragment {

    public VersusFragment() {
        // Required empty public constructor
    }

    TextView tvWFTBC1, tvWFTBC2, tvScore1, tvScore2;
    SharedPreferences spWinner;
    ConstraintLayout rlRelativePlay1, rlRelativePlay2;
    int iPunkte1 = 0, iPunkte2 = 0;
    boolean bRandomTimeIsRunning = false, bUserTimeIsrunning = false, bCanClick = false;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_versus, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlRelativePlay1 = view.findViewById(R.id.idLayoutPlay1);
        rlRelativePlay2 = view.findViewById(R.id.idLayoutPlay2);
        tvWFTBC1 = view.findViewById(R.id.idWFTBC1);
        tvWFTBC2 = view.findViewById(R.id.idWFTBC2);
        tvScore1 = view.findViewById(R.id.idScore1);
        tvScore2 = view.findViewById(R.id.idScore2);

        spWinner = context.getApplicationContext().getSharedPreferences("winner", 0);

        ObjectAnimator.ofObject(tvWFTBC1, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(500).start();
        ObjectAnimator.ofObject(tvWFTBC2, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(500).start();


        new Handler().postDelayed(this::StartRandom, 200);


        rlRelativePlay1.setOnTouchListener((view1, motionEvent) -> {
            if (bCanClick) {
                bCanClick = false;
                RandomHandler.removeCallbacksAndMessages(null);
                if (bRandomTimeIsRunning) {
                    iPunkte1--;
                    ObjectAnimator.ofObject(tvWFTBC1, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
                    ObjectAnimator.ofObject(tvWFTBC2, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
                    ObjectAnimator.ofObject(rlRelativePlay1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
                } else {
                    iPunkte1++;
                }

                if (iPunkte2 < 3 && iPunkte1 < 3) {
                    tvScore1.setText(String.valueOf(iPunkte1));
                    tvScore2.setText(String.valueOf(iPunkte2));
                    tvWFTBC1.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    tvWFTBC2.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    if (bUserTimeIsrunning) {
                        ObjectAnimator.ofObject(rlRelativePlay1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
                        new Handler().postDelayed(() -> ObjectAnimator.ofObject(rlRelativePlay2, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start(), 1000);
                    }

                    bRandomTimeIsRunning = false;
                    bUserTimeIsrunning = false;

                    new Handler().postDelayed(this::greenScreen, 3000);
                } else {
                    finish(1);
                }
            }
            return false;
        });

        rlRelativePlay2.setOnTouchListener((view2, motionEvent) -> {
            if (bCanClick) {
                bCanClick = false;
                RandomHandler.removeCallbacksAndMessages(null);
                if (bRandomTimeIsRunning) {
                    iPunkte2--;
                    ObjectAnimator.ofObject(tvWFTBC2, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
                    ObjectAnimator.ofObject(tvWFTBC1, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
                    ObjectAnimator.ofObject(rlRelativePlay2, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
                } else {
                    iPunkte2++;
                }

                if (iPunkte2 < 3 && iPunkte1 < 3) {
                    tvScore1.setText(String.valueOf(iPunkte1));
                    tvScore2.setText(String.valueOf(iPunkte2));
                    tvWFTBC1.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    tvWFTBC2.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    if (bUserTimeIsrunning) {
                        ObjectAnimator.ofObject(rlRelativePlay2, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
                        new Handler().postDelayed(() -> ObjectAnimator.ofObject(rlRelativePlay1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start(), 1000);
                    }

                    bRandomTimeIsRunning = false;
                    bUserTimeIsrunning = false;

                    new Handler().postDelayed(this::greenScreen, 3000);
                } else {
                    finish(2);
                }
            }
            return false;
        });
    }

    public void blueScreen() {
        rlRelativePlay1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        rlRelativePlay2.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        bRandomTimeIsRunning = false;
        bUserTimeIsrunning = true;
        tvWFTBC1.setText(R.string.tap);
        tvWFTBC1.setTextSize(50);
        tvWFTBC1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_shake));
        tvWFTBC2.setText(R.string.tap);
        tvWFTBC2.setTextSize(50);
        tvWFTBC2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_shake));
    }

    public void greenScreen() {
        rlRelativePlay1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        rlRelativePlay2.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        tvWFTBC1.setText(R.string.wait);
        tvWFTBC2.setText(R.string.wait);
        tvWFTBC1.setTextSize(18);
        tvWFTBC2.setTextSize(18);
        tvWFTBC1.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        tvWFTBC2.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        bRandomTimeIsRunning = true;
        bUserTimeIsrunning = false;
        StartRandom();
        ObjectAnimator.ofObject(tvWFTBC1, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(500).start();
        ObjectAnimator.ofObject(tvWFTBC2, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(500).start();
    }

    public void finish(int i) {
        bRandomTimeIsRunning = false;
        bUserTimeIsrunning = false;
        spWinner.edit().putInt("winner", i).apply();
        tvWFTBC1.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
        tvWFTBC2.setTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
        ObjectAnimator.ofObject(tvScore1, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
        ObjectAnimator.ofObject(tvScore2, "TextColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorTransparent)).setDuration(500).start();
        if (i == 1) {
            ObjectAnimator.ofObject(rlRelativePlay1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1000).start();
            ObjectAnimator.ofObject(rlRelativePlay2, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorRed)).setDuration(1000).start();
        } else {
            ObjectAnimator.ofObject(rlRelativePlay2, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1000).start();
            ObjectAnimator.ofObject(rlRelativePlay1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorRed)).setDuration(1000).start();
        }

        new Handler().postDelayed(() -> ((MainActivity) context).loadVersusResult(), 2000);
    }

    public void StartRandom() {
        bRandomTimeIsRunning = true;
        bCanClick = true;
        RandomHandler.postDelayed(RandomRunnable, (new Random().nextInt(120 - 20 + 1) + 20) * 100);
    }

    final Handler RandomHandler = new Handler();
    Runnable RandomRunnable = this::blueScreen;
}