package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ModifiedReader extends AppCompatActivity {
    Toolbar readerToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_reader);
        Intent intent=getIntent();
        String content=getIntent().getStringExtra("content");
        final String shareUrl=intent.getStringExtra("url");
        TextView toolbarTitle= (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(Html.fromHtml(getIntent().getStringExtra("title")));
        readerToolbar= (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(readerToolbar);
        ImageLoader  imageLoader=CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        String bitmap = intent.getStringExtra("images");
        final String attachment=intent.getStringExtra("attachment");
        final String att;
        if(attachment!=null)
            att= attachment.substring(attachment.lastIndexOf("=")+1,attachment.lastIndexOf("\";}"));
        else att=null;
        NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.header);
        thumbNail.setImageUrl(bitmap, imageLoader);
        getSupportActionBar().setTitle("");
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
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }



        //wb.getSettings().setUseWideViewPort(true);
        // wb.getSettings().setDefaultTextEncodingName("UTF-8");
        wb.setWebChromeClient(new WebChromeClient());
        wb.loadData(content, "text/html;charset=UTF-8", null);
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out this article on TamilGlitz : " + shareUrl;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TamilGlitz");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        //CollapsingToolbarLayout head= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        NetworkImageView header= (NetworkImageView) findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(att!=null) {
                    Intent yIntent = new Intent(getApplicationContext(), VideoPlayer.class);
                    yIntent.putExtra("video_id",att);
                    startActivity(yIntent);
                }
            }
        });
    }
}
