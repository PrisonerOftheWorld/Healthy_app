package com.devilsoftware.healthy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devilsoftware.healthy.R;

public class InfoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        final Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(mToolbar);

        Glide
                .with(this)
                .load(getIntent().getStringExtra("urlImage"))
                .into((ImageView) findViewById(R.id.expandedImage));

        WebView webView = findViewById(R.id.content);



        final String mimeType = "text/html";
        final String encoding = "UTF-8";


        webView.loadDataWithBaseURL("", getIntent().getStringExtra("content"), mimeType, encoding, "");

    }
}
