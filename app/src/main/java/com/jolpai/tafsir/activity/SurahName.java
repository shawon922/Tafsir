package com.jolpai.tafsir.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.listener.HidingScrollListener;
import com.jolpai.tafsir.adapter.SurahNameAdapter;
import com.jolpai.tafsir.adapter.holder.SurahNameItemViewHolder;
import com.jolpai.tafsir.utility.Typefaces;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.model.Global;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class SurahName extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView settingImageView;
    MediaPlayer mediaPlayer = new MediaPlayer();
    private RecyclerView recycler;
    Hashtable<String,Parcelable> hashtable=new Hashtable<>();
    Map<String, String> bookMarkStoreSurah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah_name);
        Global.setTypefaceArabic(Typefaces.get(SurahName.this, Global.selectedEngFontName));
        bookMarkStoreSurah=new Hashtable<>();
        Global.bookmarkedStore=getSharedPreferences("bookmarkedStore", Context.MODE_PRIVATE);
        Global.bookMarkedStoreSurah=getSharedPreferences("bookMarkedStoreSurah", Context.MODE_PRIVATE);

       // new DatabaseManager(SurahName.this);
        verseTransList();//testing english trans

    }
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToolbar();
        initRecyclerView();



        if (hashtable.get("recyclerLastPosition") != null) {
            recycler.getLayoutManager().onRestoreInstanceState(hashtable.get("recyclerLastPosition"));
        }

    }
    @Override
    protected void onPause(){
        super.onPause();

        RecyclerView.LayoutManager layoutManager= recycler.getLayoutManager();
        hashtable.put("recyclerLastPosition", layoutManager.onSaveInstanceState());

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

    private void initToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView tv = (TextView)findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView)findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        tv.setText("TAFSIR");
        tv.setTypeface(Global.getTypefaceArabic());
        tv.setTextSize(25);
        tv.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        int[] androidColors = SurahName.this.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        mToolbar.setBackgroundColor(getResources().getColor(R.color.gray_500));
    }

    private void initRecyclerView() {
        recycler = (RecyclerView)findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        SurahNameAdapter adapter = new SurahNameAdapter(SurahName.this,bookMarkStoreSurah);
        recycler.setAdapter(adapter);
        //setting up our OnScrollListener
        recycler.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews(recycler);
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurahNameItemViewHolder ss = (SurahNameItemViewHolder) recycler.getChildViewHolder(v);

            }
        });
    }

    private void hideViews(final RecyclerView recyclerView) {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));


    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }



    protected void verseTransList(){
        ArrayList<com.jolpai.tafsir.model.SurahName> surahNameList;
        surahNameList=Global.db.getSurahName("en");
        Global.setSurahNameList(surahNameList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {

            //new ShowDialog(this).settingsDialogFromBotton();
            Intent intent = new Intent(SurahName.this,MySettings.class);
            startActivity(intent);
        }
    }

}
