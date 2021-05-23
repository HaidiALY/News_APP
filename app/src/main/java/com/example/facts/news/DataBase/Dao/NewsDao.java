package com.example.facts.news.DataBase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.facts.news.Models.NewsResponse.ArticlesItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewsList(List<ArticlesItem> items);

    @Query("select * from ArticlesItem where sourceId=:sourceId")
    List<ArticlesItem> getNewsBySourceId(String sourceId);


}
