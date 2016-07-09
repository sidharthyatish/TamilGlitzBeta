package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ModifiedReader extends AppCompatActivity {
    Toolbar readerToolbar;
    String shareUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_reader);
        Intent intent=getIntent();
        String content=getIntent().getStringExtra("content");
        shareUrl=intent.getStringExtra("url");
       TextView toolbarTitle= (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(Html.fromHtml(getIntent().getStringExtra("title")));
        readerToolbar= (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(readerToolbar);
       // getSupportActionBar().setTitle(Html.fromHtml(getIntent().getStringExtra("title")));
       // setTitle(Html.fromHtml(getIntent().getStringExtra("title")));
        ImageLoader  imageLoader=CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        String bitmap = intent.getStringExtra("images");
        ImageView playButton= (ImageView) findViewById(R.id.videoIcon);
        final String attachment=intent.getStringExtra("attachment");
        final String att;
        if(attachment!=null) {
            att = attachment.substring(attachment.lastIndexOf("=") + 1, attachment.lastIndexOf("\";}"));
        playButton.setVisibility(View.VISIBLE);
        }
        else{
            att=null;
            playButton.setVisibility(View.INVISIBLE);
        }
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
        final WebView wb= (WebView) findViewById(R.id.webView);
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
      //  wb.loadUrl("javascript:document.getElementById('p').style.fontFa‌​mily = 'slabo';");
        //CollapsingToolbarLayout head= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        NetworkImageView header= (NetworkImageView) findViewById(R.id.header);
        if (header != null) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:{
                Toast.makeText(getApplicationContext(),"Preparing to share..",Toast.LENGTH_SHORT).show();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out this article on TamilGlitz : " + shareUrl;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TamilGlitz");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
