package com.jolpai.tafsir.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.jolpai.tafsir.adapter.VerseAdapter_ArabicOnly;
import com.jolpai.tafsir.custom.listener.HidingScrollListener;
import com.jolpai.tafsir.adapter.VerseAdapter;
import com.jolpai.tafsir.utility.Typefaces;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.db.DatabaseManager;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.Settings;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class VerseDetail extends ActionBarActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private ImageView settingImageView;
    private String surahNo;
    private String surahName;
    private RecyclerView recyclerView;
    Hashtable<String,Parcelable> hashtable=new Hashtable<>();

    int scrollX;

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

        if (hashtable.get("h") != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(hashtable.get("h"));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").

        // hare i need to save recycler  view scroll position..
        scrollX= recyclerView.computeVerticalScrollOffset();
        Toast.makeText(VerseDetail.this, "scrollX " + scrollX, Toast.LENGTH_SHORT).show();
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();


        hashtable.put("h", layoutManager.onSaveInstanceState());



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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        (this).setSupportActionBar(mToolbar);
        TextView txtToolbar = (TextView) findViewById(R.id.txtToolbarHeader);
        settingImageView = (ImageView) findViewById(R.id.settingsImage);
        settingImageView.setOnClickListener(this);
        txtToolbar.setText(surahName);
        txtToolbar.setTextColor(Color.WHITE);
        txtToolbar.setTypeface(Global.getTypefaceArabic());
        txtToolbar.setTextSize(25);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerseAdapter recyclerAdapter = new VerseAdapter(Global.getVerseList());
        VerseAdapter_ArabicOnly recyclerAdapter_arabic = new VerseAdapter_ArabicOnly(Global.getVerseList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setSoundEffectsEnabled(true);

       // recyclerView.scrollToPosition(50);
       // recyclerView.smoothScrollToPositionFromTop(position,offset,duration);
       // recyclerView.smoothScrollToPosition(50);
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
            Intent intent = new Intent(VerseDetail.this,MySettings.class);
            startActivity(intent);
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
            txtSampleArabic.setTypeface(Global.getTypefaceArabic());
            txtFontArabicSize.setText(String.valueOf(Global.arabicFontSize));

            txtFontArabicPlus.setOnClickListener(this);
            txtFontArabicMinus.setOnClickListener(this);

            final Spinner spnrFont = (Spinner) v.findViewById(R.id.spnrFontArabic);


            ArrayList<Settings> fontList;
            fontList = App.getContext().getDatabaseManager().getFont("arb");



            List<String> list = new ArrayList<String>();

            list.add("me_quran_volt_newmet");
            list.add("trado");
            list.add("_PDMS_Saleem_QuranFont");
            list.add("noorehira");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrFont.setAdapter(dataAdapter);
            String compareValue="trado";
            if (!compareValue.equals("trado")) {
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spnrFont.setSelection(spinnerPosition);
            }


            spnrFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        Toast.makeText(context, spnrFont.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        Global.setTypefaceArabic(Typefaces.get(VerseDetail.this, spnrFont.getSelectedItem().toString()));
                        txtSampleArabic.setTypeface(Global.getTypefaceArabic());
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
