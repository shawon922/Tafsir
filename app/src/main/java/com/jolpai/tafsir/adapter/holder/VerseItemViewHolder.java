package com.jolpai.tafsir.adapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.model.VerseTrans;

/**
 * Created by Tanim reja on 12/20/2015.
 */
public class VerseItemViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textArabic;
    private TextView textTrans;
    private final View card;
    public VerseItemViewHolder(View item, Context context) {
        super(item);
        LinearLayout ll=(LinearLayout)item;

        this.context=context;
        textArabic =(TextView)ll.findViewById(R.id.textArabic);
        textTrans=(TextView)ll.findViewById(R.id.textTrans);
        card=item;
    }
    public static VerseItemViewHolder newInstance(View item,Context context) {

        return new VerseItemViewHolder(item,context);
    }
    public void setItemText(VerseArabic verseArabic,VerseTrans verseTrans){

        textArabic.setText(verseArabic.getVerse());
        textArabic.setTypeface(Global.getTypefaceArabic());
        textArabic.setTextSize(Global.arabicFontSize);
        textArabic.setTextColor(Color.DKGRAY);

        textTrans.setText(verseTrans.getVerse());
        textTrans.setTypeface(Global.getTypefaceTrans());
        textTrans.setTextSize(17);
        textTrans.setTextColor(Color.DKGRAY);
        card.setTag(verseArabic);

        /*if (Global.bookmarkedStore != null) {
            String id = Global.bookmarkedStore.getString(verseArabic.getSurahNo() + ":" + verseArabic.getVerseId(), "");
            if(id.equalsIgnoreCase(verseArabic.getVerseId())){
                textArabic.setTextColor(context.getResources().getColor(R.color.amber_500));
            }
        }*/
    }
}
