package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.entity.SurahName;

import java.util.Random;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerSurahNameItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
   // private final TextView mItemTextView;
    Context context;
    private final TextView txtSurahName,txtSurahNo,txtAyahNo;
    private  static Typeface tf ;
    private  final  View card;

    public RecyclerSurahNameItemViewHolder(final View parent, TextView txtSurahName) {
        super(parent);
        this.card=parent;
        this.txtSurahName=txtSurahName;
        this.txtSurahNo = (TextView)parent.findViewById(R.id.txtSurahNo);
        this.txtAyahNo=(TextView)parent.findViewById(R.id.txtAyahNo);
        context=parent.getContext();
    }

    public static RecyclerSurahNameItemViewHolder newInstance(View parent) {

        tf=Typeface.createFromAsset(parent.getContext().getAssets(),"fonts/TRADO.TTF");
        TextView txtSurahName = (TextView) parent.findViewById(R.id.txtSurahName);


        return new RecyclerSurahNameItemViewHolder(parent,txtSurahName);
    }

    public void setItemText(SurahName surahName) {
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        int brown_500=context.getResources().getColor(R.color.brown_500);
        card.setBackgroundColor(randomAndroidColor);
        card.setTag(surahName);

        txtSurahNo.setText(surahName.getSurahNo());
        txtSurahNo.setTypeface(tf);
        txtSurahNo.setTextSize(20);
        txtSurahNo.setTextColor(Color.DKGRAY);

        txtSurahName.setText(surahName.getSurahName());
        txtSurahName.setTypeface(tf);
        txtSurahName.setTextSize(23);
        txtSurahName.setTextColor(randomAndroidColor);

        txtAyahNo.setText(surahName.getVerseNo());
        txtAyahNo.setTypeface(tf);
        txtAyahNo.setTextSize(15);
        txtAyahNo.setTextColor(brown_500);





    }

    @Override
    public void onClick(View v) {
     //   Toast.makeText(context,""+getPosition(),Toast.LENGTH_LONG).show();
        SurahName surahName =(SurahName) v.getTag();
        Toast.makeText(context,""+surahName.getSurahName(),Toast.LENGTH_LONG).show();
    }
}
