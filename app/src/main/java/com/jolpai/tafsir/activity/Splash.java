package com.jolpai.tafsir.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.model.Global;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {


   TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        txt=(TextView)findViewById(R.id.fullscreen_content);
        txt.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                txt.setVisibility(View.VISIBLE);
               // txt.setTypeface(Global.getTypefaceTrans());
                openWindow();

            }
        }, 300);
    }


    public void openWindow(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(Splash.this, SurahName.class);
                startActivity(mainIntent);
                finish();

            }
        },100);



    }


}
