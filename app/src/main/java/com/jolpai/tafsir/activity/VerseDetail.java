package com.jolpai.tafsir.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.HidingScrollListener;
import com.jolpai.tafsir.adapter.RecyclerAyatAdapter;
import com.jolpai.tafsir.custom.view.ShowDialog;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.entity.Global;

import java.util.ArrayList;

public class VerseDetail extends ActionBarActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private ImageView settingImageView;
    private String surahNo;
    private String surahName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_detail);

       DatabaseManager dbm = new DatabaseManager(VerseDetail.this);


        Intent intent = getIntent();
        surahNo= intent.getStringExtra("surahNo");
        surahName= intent.getStringExtra("surahName");
        verseTransList();//testing english trans
        getDataFromPref();



    }


    private void initToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView tv = (TextView)findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView)findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        tv.setText(surahName);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(Global.getTypefaceArabic());
        tv.setTextSize(25);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAyatAdapter recyclerAdapter = new RecyclerAyatAdapter(Global.getVerseList());
        recyclerView.setAdapter(recyclerAdapter);
        //setting up our OnScrollListener
        recyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

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
        initToolbar();
        initRecyclerView();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    protected void getDataFromPref(){
        ArrayList<com.jolpai.tafsir.entity.Verse> verseArabicList ;
        verseArabicList=App.getContext().getDatabaseManager().getVersesArabic(surahNo);
        Global.setVerseList(verseArabicList);
    }

    protected void verseTransList(){
        ArrayList<com.jolpai.tafsir.entity.Verse> verseVerseTransList;
        verseVerseTransList =App.getContext().getDatabaseManager().getPlainTrans(surahNo);
        Global.setVerseVerseTransList(verseVerseTransList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {

            new ShowDialog(this).settingsDialogFromBotton();

        }
    }
}
