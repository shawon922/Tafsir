package com.jolpai.tafsir.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.db.MyDB;
import com.jolpai.tafsir.model.GLOBAL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {


    TextView txt;
    FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        GLOBAL.db=MyDB.getInstance(Splash.this);

        mainLayout=(FrameLayout)findViewById(R.id.main_layout);

        txt=(TextView)findViewById(R.id.fullscreen_content);

        txt.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setVisibility(View.VISIBLE);
                txt.setText("T");
               // mainLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Ta");
                       // mainLayout.setBackgroundColor(Color.parseColor("#3F51B5"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txt.setVisibility(View.VISIBLE);
                                txt.setText("TaF");
                              //  mainLayout.setBackgroundColor(Color.parseColor("#673AB7"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt.setVisibility(View.VISIBLE);
                                        txt.setText("TaFs");
                                       // mainLayout.setBackgroundColor(Color.parseColor("#CDDC39"));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txt.setVisibility(View.VISIBLE);
                                                txt.setText("TaFsI");
                                             //   mainLayout.setBackgroundColor(Color.parseColor("#8BC34A"));
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        txt.setVisibility(View.VISIBLE);
                                                        txt.setText("TaFsIr");
                                                    //    mainLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
                                                        openWindow();

                                                    }
                                                }, 150);

                                            }
                                        }, 150);

                                    }
                                }, 150);

                            }
                        }, 150);

                    }
                }, 150);

                //openWindow();

            }
        }, 150);
    }


    public void openWindow(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(Splash.this, SurahName.class);
                startActivity(mainIntent);
                finish();

            }
        },150);



    }


}
