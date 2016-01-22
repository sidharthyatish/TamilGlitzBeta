package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ReaderActivity extends AppCompatActivity {
    Toolbar readerToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Intent intent=new Intent();
        String content=getIntent().getStringExtra("content");
        readerToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(readerToolbar);
        getSupportActionBar().setTitle("Tamil Glitz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readerToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WebView wb= (WebView) findViewById(R.id.webView);
        wb.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        //wb.getSettings().setUseWideViewPort(true);
       // wb.getSettings().setDefaultTextEncodingName("UTF-8");
        wb.setWebChromeClient(new WebChromeClient());
        wb.loadData(content, "text/html;charset=UTF-8", null);
    }
}
