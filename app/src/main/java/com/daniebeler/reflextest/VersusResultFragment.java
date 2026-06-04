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

public class VersusResultFragment extends Fragment {

    public VersusResultFragment() {
        // Required empty public constructor
    }

    View vStrich3;
    TextView tvResult1, tvResult2, tvHighscore;
    ConstraintLayout clRelativeVR1, clRelativeVR2;
    SharedPreferences spWinner, spHighscore;
    boolean bZeitAbgelaufen = false, bInBackground = false, bClicked = false;
    int iChangeText1 = 0, iChangeText2 = 0, iChangeText3 = 0, iChangeText4 = 0, iChangeText5 = 0, iChangeText6 = 0;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_versus_result, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int cWhite = ContextCompat.getColor(context, R.color.colorWhite);
        final int cTransparent = ContextCompat.getColor(context, R.color.colorTransparent);
        final int cGreen = ContextCompat.getColor(context, R.color.colorGreen);
        final int cLila = ContextCompat.getColor(context, R.color.colorLila);
        final int cRed = ContextCompat.getColor(context, R.color.colorRed);

        final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.anim_text_from_left);

        tvResult1 = view.findViewById(R.id.idResult1);
        tvResult2 = view.findViewById(R.id.idResult2);
        clRelativeVR1 = view.findViewById(R.id.idRelativeVR1);
        clRelativeVR2 = view.findViewById(R.id.idRelativeVR2);
        vStrich3 = view.findViewById(R.id.idStrich3);
        tvHighscore = view.findViewById(R.id.idHighscore);
        spHighscore = context.getApplicationContext().getSharedPreferences("highscore", 0);
        spWinner = context.getApplicationContext().getSharedPreferences("winner", 0);

        ObjectAnimator.ofObject(tvResult1, "TextColor", new ArgbEvaluator(), cTransparent, cWhite).setDuration(500).start();
        ObjectAnimator.ofObject(tvResult2, "TextColor", new ArgbEvaluator(), cTransparent, cWhite).setDuration(500).start();

        if (spHighscore.getLong("highscore", 100000) == 100000) {
            tvHighscore.setText(R.string.no_highscore);
        } else {
            tvHighscore.setText(getString(R.string.highscore, spHighscore.getLong("highscore", 0)));
        }

        if (spWinner.getInt("winner", 0) == 1) {
            clRelativeVR1.setBackgroundColor(cGreen);
            clRelativeVR2.setBackgroundColor(cRed);
            tvResult1.setText(R.string.winner);
            tvResult2.setText(R.string.looser);
            new Handler().postDelayed(() -> {
                ObjectAnimator.ofObject(clRelativeVR1, "backgroundColor", new ArgbEvaluator(), cGreen, cLila).setDuration(1500).start();
                ObjectAnimator.ofObject(clRelativeVR2, "backgroundColor", new ArgbEvaluator(), cRed, cLila).setDuration(1500).start();

                runnable.run();
                runnable2.run();
            }, 2000);
        }
        else {
            clRelativeVR1.setBackgroundColor(cRed);
            clRelativeVR2.setBackgroundColor(cGreen);
            tvResult2.setText(R.string.winner);
            tvResult1.setText(R.string.looser);

            new Handler().postDelayed(() -> {
                ObjectAnimator.ofObject(clRelativeVR1, "backgroundColor", new ArgbEvaluator(), cRed, cLila).setDuration(1500).start();
                ObjectAnimator.ofObject(clRelativeVR2, "backgroundColor", new ArgbEvaluator(), cGreen, cLila).setDuration(1500).start();

                runnable3.run();
                runnable4.run();
            }, 2000);
        }

        clRelativeVR1.setOnTouchListener((view1, event) -> {
            if (bZeitAbgelaufen) {
                if (!bClicked) {
                    bClicked = true;
                    ObjectAnimator.ofObject(clRelativeVR1, "backgroundColor", new ArgbEvaluator(), cLila, cGreen).setDuration(2000).start();
                    ObjectAnimator.ofObject(clRelativeVR2, "backgroundColor", new ArgbEvaluator(), cLila, cGreen).setDuration(2000).start();
                    vStrich3.startAnimation(anim1);
                    vStrich3.setBackgroundColor(cWhite);
                    tvHighscore.startAnimation(anim1);
                    tvHighscore.setTextColor(cWhite);

                    runnable5.run();
                    runnable6.run();

                    new Handler().postDelayed(() -> {
                        if (!bInBackground) {
                            ((MainActivity) context).loadHome();
                        }
                    }, 2000);
                }
            }
            return false;
        });

        clRelativeVR2.setOnTouchListener((view2, event) -> {
            if (bZeitAbgelaufen) {
                if (!bClicked) {
                    bClicked = true;
                    ObjectAnimator.ofObject(clRelativeVR1, "backgroundColor", new ArgbEvaluator(), cLila, cGreen).setDuration(1000).start();
                    ObjectAnimator.ofObject(clRelativeVR2, "backgroundColor", new ArgbEvaluator(), cLila, cGreen).setDuration(1000).start();
                    ObjectAnimator.ofObject(tvResult1, "TextColor", new ArgbEvaluator(), cWhite, cTransparent).setDuration(1000).start();
                    ObjectAnimator.ofObject(tvResult2, "TextColor", new ArgbEvaluator(), cWhite, cTransparent).setDuration(1000).start();
                    new Handler().postDelayed(() -> {
                        if (!bInBackground) {
                            ((MainActivity) context).loadVersus();
                        }
                    }, 1000);
                }
            }
            return false;
        });
    }

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // do something

            iChangeText6++;
            switch (iChangeText6) {
                case 1:
                    tvResult1.setText("Winner!!!");
                    break;
                case 2:
                    tvResult1.setText("Winner!!");
                    break;
                case 3:
                    tvResult1.setText("Winner!");
                    break;
                case 4:
                    tvResult1.setText("Winner");
                    break;
                case 5:
                    tvResult1.setText("Winne");
                    break;
                case 6:
                    tvResult1.setText("Winn");
                    break;
                case 7:
                    tvResult1.setText("Win");
                    break;
                case 8:
                    tvResult1.setText("Wi");
                    break;
                case 9:
                    tvResult1.setText("W");
                    break;
                case 10:
                    tvResult1.setText("");
                    tvResult1.setRotation(0);
                    break;
                case 11:
                    tvResult1.setText("C");
                    break;
                case 12:
                    tvResult1.setText("Ch");
                    break;
                case 13:
                    tvResult1.setText("Cha");
                    break;
                case 14:
                    tvResult1.setText("Chan");
                    break;
                case 15:
                    tvResult1.setText("Chang");
                    break;
                case 16:
                    tvResult1.setText("Change");
                    break;
                case 17:
                    tvResult1.setText("Change ");
                    break;
                case 18:
                    tvResult1.setText("Change G");
                    break;
                case 19:
                    tvResult1.setText("Change Ga");
                    break;
                case 20:
                    tvResult1.setText("Change Gam");
                    break;
                case 21:
                    tvResult1.setText("Change Game");
                    break;
                case 22:
                    tvResult1.setText("Change Gamem");
                    break;
                case 23:
                    tvResult1.setText("Change Gamemo");
                    break;
                case 24:
                    tvResult1.setText("Change Gamemod");
                    break;
                case 25:
                    tvResult1.setText("Change Gamemode");
                    bZeitAbgelaufen = true;
                    break;
                default:
            }

            handler.postDelayed(this, 80);
        }
    };

    final Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            // do something

            iChangeText5++;
            switch (iChangeText5) {
                case 1:
                    tvResult2.setText("Loser...");
                    break;
                case 2:
                    tvResult2.setText("Loser..");
                    break;
                case 3:
                    tvResult2.setText("Loser.");
                    break;
                case 4:
                    tvResult2.setText("Loser");
                    break;
                case 5:
                    tvResult2.setText("Lose");
                    break;
                case 6:
                    tvResult2.setText("Los");
                    break;
                case 7:
                    tvResult2.setText("Lo");
                    break;
                case 8:
                    tvResult2.setText("L");
                    break;
                case 9:
                    tvResult2.setText("");
                    break;
                case 10:
                    tvResult2.setText("R");
                    break;
                case 11:
                    tvResult2.setText("Re");
                    break;
                case 12:
                    tvResult2.setText("Ret");
                    break;
                case 13:
                    tvResult2.setText("Retr");
                    break;
                case 14:
                    tvResult2.setText("Retry");
                    break;
                default:
            }

            handler2.postDelayed(this, 140);
        }
    };

    final Handler handler3 = new Handler();
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            // do something

            iChangeText3++;
            switch (iChangeText3) {
                case 1:
                    tvResult2.setText("Winner!!!");
                    break;
                case 2:
                    tvResult2.setText("Winner!!");
                    break;
                case 3:
                    tvResult2.setText("Winner!");
                    break;
                case 4:
                    tvResult2.setText("Winner");
                    break;
                case 5:
                    tvResult2.setText("Winne");
                    break;
                case 6:
                    tvResult2.setText("Winn");
                    break;
                case 7:
                    tvResult2.setText("Win");
                    break;
                case 8:
                    tvResult2.setText("Wi");
                    break;
                case 9:
                    tvResult2.setText("W");
                    break;
                case 10:
                    tvResult2.setText("");
                    break;
                case 11:
                    tvResult2.setText("R");
                    break;
                case 12:
                    tvResult2.setText("Re");
                    break;
                case 13:
                    tvResult2.setText("Ret");
                    break;
                case 14:
                    tvResult2.setText("Retr");
                    break;
                case 15:
                    tvResult2.setText("Retry");
                    break;
                default:
            }

            handler3.postDelayed(this, 133);
        }
    };

    final Handler handler4 = new Handler();
    Runnable runnable4 = new Runnable() {
        @Override
        public void run() {
            // do something

            iChangeText4++;
            switch (iChangeText4) {
                case 1:
                    tvResult1.setText("Loser...");
                    break;
                case 2:
                    tvResult1.setText("Loser..");
                    break;
                case 3:
                    tvResult1.setText("Loser.");
                    break;
                case 4:
                    tvResult1.setText("Loser");
                    break;
                case 5:
                    tvResult1.setText("Lose");
                    break;
                case 6:
                    tvResult1.setText("Los");
                    break;
                case 7:
                    tvResult1.setText("Lo");
                    break;
                case 8:
                    tvResult1.setText("L");
                    break;
                case 9:
                    tvResult1.setText("");
                    tvResult1.setRotation(0);
                    break;
                case 10:
                    tvResult1.setText("c");
                    break;
                case 11:
                    tvResult1.setText("Ch");
                    break;
                case 12:
                    tvResult1.setText("Cha");
                    break;
                case 13:
                    tvResult1.setText("Chan");
                    break;
                case 14:
                    tvResult1.setText("Chang");
                    break;
                case 15:
                    tvResult1.setText("Change");
                    break;
                case 16:
                    tvResult1.setText("Change ");
                    break;
                case 17:
                    tvResult1.setText("Change G");
                    break;
                case 18:
                    tvResult1.setText("Change Ga");
                    break;
                case 19:
                    tvResult1.setText("Change Gam");
                    break;
                case 20:
                    tvResult1.setText("Change Game");
                    break;
                case 21:
                    tvResult1.setText("Change Gamem");
                    break;
                case 22:
                    tvResult1.setText("Change Gamemo");
                    break;
                case 23:
                    tvResult1.setText("Change Gamemod");
                    break;
                case 24:
                    tvResult1.setText("Change Gamemode");
                    bZeitAbgelaufen = true;
                    break;
                default:
            }

            handler4.postDelayed(this, 83);
        }
    };

    final Handler handler5 = new Handler();
    Runnable runnable5 = new Runnable() {
        @Override
        public void run() {
            // do something

            iChangeText2++;
            switch (iChangeText2) {
                case 1:
                    tvResult1.setText("Change gamemode");
                    break;
                case 2:
                    tvResult1.setText("Change gamemod");
                    break;
                case 3:
                    tvResult1.setText("Change gamemo");
                    break;
                case 4:
                    tvResult1.setText("Change gamem");
                    break;
                case 5:
                    tvResult1.setText("Change game");
                    break;
                case 6:
                    tvResult1.setText("Change gam");
                    break;
                case 7:
                    tvResult1.setText("Change ga");
                    break;
                case 8:
                    tvResult1.setText("Change g");
                    break;
                case 9:
                    tvResult1.setText("Change ");
                    break;
                case 10:
                    tvResult1.setText("Chang");
                    break;
                case 12:
                    tvResult1.setText("Chan");
                    break;
                case 13:
                    tvResult1.setText("Cha");
                    break;
                case 14:
                    tvResult1.setText("Ch");
                    break;
                case 15:
                    tvResult1.setText("C");
                    break;
                case 16:
                    tvResult1.setText("");
                    break;
                case 17:
                    tvResult1.setText("V");
                    break;
                case 18:
                    tvResult1.setText("Ve");
                    break;
                case 19:
                    tvResult1.setText("Ver");
                    break;
                case 20:
                    tvResult1.setText("Vers");
                    break;
                case 21:
                    tvResult1.setText("Versu");
                    break;
                case 22:
                    tvResult1.setText("Versus");
                    break;
                default:
            }

            handler5.postDelayed(this, 90);
        }
    };

    final Handler handler6 = new Handler();
    Runnable runnable6 = new Runnable() {
        @Override
        public void run() {
            // do something
            iChangeText1++;
            switch (iChangeText1) {
                case 1:
                    tvResult2.setText("retr");
                    break;
                case 2:
                    tvResult2.setText("ret");
                    break;
                case 3:
                    tvResult2.setText("re");
                    break;
                case 4:
                    tvResult2.setText("r");
                    break;
                case 5:
                    tvResult2.setText("");
                    break;
                case 6:
                    tvResult2.setText("S");
                    break;
                case 7:
                    tvResult2.setText("So");
                    break;
                case 8:
                    tvResult2.setText("Sol");
                    break;
                case 9:
                    tvResult2.setText("Solo");
                    break;
                default:
            }

            handler6.postDelayed(this, 222);
        }
    };
}