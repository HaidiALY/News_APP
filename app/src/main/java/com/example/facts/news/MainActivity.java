package com.example.facts.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.getnews.news.R;
import com.example.facts.news.Adapters.NewsAdapter;
import com.example.facts.news.Base.BaseActivity;
import com.example.facts.news.Models.NewsResponse.ArticlesItem;
import com.example.facts.news.Models.SourcesResponse.SourcesItem;
import com.example.facts.news.Repo.NewsRepository;

import java.util.List;

public class MainActivity extends BaseActivity {

    protected TabLayout tablayout;
    protected RecyclerView contentRecyclerView;
    NewsRepository repository;
    ArticlesItem articlesItem;
    String Lang="en";
    NewsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        layoutManager=new LinearLayoutManager(activity);
        adapter=new NewsAdapter(null);
        contentRecyclerView.setAdapter(adapter);
        contentRecyclerView.setLayoutManager(layoutManager);
        adapter.setOnShareClickLisnear(new NewsAdapter.onShareClickLisnear() {
            @Override
            public void onShareClick(int pos, ArticlesItem articlesItem) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        adapter.setOnTextClick(new NewsAdapter.onIteamClickLisnear() {
            @Override
            public void onIteamClick(int pos, ArticlesItem articlesItem) {
                String URL=articlesItem.getUrl();
                Log.e("URL",URL);
                Intent intent=new Intent(activity,Desccription.class);
                intent.putExtra("url",URL);
                startActivity(intent);
            }
        });
        repository=new NewsRepository(Lang);
        repository.getNewsSources(onSourcesPreparedListener);



    }


    NewsRepository.onSourcesPreparedListener onSourcesPreparedListener=new NewsRepository.onSourcesPreparedListener() {
        @Override
        public void onSourcesPrepared(final List<SourcesItem> sources) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSourcesOfTabLayout(sources);
                }
            });

        }
    };
    void showSourcesOfTabLayout(final List<SourcesItem> sources){

        if(sources==null||sources.size()==0)return;
        for (SourcesItem source : sources) {

            TabLayout.Tab tab=tablayout.newTab();
            tab.setText(source.getName());
            tab.setTag(source);
            tablayout.addTab(tab);

        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

               SourcesItem source= ((SourcesItem) tab.getTag());
               repository.getNewsBySourceId(source.getId(),onNewsPreparedListner);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                SourcesItem source= ((SourcesItem) tab.getTag());
                repository.getNewsBySourceId(source.getId(),onNewsPreparedListner);
            }
        });
        tablayout.getTabAt(0).select();


    }
    private void initView() {
        tablayout =  findViewById(R.id.tablayout);
        contentRecyclerView =  findViewById(R.id.contentRecyclerView);
    }

    NewsRepository.onNewsPreparedListner onNewsPreparedListner=new NewsRepository.onNewsPreparedListner() {
        @Override
        public void onNewsPrepared(final List<ArticlesItem> newsList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.changeData(newsList);
                }
            });

        }
    };
}
