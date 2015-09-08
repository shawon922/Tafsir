package com.jolpai.tafsir.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.adapter.HidingScrollListener;
import com.jolpai.tafsir.adapter.RecyclerAyatAdapter;
import com.jolpai.tafsir.custom.view.ShowDialog;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.entity.Global;
import com.jolpai.tafsir.entity.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        surahNo = intent.getStringExtra("surahNo");
        surahName = intent.getStringExtra("surahName");
        verseTransList();//testing english trans
        getDataFromPref();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView tv = (TextView) findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView) findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        tv.setText(surahName);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(Global.getTypefaceArabic());
        tv.setTextSize(25);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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

    protected void getDataFromPref() {
        ArrayList<com.jolpai.tafsir.entity.Verse> verseArabicList;
        verseArabicList = App.getContext().getDatabaseManager().getVersesArabic(surahNo);
        Global.setVerseList(verseArabicList);
    }

    protected void verseTransList() {
        ArrayList<com.jolpai.tafsir.entity.Verse> verseVerseTransList;
        verseVerseTransList = App.getContext().getDatabaseManager().getPlainTrans(surahNo);
        Global.setVerseVerseTransList(verseVerseTransList);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == settingImageView.getId()) {

           // new ShowDialog(this).settingsDialogFromBotton();
            new dialog().settingsDialogFromBotton();
        }
    }


    private class dialog implements View.OnClickListener {

        private Context context =VerseDetail.this;
        private View view;
        private TextView txtFontArabicSize,
                txtSampleArabic,
                txtFontArabicPlus,
                txtFontArabicMinus;

        public void settingsDialogFromBotton() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogAnimation);
            // Get the layout inflater
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.dialog_settings, null);

            txtFontArabicPlus = (TextView) v.findViewById(R.id.txtFontArabicPlus);
            txtFontArabicMinus = (TextView) v.findViewById(R.id.txtFontArabicMinus);
            txtFontArabicSize = (TextView) v.findViewById(R.id.txtFontArabicSize);
            txtSampleArabic = (TextView) v.findViewById(R.id.txtSampleArabic);
            view = v.findViewById(R.id.txtSampleArabic);

            txtSampleArabic.setTextSize(Global.arabicFontSize);
            txtFontArabicSize.setText(String .valueOf(Global.arabicFontSize));

            txtFontArabicPlus.setOnClickListener(this);
            txtFontArabicMinus.setOnClickListener(this);

            final Spinner spnrFont = (Spinner) v.findViewById(R.id.spnrFontArabic);

            ArrayList<Settings> fontList;
            fontList = App.getContext().getDatabaseManager().getFont("arb");
            


            List<String> list = new ArrayList<String>();
            list.add("Choose Arabic Font");
            list.add("me_quran_volt_newmet");
            list.add("trado");
            list.add("_PDMS_Saleem_QuranFont");
            list.add("noorehira");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrFont.setAdapter(dataAdapter);

            spnrFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        Toast.makeText(context, spnrFont.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        Global.selectedArabicFontName = spnrFont.getSelectedItem().toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                            // (VerseDetail)context.onResume();

                            initToolbar();
                            initRecyclerView();


                        }
                    })
                    .setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
        }


        public void increaseFontSize() {
            Global.arabicFontSize += 1;
            txtFontArabicSize.setText(String.valueOf(Global.arabicFontSize));
            txtSampleArabic.setTextSize(Global.arabicFontSize);
        }

        public void decreaseFontSize() {
            Global.arabicFontSize -= 1;
            txtFontArabicSize.setText(String.valueOf(Global.arabicFontSize));
            txtSampleArabic.setTextSize(Global.arabicFontSize);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtFontArabicPlus:
                    increaseFontSize();
                    break;
                case R.id.txtFontArabicMinus:
                    decreaseFontSize();
                    break;


                default:
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
