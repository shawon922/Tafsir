package com.jolpai.tafsir.custom.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.entity.Global;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tanim reja on 8/3/2015.
 */
public class ShowDialog {
    private static Context context;

    public ShowDialog(Context context){
        this.context=context;


    }

    public static void settingsDialogFromBotton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogAnimation);
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_settings, null);

        final Spinner spnrFont = (Spinner)v.findViewById(R.id.spnrFont);
        List<String> list = new ArrayList<String>();
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
                Toast.makeText(context,spnrFont.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                Global.selectedArabicFontName=spnrFont.getSelectedItem().toString();

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


                    }
                })
                .setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
         builder.create().show();
    }


}