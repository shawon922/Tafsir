package com.jolpai.tafsir.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.HidingScrollListener;
import com.jolpai.tafsir.adapter.RecyclerAdapter;
import com.jolpai.tafsir.adapter.RecyclerSurahNameAdapter;
import com.jolpai.tafsir.custom.view.ShowDialog;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.entity.Global;
import com.jolpai.tafsir.entity.Trans;

import java.util.ArrayList;
import java.util.List;

public class SurahName extends AppCompatActivity implements View.OnClickListener {





    private Toolbar mToolbar;
    private ImageView settingImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah);

        DatabaseManager dbm = new DatabaseManager(SurahName.this);
        verseTransList();//testing english trans

        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView tv = (TextView)findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView)findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        tv.setText("TAFSIR");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(25);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerSurahNameAdapter recyclerAdapter = new RecyclerSurahNameAdapter();
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
      //  mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

    }

    private void showViews() {
      //  mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }



    protected void verseTransList(){
        ArrayList<com.jolpai.tafsir.entity.SurahName> surahNameList;
        surahNameList=App.getContext().getDatabaseManager().getSurahName("en");
        Global.setSurahNameList(surahNameList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {

            new ShowDialog(this).settingsDialogFromBotton();

        }
    }























    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_surah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
