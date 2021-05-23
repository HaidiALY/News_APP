package com.example.facts.news;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.getnews.news.R;
import com.example.facts.news.Base.BaseActivity;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(activity,MainActivity.class));
                    }
                },2000);
    }
}
