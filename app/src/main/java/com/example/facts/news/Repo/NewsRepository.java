package com.example.facts.news.Repo;

import com.example.facts.news.Api.ApiManager;
import com.example.facts.news.DataBase.NewsDataBase;
import com.example.facts.news.Models.NewsResponse.ArticlesItem;
import com.example.facts.news.Models.NewsResponse.NewsResponse;
import com.example.facts.news.Models.SourcesResponse.SourcesItem;
import com.example.facts.news.Models.SourcesResponse.SourcesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    String Lang;
   public static final String APIKEY="dc3d106e730c4256b8c275d9da58d090";


    public NewsRepository(String lang) {
        Lang = lang;
    }

   public void getNewsSources(final onSourcesPreparedListener onSourcesPreparedListener){


       ApiManager
               .getApis()
               .getNewsSources(APIKEY  ,Lang)
               .enqueue(new Callback<SourcesResponse>() {
                   @Override
                   public void onResponse(Call<SourcesResponse> call,
                                          Response<SourcesResponse> response) {


                       if(response.isSuccessful()){
                           if(onSourcesPreparedListener!=null){
                           List<SourcesItem> list
                                   = response
                                   .body()
                                   .getSources();
                           onSourcesPreparedListener.onSourcesPrepared(list);

                           InsertSourcesThread insertSourcesThread=new InsertSourcesThread(list);
                           insertSourcesThread.start();
                           }
                       }
                   }

                   @Override
                   public void onFailure(Call<SourcesResponse> call, Throwable t) {


                       RetrieveSorcesThread retrieveSorcesThread=new RetrieveSorcesThread(onSourcesPreparedListener);
                       retrieveSorcesThread.start();
                       //showMessage(getString(R.string.error),t.getLocalizedMessage(),getString(R.string.ok));
                   }
               });
   }

   public  void getNewsBySourceId(final String sourceId, final onNewsPreparedListner onNewsPreparedListner){

       ApiManager
               .getApis()
               .getNewsBySourceId(APIKEY  ,Lang,sourceId)
               .enqueue(new Callback<NewsResponse>() {
                   @Override
                   public void onResponse(Call<NewsResponse> call,
                                          Response<NewsResponse> response) {


                       if(response.isSuccessful()){
                           List<ArticlesItem>newsList=response.body().getArticles();
                           if(onNewsPreparedListner!=null){
                               onNewsPreparedListner.onNewsPrepared(newsList);

                               InsertArticlesThread insertArticlesThread=new InsertArticlesThread(newsList);
                               insertArticlesThread.start();
                           }

                       }
                   }

                   @Override
                   public void onFailure(Call<NewsResponse> call, Throwable t) {

                       RetrieveNewsBySorcesIdThread retrieveNewsBySorcesIdThread=new RetrieveNewsBySorcesIdThread(sourceId,onNewsPreparedListner);
                       retrieveNewsBySorcesIdThread.start();
                   }
               });

   }

   public  interface onSourcesPreparedListener{

       void onSourcesPrepared(List<SourcesItem>sources);
    }

    public  interface onNewsPreparedListner{

        void onNewsPrepared(List<ArticlesItem>newsList);
    }

    class InsertSourcesThread extends Thread{

        List<SourcesItem> sourcesList;
     public InsertSourcesThread(List<SourcesItem>list){
            this.sourcesList=list;
        }
        @Override
        public void run() {

            NewsDataBase.getInstance().sourcesDao().insertSourcesList(sourcesList);
        }
    }

    class RetrieveSorcesThread extends Thread{

        onSourcesPreparedListener onSourcesPreparedListener;
        RetrieveSorcesThread(onSourcesPreparedListener onSourcesPreparedListener1)
        {
            this.onSourcesPreparedListener=onSourcesPreparedListener1;
        }


        @Override
        public void run() {
            List<SourcesItem>sources=NewsDataBase.getInstance().sourcesDao().getAllSources();

            onSourcesPreparedListener.onSourcesPrepared(sources);
        }
    }

    class  InsertArticlesThread extends Thread{

        List<ArticlesItem>newsList;
        public  InsertArticlesThread(List<ArticlesItem>list){
            this.newsList=list;
        }

        @Override
        public void run() {

            for (ArticlesItem item : newsList) {
                item.setSourceId(item.getSource().getId());
                item.setSourceName(item.getSource().getName());
            }
            NewsDataBase.getInstance().newsDao().insertNewsList(newsList);
        }
    }

    class RetrieveNewsBySorcesIdThread extends Thread{

        String sourceId;
        onNewsPreparedListner onNewsPreparedListner1;

      public   RetrieveNewsBySorcesIdThread(String sourceId,onNewsPreparedListner onNewsPreparedListner1){
            this.sourceId=sourceId;
            this.onNewsPreparedListner1=onNewsPreparedListner1;
        }

        @Override
        public void run() {

         List<ArticlesItem>newsList= NewsDataBase.getInstance().newsDao().getNewsBySourceId(sourceId);
         onNewsPreparedListner1.onNewsPrepared(newsList);
        }
    }

}
