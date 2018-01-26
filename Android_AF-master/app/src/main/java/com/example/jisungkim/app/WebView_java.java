package com.example.jisungkim.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by JisungKim on 2018-01-27.
 */

public class WebView_java extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://store.naver.com/restaurants/detail?id=13324757&query=%ED%83%9C%EC%A1%B0%EA%B0%90%EC%9E%90%EA%B5%AD");


    }
}
