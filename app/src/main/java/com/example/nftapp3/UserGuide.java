package com.example.nftapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.location.GnssNavigationMessage;
import android.os.Bundle;
import android.telecom.Call;
import android.webkit.WebSettings;
import android.webkit.WebView;

import javax.security.auth.callback.Callback;

public class UserGuide extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        webView = findViewById(R.id.myWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());
        webView.loadUrl("file:///android_asset/User_Guide.html");

    }

    public class Callback extends UserGuide{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view. String url){
            return false;
        }

        public void onBackPressed(){
            if(webView!=null && webView.canGoBack()){
                webView.goBack();
            }
            else{
                super.onBackPressed();
            }
        }

    }
}