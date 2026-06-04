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

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static boolean bFirstTime = false;
    boolean bClicked = false;
    ConstraintLayout rlRelativeHome;
    TextView tvHighScore, tvSolo, tvVersus, tvX, tvY;
    View vStrich1, vStrich2;
    SharedPreferences spHighscore;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = inflater.getContext();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlRelativeHome = view.findViewById(R.id.idLayoutHome);
        tvHighScore = view.findViewById(R.id.idHighscore);
        tvSolo = view.findViewById(R.id.idSolo);
        tvVersus = view.findViewById(R.id.idVersus);
        tvX = view.findViewById(R.id.idx);
        tvY = view.findViewById(R.id.idy);
        spHighscore = context.getSharedPreferences("highscore", 0);
        vStrich1 = view.findViewById(R.id.idStrich1);
        vStrich2 = view.findViewById(R.id.idStrich2);

        final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_left);
        final Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_bottom);
        final Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_left);
        final Animation anim4 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_bottom);
        final Animation anim5 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_top);
        final Animation anim6 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_top);

        bClicked = false;

        if (!bFirstTime) {
            ObjectAnimator.ofObject(rlRelativeHome, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorTransparent), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
            tvHighScore.startAnimation(anim1);
            tvSolo.startAnimation(anim2);
            tvVersus.startAnimation(anim5);
            vStrich1.startAnimation(anim1);
            vStrich2.startAnimation(anim1);
            bFirstTime = true;


        }

        if (spHighscore.getLong("highscore", 100000) == 100000) {
            tvHighScore.setText(R.string.no_highscore);
        } else {
            tvHighScore.setText(getString(R.string.highscore, spHighscore.getLong("highscore", 0)));
        }

        rlRelativeHome.setOnTouchListener((view1, event) -> {
            if (event.getY() < rlRelativeHome.getResources().getDisplayMetrics().heightPixels / 2f) {
                //top
                if (!bClicked) {
                    bClicked = true;
                    tvHighScore.startAnimation(anim3);
                    tvSolo.startAnimation(anim4);
                    tvVersus.startAnimation(anim6);
                    vStrich1.startAnimation(anim3);
                    tvX.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                    tvX.startAnimation(anim2);
                    tvY.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                    tvY.startAnimation(anim5);
                    new Handler().postDelayed(() -> ((MainActivity) context).loadVersus(), 2000);
                }
            } else {
                //bottom
                if (!bClicked) {
                    bClicked = true;
                    tvHighScore.startAnimation(anim3);
                    tvSolo.startAnimation(anim4);
                    tvVersus.startAnimation(anim6);
                    vStrich1.startAnimation(anim3);
                    vStrich2.startAnimation(anim3);
                    new Handler().postDelayed(() -> ((MainActivity) context).loadPlay(), 2000);
                }
            }
            return false;
        });

    }
}