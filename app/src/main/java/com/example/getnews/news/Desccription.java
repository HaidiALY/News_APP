package com.example.getnews.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Desccription extends AppCompatActivity {




    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_desccription);

        webView=findViewById(R.id.webview);

       /* Bundle pos = getIntent().getExtras();


        if (pos != null) {
            String url =pos.getString("url");
            webView.loadUrl(url);
        }*/

       Bundle url=getIntent().getExtras();
       String Url=url.getString("url");
       webView.loadUrl(Url);









    }




}
