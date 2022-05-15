package com.example.nftapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class User_Guide extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        WebView web;
        web = findViewById(R.id.myWebView);
        web.loadUrl("file:///android_asset/User_Guide.html");

    }
}