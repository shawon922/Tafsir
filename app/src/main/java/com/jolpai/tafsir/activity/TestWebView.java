package com.jolpai.tafsir.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.jolpai.tafsir.R;

public class TestWebView extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);

        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);

        String customHtml = "<html><body><h2>Greetings from JavaCodeGeeks</h2></body></html>";
        webView.loadData(customHtml, "text/html", "UTF-8");


    }


}
