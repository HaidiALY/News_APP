package com.example.facts.news;

import android.app.Application;

import com.example.facts.news.DataBase.NewsDataBase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NewsDataBase.init(this);
    }
}
