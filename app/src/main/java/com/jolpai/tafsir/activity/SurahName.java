package com.jolpai.tafsir.activity;

import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.listener.HidingScrollListener;
import com.jolpai.tafsir.adapter.SurahNameAdapter;
import com.jolpai.tafsir.adapter.SurahNameItemViewHolder;
import com.jolpai.tafsir.utility.Typefaces;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.model.Global;

import java.util.ArrayList;
import java.util.Random;

public class SurahName extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView settingImageView;
    MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah_name);
        Global.setTypefaceArabic(Typefaces.get(SurahName.this, Global.selectedEngFontName));

        DatabaseManager dbm = new DatabaseManager(SurahName.this);
        verseTransList();//testing english trans



    }

    @Override
    protected void onResume() {
        super.onResume();
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
        tv.setTypeface(Global.getTypefaceArabic());
        tv.setTextSize(25);
        tv.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        int[] androidColors = SurahName.this.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        mToolbar.setBackgroundColor(getResources().getColor(R.color.gray_500));
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SurahNameAdapter recyclerAdapter = new SurahNameAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        //setting up our OnScrollListener
        recyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews(recyclerView);
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurahNameItemViewHolder ss = (SurahNameItemViewHolder) recyclerView.getChildViewHolder(v);

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
        surahNameList=App.getContext().getDatabaseManager().getSurahName("en");
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
