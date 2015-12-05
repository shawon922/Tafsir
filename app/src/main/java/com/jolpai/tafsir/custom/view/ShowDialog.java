package com.jolpai.tafsir.custom.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.activity.VerseDetail;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.Settings;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tanim reja on 8/3/2015.
 */
public class ShowDialog implements View.OnClickListener{
    private static Context context;
    private View view;
    private TextView  txtFontArabicSize,
            txtSampleArabic,
            txtFontArabicPlus,
            txtFontArabicMinus;
    private SharedPreferences sharedPreferences;


    public ShowDialog(Context context){
        this.context=context;


    }

    public  void settingsDialogFromBotton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogAnimation);
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.dialog_settings, null);

        txtFontArabicPlus =(TextView)v.findViewById(R.id.txtFontArabicPlus);
        txtFontArabicMinus =(TextView)v.findViewById(R.id.txtFontArabicMinus);
        txtFontArabicSize =(TextView)v.findViewById(R.id.txtFontArabicSize);
        txtSampleArabic=(TextView)v.findViewById(R.id.txtSampleArabic);
        view =v.findViewById(R.id.txtSampleArabic);

        txtFontArabicPlus.setOnClickListener(this);
        txtFontArabicMinus.setOnClickListener(this);

        final Spinner spnrFont = (Spinner)v.findViewById(R.id.spnrFontArabic);
        List<String> list = new ArrayList<String>();
        list.add("Choose You Font");
        list.add("me_quran_volt_newmet");
        list.add("trado");
        list.add("_PDMS_Saleem_QuranFont");
        list.add("noorehira");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, list);
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
                        sharedPreferences = context.getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Settings mySettings = new Settings();
                        mySettings.setArabicFontSize(Global.arabicFontSize);
                       // editor.p
                      //  editor.putString("mySettings",mySettings);
                        editor.commit();

                        VerseDetail v = new VerseDetail();
                        v.initRecyclerView();


                    }
                })
                .setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
         builder.create().show();
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

