package com.example.getnews.news;

import android.app.Application;

import com.example.getnews.news.DataBase.NewsDataBase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NewsDataBase.init(this);
    }
}
