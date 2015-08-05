package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jolpai.tafsir.R;

import java.util.List;

/**
 * Created by Tanim reja on 8/4/2015.
 */
public class VerseAdapter  extends ArrayAdapter<String> implements View.OnClickListener{

    private Context context;
    private List<String> list;

    public VerseAdapter(Context context, List<String> list) {
        super(context, R.layout.row_verse_arabic,list);

        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_verse_arabic, parent, false);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtVerseArabic);
            convertView.setOnClickListener(this);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(list != null) {

            String arabic = list.get(position);
            holder.txtTitle.setText(arabic);
            holder.txtTitle.setTextSize(25);
            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/TRADO.TTF");
            holder.txtTitle.setTypeface(tf, Typeface.NORMAL);

        }

        return convertView;

    }

    @Override
    public void onClick(View v) {


    }


    static class ViewHolder  {
        TextView txtTitle;
        String id;
        ImageView imgMenuIcon;

    }
}
