package com.jolpai.tafsir.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.listener.HidingScrollListener;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.model.*;
import com.jolpai.tafsir.model.SurahName;
import com.jolpai.tafsir.adapter.sticky.DividerDecoration;
import com.jolpai.tafsir.adapter.InlineStickyAdapter;
import com.jolpai.tafsir.adapter.sticky.StickyHeaderDecoration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class VerseDetail extends AppCompatActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private ImageView settingImageView;
    private com.jolpai.tafsir.model.SurahName surahName;
    Hashtable<String,Parcelable> hashtable=new Hashtable<>();
    Set<String> bookmarkSet=new HashSet<>();
    private StickyHeaderDecoration headerDecoration;
    private RecyclerView recycler;

    int scrollX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_detail);



        DatabaseManager dbm = new DatabaseManager(VerseDetail.this);

        Intent intent = getIntent();
        surahName = (SurahName)intent.getSerializableExtra("surahName");
        verseTransList();//testing english trans
        getDataFromPref();
        initToolbar();
        initStickyRecycler();


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


        if (hashtable.get("recyclerLastPosition") != null) {
            recycler.getLayoutManager().onRestoreInstanceState(hashtable.get("recyclerLastPosition"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").

        setBookmark();
        // hare i need to save recycler  view scroll position..
        scrollX= recycler.computeVerticalScrollOffset();

        RecyclerView.LayoutManager layoutManager=recycler.getLayoutManager();
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

    public void setBookmark(){
        int verseNo = Integer.parseInt(surahName.getVerseNo());
        boolean hasBookmarked=false;
        for (int i = 0; i < verseNo; i++) {
            String key = surahName.getSurahNo() + ":" + i;
            String value = Global.bookmarkedStore.getString(key, null);

            if (value != null) {
                // that means this surah has atleast one bookmarked ayah so record it in bookmarkSet.
                hasBookmarked=true;
                bookmarkSet = Global.bookmarkedStore.getStringSet("bookmarkSet", null);
                if (bookmarkSet != null) {
                    if (!bookmarkSet.contains(surahName.getSurahNo())) {
                        bookmarkSet.add(surahName.getSurahNo());

                        SharedPreferences.Editor editor = Global.bookmarkedStore.edit();
                        editor.putStringSet("bookmarkSet", bookmarkSet);
                        editor.commit();
                    }
                    break;
                }else {
                    bookmarkSet=new HashSet<>();
                    SharedPreferences.Editor editor = Global.bookmarkedStore.edit();
                    editor.putStringSet("bookmarkSet", bookmarkSet);
                    editor.commit();
                     /*
                     call setBookmark() recursively to ensure that bookmarkSet is notified about any change may be this will happed
                     only first time
                    */
                    setBookmark();
                }
            }
        }
        if( ! hasBookmarked){
            /*  that means this surah has not any bookmarked ayah so remove data from bookmarkSet if it contains
                any previous bookmarked data about this surahName.
            */
            bookmarkSet = Global.bookmarkedStore.getStringSet("bookmarkSet", null);
            if (bookmarkSet != null) {
                if (bookmarkSet.contains(surahName.getSurahNo())) {
                    bookmarkSet.remove(surahName.getSurahNo());

                    SharedPreferences.Editor editor = Global.bookmarkedStore.edit();
                    editor.putStringSet("bookmarkSet", bookmarkSet);
                    editor.commit();
                }
            }else {
                bookmarkSet=new HashSet<>();
                SharedPreferences.Editor editor = Global.bookmarkedStore.edit();
                editor.putStringSet("bookmarkSet", bookmarkSet);
                editor.commit();
                /*
                call setBookmark() recursively to ensure that bookmarkSet is notified about any change may be this will happed
                only first time
                */
                setBookmark();
            }
        }

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView txtToolbar = (TextView) findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView) findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        txtToolbar.setText(surahName.getSurahName());
        txtToolbar.setTextColor(Color.WHITE);
        txtToolbar.setTypeface(Global.getTypefaceArabic());
        txtToolbar.setTextSize(25);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }


    public void initStickyRecycler(){


        // recyclerView.scrollToPosition(50);
        // recyclerView.smoothScrollToPositionFromTop(position,offset,duration);
        // recyclerView.smoothScrollToPosition(50);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        final InlineStickyAdapter adapter = new InlineStickyAdapter(this,
                Global.getVerseList(),
                Global.getVerseVerseTransList(),
                mToolbar);

        headerDecoration = new StickyHeaderDecoration(adapter, true);
        // headerDecoration.
        recycler.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                headerDecoration.invalidateHeaders();
            }


        });
        final DividerDecoration divider =new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height_zero)
                .setPadding(R.dimen.default_divider_padding_zero)
                .setColorResource(R.color.transparent)
                .build();

        recycler.addItemDecoration(divider);

        recycler.addItemDecoration(headerDecoration, 1);

        recycler.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
        recycler.invalidate();


    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    protected void getDataFromPref() {
        ArrayList<Verse> verseArabicList;
        verseArabicList = App.getContext().getDatabaseManager().getVersesArabic(surahName.getSurahNo());
        Global.setVerseList(verseArabicList);
    }

    protected void verseTransList() {
        ArrayList<Verse> verseVerseTransList;
        verseVerseTransList = App.getContext().getDatabaseManager().getPlainTrans(surahName.getSurahNo());
        Global.setVerseVerseTransList(verseVerseTransList);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {
            Intent intent = new Intent(VerseDetail.this,MySettings.class);
            startActivity(intent);
        }
    }
}
