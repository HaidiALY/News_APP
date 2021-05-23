package com.example.getnews.news.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.getnews.news.DataBase.Dao.NewsDao;
import com.example.getnews.news.DataBase.Dao.SourcesDao;
import com.example.getnews.news.Models.NewsResponse.ArticlesItem;
import com.example.getnews.news.Models.SourcesResponse.SourcesItem;

@Database(entities = {SourcesItem.class,ArticlesItem.class},version = 2,exportSchema = false)
public abstract class NewsDataBase  extends RoomDatabase {

    private static NewsDataBase newsDataBase;
    final static String DBName="newsDataBase";
    public abstract SourcesDao sourcesDao();
    public abstract NewsDao newsDao();


    public static NewsDataBase init(Context context){

        if(newsDataBase==null) {
            newsDataBase = Room.databaseBuilder(context, NewsDataBase.class, DBName)
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()
                    .build();
        }
        return newsDataBase;
    }

    public static NewsDataBase getInstance(){

        return newsDataBase;
    }

}
