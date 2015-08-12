package com.jolpai.tafsir.custom.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jolpai.tafsir.R;


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
        View v = inflater.inflate(R.layout.dialog_certificate_statement_request, null);


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