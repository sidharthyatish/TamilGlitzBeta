package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ReaderTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_test);
        Intent intent=new Intent();
        //String content=getIntent().getStringExtra("content");
        String bitmap = intent.getStringExtra("images");
        NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.testImage);
        ImageLoader imageLoader=CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        thumbNail.setImageUrl(bitmap, imageLoader);
       // TextView tv= (TextView) findViewById(R.id.contenttext);
        //tv.setText(content);
    }
}
