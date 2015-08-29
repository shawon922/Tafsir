package com.jolpai.tafsir.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.HidingScrollListener;
import com.jolpai.tafsir.adapter.RecyclerAyatAdapter;
import com.jolpai.tafsir.custom.view.ShowDialog;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.entity.Global;
import com.jolpai.tafsir.entity.VerseTrans;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends ActionBarActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private ImageView settingImageView;
    private String surahNo;
    private String surahName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

       DatabaseManager dbm = new DatabaseManager(NavigationDrawer.this);


        Intent intent = getIntent();
        surahNo= intent.getStringExtra("surahNo");
        surahName= intent.getStringExtra("surahName");
        verseTransList();//testing english trans
        getDataFromPref();
        initToolbar();
        initRecyclerView();


    }


    private void initToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView tv = (TextView)findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView)findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        tv.setText(surahName);
        tv.setTextColor(Color.WHITE);
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
    protected void onResume() {
        super.onResume();

    }

    protected void getDataFromPref(){
        ArrayList<String> name ;
        SharedPreferences prefs = this.getSharedPreferences("myKey", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        if(prefs.contains("versesT")){
            String json = prefs.getString("verses", null);
            name = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        }else{
            name=App.getContext().getDatabaseManager().getVersesArabic(surahNo);
            String jsonString = gson.toJson(name);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("verses",jsonString);
            editor.commit();
        }
        Global.setVerseList(name);

    }

    protected void verseTransList(){
        ArrayList<VerseTrans> verseVerseTransList;
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
