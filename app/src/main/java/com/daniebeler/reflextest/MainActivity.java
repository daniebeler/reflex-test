package com.daniebeler.reflextest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private boolean bInBackground = false;
    private String strActiveFragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new HomeFragment());
        ft.commit();
        strActiveFragment = "Home";
    }

    public void loadPlay() {
        if (!bInBackground) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, new PlayFragment());
            ft.commit();
            strActiveFragment = "Play";
        }
    }

    public void loadResult() {
        if (!bInBackground) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, new ResultFragment());
            ft.commit();
            strActiveFragment = "Result";
        }
    }

    public void loadHome() {
        if (!bInBackground) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, new HomeFragment());
            ft.commit();
            strActiveFragment = "Home";
        }
    }

    public void loadVersus() {
        if (!bInBackground) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, new VersusFragment());
            ft.commit();
            strActiveFragment = "Versus";
        }
    }

    public void loadVersusResult() {
        if (!bInBackground) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder, new VersusResultFragment());
            ft.commit();
            strActiveFragment = "VersusResult";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        bInBackground = false;

        if (strActiveFragment.equals("Play")) {
            loadHome();
        } else if (strActiveFragment.equals("Versus")) {
            loadHome();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bInBackground = true;
    }
}