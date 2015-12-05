package com.jolpai.tafsir.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.db.App;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.utility.Typefaces;

import java.util.ArrayList;
import java.util.List;

public class MySettings extends Activity implements View.OnClickListener {

    private static Context context;
    private View view;
    private TextView txtFontArabicSize,
            txtSampleArabic,
            txtFontArabicPlus,
            txtFontArabicMinus;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_settings);


        context=MySettings.this;
        txtFontArabicPlus =(TextView)findViewById(R.id.txtFontArabicPlus);
        txtFontArabicMinus =(TextView)findViewById(R.id.txtFontArabicMinus);
        txtFontArabicSize =(TextView)findViewById(R.id.txtFontArabicSize);
        txtSampleArabic=(TextView)findViewById(R.id.txtSampleArabic);
        view =findViewById(R.id.txtSampleArabic);

        txtFontArabicPlus.setOnClickListener(this);
        txtFontArabicMinus.setOnClickListener(this);

        final Spinner spnrFont = (Spinner)findViewById(R.id.spnrFontArabic);
        List<String> list = App.getContext().getDatabaseManager().getArabicFontList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrFont.setAdapter(dataAdapter);

        spnrFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(context, spnrFont.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Global.setTypefaceArabic(Typefaces.get(context, spnrFont.getSelectedItem().toString()));
                    txtSampleArabic.setTypeface(Global.getTypefaceArabic());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void increaseFontSize(){
        Global.arabicFontSize +=1;
        txtFontArabicSize.setText(String.valueOf(Global.arabicFontSize));
        txtSampleArabic.setTextSize(Global.arabicFontSize);
    }

    public void decreaseFontSize(){
        Global.arabicFontSize -=1;
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
