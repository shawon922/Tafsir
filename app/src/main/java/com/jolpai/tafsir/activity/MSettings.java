package com.jolpai.tafsir.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.model.AppSettings;
import com.jolpai.tafsir.model.GLOBAL;
import com.jolpai.tafsir.utility.Fontface;
import com.jolpai.tafsir.utility.Utility;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MSettings extends Activity implements View.OnClickListener {

    private static Context context;
    private View view;
    private TextView txtArabicFontSize,
            txtSampleArabic,
            txtFontArabicPlus,
            txtFontArabicMinus,
            txtSampleSecondary,
            txtSecondaryFontSize,
            txtSecondaryFontPlus,
            txtSecondaryFontMinus;
    private Button btnDiscart,
                   btnSave;
    private Spinner spnrArabicFont,
    spnrSecondaryLang,
    spnrTranslator,
    spnrSecondaryFont;

    private Switch switchTranslation;
    private Set<String> settingSet=new HashSet<>();
    private SharedPreferences mySettings;
    private AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_settings);

        context=MSettings.this;
        txtFontArabicPlus =(TextView)findViewById(R.id.txtFontArabicPlus);
        txtFontArabicMinus =(TextView)findViewById(R.id.txtFontArabicMinus);
        txtArabicFontSize =(TextView)findViewById(R.id.txtFontArabicSize);
        txtSampleArabic=(TextView)findViewById(R.id.txtSampleArabic);

        txtSampleSecondary=(TextView)findViewById(R.id.txtSampleSecondary);
        txtSecondaryFontSize=(TextView)findViewById(R.id.txtSecondaryFontSize);
        txtSecondaryFontPlus=(TextView)findViewById(R.id.txtSecondaryFontPlus);
        txtSecondaryFontMinus=(TextView)findViewById(R.id.txtSecondaryFontMinus);

        view =findViewById(R.id.txtSampleArabic);
        btnDiscart=(Button)findViewById(R.id.btnDiscard);
        btnSave=(Button)findViewById(R.id.btnSave);
        switchTranslation=(Switch)findViewById(R.id.switchTranslation);

        txtFontArabicPlus.setOnClickListener(this);
        txtFontArabicMinus.setOnClickListener(this);

        txtSecondaryFontPlus.setOnClickListener(this);
        txtSecondaryFontMinus.setOnClickListener(this);

        btnSave.setOnClickListener(this);
        btnDiscart.setOnClickListener(this);

        spnrArabicFont = (Spinner)findViewById(R.id.spnrFontArabic);
        spnrSecondaryLang = (Spinner)findViewById(R.id.spnrSecondaryLang);
        spnrTranslator = (Spinner)findViewById(R.id.spnrTranslator);
        spnrSecondaryFont = (Spinner)findViewById(R.id.spnrSecondaryFont);

        mySettings = context.getSharedPreferences("MySettings",MODE_PRIVATE);

        appSettings= Utility.getAppSettings(context);

        setArabicFont();
        setTranslation();
        setSecondaryFont();




    }

    public void setArabicFont(){
        List<String> list = GLOBAL.db.getArabicFontList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrArabicFont.setAdapter(dataAdapter);
        txtSampleArabic.setTypeface(appSettings.getArabicFont());
        txtArabicFontSize.setText(String.valueOf(appSettings.getArabicFontSize()));

        spnrArabicFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(context, spnrArabicFont.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    txtSampleArabic.setTypeface(Fontface.get(context, spnrArabicFont.getSelectedItem().toString()));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mySettings != null){
            String arabicFont= mySettings.getString("arabicFont",null);
            if(arabicFont != null){
                int position =list.indexOf(arabicFont);
                spnrArabicFont.setSelection(position);
            }
        }

    }

    public void setTranslation(){
        List<String> secondaryLanglist = GLOBAL.db.getSecondaryLangList();
        List<String> translatorList=GLOBAL.db.getTranslatorList();

        switchTranslation.setChecked(appSettings.getTranslation());


        ArrayAdapter<String> adapterSecondaryLang = new ArrayAdapter(context,android.R.layout.simple_spinner_item, secondaryLanglist);
        adapterSecondaryLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSecondaryLang.setAdapter(adapterSecondaryLang);

        spnrSecondaryLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(context, spnrSecondaryLang.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mySettings != null){
            String secondaryLang= mySettings.getString("secondaryLang",null);
            if(secondaryLang != null){
                int position =secondaryLanglist.indexOf(secondaryLang);
                spnrSecondaryLang.setSelection(position);
            }
        }

        ArrayAdapter<String> adapterTranslator = new ArrayAdapter(context,android.R.layout.simple_spinner_item, translatorList);
        adapterTranslator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTranslator.setAdapter(adapterTranslator);

        spnrTranslator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(context, spnrTranslator.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mySettings != null){
            String translator= mySettings.getString("translator",null);
            if(translator != null){
                int position =translatorList.indexOf(translator);
                spnrTranslator.setSelection(position);
            }
        }

    }
    public  void setSecondaryFont(){
        List<String> list = GLOBAL.db.getSecondaryFontList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSecondaryFont.setAdapter(dataAdapter);
        txtSecondaryFontSize.setText(String.valueOf(appSettings.getSecondaryFontSize()));

        spnrSecondaryFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Toast.makeText(context, spnrSecondaryFont.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    //GLOBAL.setTypefaceArabic(Fontface.get(context, spnrSecondaryFont.getSelectedItem().toString()));
                   // txtSampleArabic.setTypeface(GLOBAL.getTypefaceArabic());

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mySettings != null){
            String arabicFont= mySettings.getString("secondaryFont",null);
            if(arabicFont != null){
                int position =list.indexOf(arabicFont);
                spnrSecondaryFont.setSelection(position);
            }
        }
    }

    public void increaseArabicFontSize(){
        //GLOBAL.arabicFontSize +=1;
        int size =appSettings.getArabicFontSize();
        appSettings.setArabicFontSize(size += 1);


        txtArabicFontSize.setText(String.valueOf(size));
        txtSampleArabic.setTextSize(size);
    }

    public void decreaseArabicFontSize(){
      //  GLOBAL.arabicFontSize -=1;
        int size =appSettings.getArabicFontSize();
        appSettings.setArabicFontSize(size -= 1);

        txtArabicFontSize.setText(String.valueOf(size));
        txtSampleArabic.setTextSize(size);
    }

    public void increaseSecondaryFontSize(){
        //GLOBAL.arabicFontSize +=1;
        int size =appSettings.getSecondaryFontSize();
        appSettings.setSecondaryFontSize(size += 1);


        txtSampleSecondary.setTextSize(size);
        txtSecondaryFontSize.setText(String.valueOf(size));
    }

    public void decreaseSecondaryFontSize(){
        //  GLOBAL.arabicFontSize -=1;
        int size =appSettings.getSecondaryFontSize();
        appSettings.setSecondaryFontSize(size -= 1);

        txtSampleSecondary.setTextSize(size);
        txtSecondaryFontSize.setText(String.valueOf(size));
    }
    public void btnSaveClick(){

        if(mySettings != null){
            SharedPreferences.Editor editor = mySettings.edit();
            editor.putString("arabicFont",spnrArabicFont.getSelectedItem().toString());
            editor.putString("arabicFontSize", txtArabicFontSize.getText().toString());

            editor.putBoolean("translation", switchTranslation.isChecked());
            editor.putString("secondaryLang", spnrSecondaryLang.getSelectedItem().toString());
            editor.putString("translator", spnrTranslator.getSelectedItem().toString());
            editor.putString("secondaryFont", spnrSecondaryFont.getSelectedItem().toString());
            editor.putString("secondaryFontSize",txtSecondaryFontSize.getText().toString());


            editor.commit();

        }
        finish();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtFontArabicPlus:
                increaseArabicFontSize();
                break;
            case R.id.txtFontArabicMinus:
                decreaseArabicFontSize();
                break;
            case R.id.txtSecondaryFontPlus:
                increaseSecondaryFontSize();
                break;
            case R.id.txtSecondaryFontMinus:
                decreaseSecondaryFontSize();
                break;
            case R.id.btnSave:
                btnSaveClick();
                break;
            case R.id.btnDiscard:
                finish();
                break;



            default:
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
