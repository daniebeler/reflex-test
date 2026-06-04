package com.daniebeler.reflextest;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    public ResultFragment() {
        // Required empty public constructor
    }

    ConstraintLayout rlRelativeResult;
    View vStrich1, vStrich2, vStrich3;
    TextView tvScore, tvHighscore, tvRetry, tvGamemeode;
    SharedPreferences spHighscore, spScore;
    SharedPreferences.Editor speHighscore;
    int handlercounter1 = 0, handlercounter2 = 0;
    boolean bClicked = false, toSoonToClick = true;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_right);
        final Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_bottom);
        final Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_left);
        final Animation anim4 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_right);
        final Animation anim5 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_bottom);
        final Animation anim6 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_top);
        final Animation anim7 = AnimationUtils.loadAnimation(context, R.anim.anim_text_to_top);

        rlRelativeResult = view.findViewById(R.id.idLayoutResult);
        tvScore = view.findViewById(R.id.idScore);
        tvHighscore = view.findViewById(R.id.idHighscore);
        tvRetry = view.findViewById(R.id.idRetry);
        tvGamemeode = view.findViewById(R.id.idGamemode);
        vStrich1 = view.findViewById(R.id.idStrich1);
        vStrich2 = view.findViewById(R.id.idStrich2);
        vStrich3 = view.findViewById(R.id.idStrich3);

        tvHighscore.startAnimation(anim3);
        tvScore.startAnimation(anim4);
        tvRetry.startAnimation(anim5);
        tvGamemeode.startAnimation(anim6);
        vStrich1.startAnimation(anim4);
        vStrich2.startAnimation(anim3);

        spHighscore = context.getApplicationContext().getSharedPreferences("highscore", 0);
        speHighscore = spHighscore.edit();
        spScore = context.getApplicationContext().getSharedPreferences("score", 0);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                toSoonToClick = false;
            }
        }, 1000);

        ObjectAnimator.ofObject(rlRelativeResult, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorLila)).setDuration(1000).start();
        ObjectAnimator.ofObject(vStrich3, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorLila)).setDuration(1000).start();

        if (spScore.getLong("score", 100000) == 0){
            tvScore.setText(R.string.to_soon);
        }else{
            if (spScore.getLong("score", 100000) < spHighscore.getLong("highscore", 100000)){
                speHighscore.putLong("highscore", spScore.getLong("score", 0)).apply();
            }
            tvScore.setText(getString(R.string.score, spScore.getLong("score", 0)));
        }
        if (spHighscore.getLong("highscore", 100000) == 100000){
            tvHighscore.setText(R.string.no_highscore);

        }else{
            tvHighscore.setText(getString(R.string.highscore, spHighscore.getLong("highscore", 100000)));
        }

        rlRelativeResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (!bClicked && !toSoonToClick) {
                    if (event.getY() < rlRelativeResult.getResources().getDisplayMetrics().heightPixels / 2f) {
                        //top
                        bClicked = true;
                        ObjectAnimator.ofObject(rlRelativeResult, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorLila), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(1500).start();
                        tvScore.startAnimation(anim1);
                        ObjectAnimator.ofObject(vStrich1, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorWhite), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(2000).start();
                        ObjectAnimator.ofObject(vStrich3, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorLila), ContextCompat.getColor(context, R.color.colorWhite)).setDuration(2000).start();

                        runnable.run();
                        runnable2.run();

                        new Handler().postDelayed(() -> ((MainActivity) context).loadHome(), 2000);
                    }
                    else {
                        //bottom
                        if (!bClicked && !toSoonToClick) {
                            bClicked = true;
                            ObjectAnimator.ofObject(rlRelativeResult, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorLila), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(2000).start();
                            ObjectAnimator.ofObject(vStrich3, "backgroundColor", new ArgbEvaluator(), ContextCompat.getColor(context, R.color.colorLila), ContextCompat.getColor(context, R.color.colorGreen)).setDuration(2000).start();

                            tvHighscore.startAnimation(anim1);
                            tvScore.startAnimation(anim1);
                            tvRetry.startAnimation(anim2);
                            tvGamemeode.startAnimation(anim7);
                            vStrich1.startAnimation(anim1);
                            vStrich2.startAnimation(anim1);

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    ((MainActivity) context).loadPlay();
                                }
                            }, 2000);
                        }
                    }
                }
                return false;
            }
        });
    }

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // do something

            handlercounter1++;
            switch (handlercounter1) {
                case 1:
                    tvRetry.setText("Retr");
                    break;
                case 2:
                    tvRetry.setText("Ret");
                    break;
                case 3:
                    tvRetry.setText("Re");
                    break;
                case 4:
                    tvRetry.setText("R");
                    break;
                case 5:
                    tvRetry.setText("");
                    break;
                case 6:
                    tvRetry.setText("S");
                    break;
                case 7:
                    tvRetry.setText("So");
                    break;
                case 8:
                    tvRetry.setText("Sol");
                    break;
                case 9:
                    tvRetry.setText("Solo");
                    break;
                default:
            }

            handler.postDelayed(this, 222);
        }
    };

    final Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            // do something

            handlercounter2++;
            switch (handlercounter2) {
                case 1:
                    tvGamemeode.setText("Change Gamemode");
                    break;
                case 2:
                    tvGamemeode.setText("Change Gamemod");
                    break;
                case 3:
                    tvGamemeode.setText("Change Gamemo");
                    break;
                case 4:
                    tvGamemeode.setText("Change Gamem");
                    break;
                case 5:
                    tvGamemeode.setText("Change Game");
                    break;
                case 6:
                    tvGamemeode.setText("Change Gam");
                    break;
                case 7:
                    tvGamemeode.setText("Change Ga");
                    break;
                case 8:
                    tvGamemeode.setText("Change G");
                    break;
                case 9:
                    tvGamemeode.setText("Change ");
                    break;
                case 10:
                    tvGamemeode.setText("Chang");
                    break;
                case 12:
                    tvGamemeode.setText("Chan");
                    break;
                case 13:
                    tvGamemeode.setText("Cha");
                    break;
                case 14:
                    tvGamemeode.setText("Ch");
                    break;
                case 15:
                    tvGamemeode.setText("C");
                    break;
                case 16:
                    tvGamemeode.setText("");
                    break;
                case 17:
                    tvGamemeode.setText("V");
                    break;
                case 18:
                    tvGamemeode.setText("Ve");
                    break;
                case 19:
                    tvGamemeode.setText("Ver");
                    break;
                case 20:
                    tvGamemeode.setText("Vers");
                    break;
                case 21:
                    tvGamemeode.setText("Versu");
                    break;
                case 22:
                    tvGamemeode.setText("Versus");
                    break;
                default:
            }

            handler2.postDelayed(this, 90);

        }
    };
}