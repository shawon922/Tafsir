package com.jolpai.tafsir.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.VerseAdapter;
import com.jolpai.tafsir.adapter.VerseAdapter_ArabicOnly;
import com.jolpai.tafsir.custom.listener.HidingScrollListener;
import com.jolpai.tafsir.custom.view.R2L;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.Settings;
import com.jolpai.tafsir.model.Verse;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.utility.Typefaces;

import java.util.ArrayList;
import java.util.List;

public class VerseDetail_ArabicOnly extends AppCompatActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private ImageView settingImageView;
    private String surahNo;
    private String surahName;
    private Context context;
    private R2L mCustomR2L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_arabic_only);
        context=VerseDetail_ArabicOnly.this;
        mCustomR2L = (R2L)findViewById(R.id.cl_R2L);

        DatabaseManager dbm = new DatabaseManager(VerseDetail_ArabicOnly.this);

        Intent intent = getIntent();
        surahNo = intent.getStringExtra("surahNo");
        surahName = intent.getStringExtra("surahName");
        getData();


       // setHtml();
        setRawData();
    }

    private void getData(){
        verseTransList();//testing english trans
        getDataFromPref();
    }
    private void setData(){

    }

    public void setRawData(){
        List<Verse> itemList= Global.getVerseList();
        mCustomR2L.removeAllViews();
        for(int k=0;k<itemList.size();k++){
            VerseArabic verseArabic = (VerseArabic) itemList.get(k);
          //  String [] arrayWhitespace = verseArabic.getVerse().split("\\s+");
            View normalView =View.inflate(context, R.layout.normal_view, null);

            TextView tv;
            tv = (TextView)normalView.findViewById(R.id.textView);
            tv.setText(verseArabic.getVerse());
            tv.setTypeface(Global.getTypefaceArabic());
            tv.setTextSize(Global.arabicFontSize);
            tv.setTextColor(Color.DKGRAY);
            /*for (int i=0; i<arrayWhitespace.length; i++) {
               // View normalView =View.inflate(context, R.layout.normal_view, null);
                View normalEmptyView =View.inflate(context, R.layout.normal_view, null);
                TextView tvEmpty=(TextView)normalEmptyView.findViewById(R.id.textView);
                tv = (TextView)normalView.findViewById(R.id.textView);
                tv.setText(arrayWhitespace[i].toString());
                tv.setTypeface(Global.getTypefaceArabic());
                tv.setTextSize(Global.arabicFontSize);
                tv.setTextColor(Color.DKGRAY);
                tvEmpty.setText("   ");

                // mCustomR2L.removeAllViews();
                mCustomR2L.addView(normalView);
                mCustomR2L.addView(normalEmptyView);
            }*/

            mCustomR2L.addView(normalView);
            View normalImageViewAyat =View.inflate(context, R.layout.normal_image_view_ayat_no, null);
            TextView txtViewAyatNo=(TextView)normalImageViewAyat.findViewById(R.id.txtAyatNo);
            txtViewAyatNo.setText(verseArabic.getVerseId());
            mCustomR2L.addView(normalImageViewAyat);
        }

    }
    public void setHtml(){

        List<Verse> itemList= Global.getVerseList();

        String html="";


        for(int k=0;k<itemList.size();k++){
            VerseArabic verseArabic1 = (VerseArabic) itemList.get(k);
            html += verseArabic1.getVerse();

            //<span style="color:blue">blue</span>
            String number = "<span style=\"color:blue\" class=\"num\"> "+k+" </span>";

            html +=number;
        }

        WebView webView=null;// (WebView)findViewById(R.id.webview);


        String pish = "<html><head><link href=\"style/myStyle.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><p>";

        String pas = " </p></body></html>";
        Log.e("html",html);
        String myHtmlString = pish + html + pas;

        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");


        webView.loadDataWithBaseURL("file:///android_asset/", myHtmlString, "text/html", "utf-8", null);

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);


        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        // Add the interface to record javascript events


    }


    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
     //   initToolbar();
       // initRecyclerView();
//

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").


    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.

    }

    protected void getDataFromPref() {
        ArrayList<com.jolpai.tafsir.model.Verse> verseArabicList;
        verseArabicList = App.getContext().getDatabaseManager().getVersesArabic(surahNo);
        Global.setVerseList(verseArabicList);
    }

    protected void verseTransList() {
        ArrayList<com.jolpai.tafsir.model.Verse> verseVerseTransList;
        verseVerseTransList = App.getContext().getDatabaseManager().getPlainTrans(surahNo);
        Global.setVerseVerseTransList(verseVerseTransList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {

           // new ShowDialog(this).settingsDialogFromBotton();
            //new dialog().settingsDialogFromBotton();
            Intent intent = new Intent(VerseDetail_ArabicOnly.this,MySettings.class);
            startActivity(intent);
        }
    }



}
