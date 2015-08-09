package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.R2L;

import java.util.List;

/**
 * Created by Tanim reja on 8/4/2015.
 */
public class VerseAdapter extends ArrayAdapter<String> implements View.OnClickListener {

    private Context context;
    private List<String> list;

    R2L customLayout,customLayout_other;
    LayoutInflater cInflater = null;
    Typeface tf;

    public VerseAdapter(Context context, List<String> list) {
        super(context, R.layout.row_verse_arabic, list);

        this.context = context;
        this.list = list;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/TRADO.TTF");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        cInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = cInflater.inflate(R.layout.row_verse_arabic, parent, false);

        customLayout = (R2L) convertView.findViewById(R.id.cl_R2L);

        if (list != null) {
            String arabic = list.get(position);
            String [] arrayWhitespace = arabic.split("\\s+");

            for (int i=0; i<arrayWhitespace.length; i++) {
                View normalView =View.inflate(context, R.layout.normal_view, null);
                View emptyView =View.inflate(context, R.layout.normal_empty_view, null);

                TextView tv = (TextView)normalView.findViewById(R.id.textView);
                TextView tv2 = (TextView)emptyView.findViewById(R.id.textView2);

                tv.setTypeface(tf, Typeface.NORMAL);
                tv.setTextSize(35);
                tv.setTextColor(Color.DKGRAY);
                tv.setTypeface(tf, Typeface.NORMAL);
                tv.setText(arrayWhitespace[i].toString());
                tv2.setText(" ");
                tv2.setTextSize(35);

                customLayout.addView(normalView);
                customLayout.addView(emptyView);
            }
        }
        return convertView;

    }

    private R2L ditasteString(String msg,ViewGroup parent ){

       LayoutInflater  cInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View convertView = cInflater.inflate(R.layout.row_verse_arabic, parent, false);

        R2L customLayout = (R2L) convertView.findViewById(R.id.cl_R2L);
        TextView tv = new TextView(context);

        String [] arrayWhitespace = msg.split("\\s+");
        for (int i=0; i<arrayWhitespace.length; i++) {
            tv.setText(arrayWhitespace[i].toString());
        }

        return customLayout;

    }

    @Override
    public void onClick(View v) {


    }


    static class ViewHolder {
        TextView txtTitle;
        String id;
        ImageView imgMenuIcon;

    }
}
