package com.jolpai.tafsir.adapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.model.GLOBAL;
import com.jolpai.tafsir.model.VerseArabic;

/**
 * Created by Tanim reja on 12/20/2015.
 */
public class VerseItemHeaderViewHolder extends RecyclerView.ViewHolder  {

    public TextView header;
    private final View cardIcon;
    private Context context;
    public VerseItemHeaderViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        LinearLayout ll=(LinearLayout)itemView;
        header=(TextView)ll.findViewById(R.id.txtAyatNo);
        cardIcon=itemView;
    }

    public static VerseItemHeaderViewHolder newInstance(View header,Context context) {

        return new VerseItemHeaderViewHolder(header,context);
    }

    public void setHeaderText(VerseArabic verseArabic) {

        header.setText(verseArabic.getVerseId());

        if (GLOBAL.bookmarkedStore != null) {
            String id = GLOBAL.bookmarkedStore.getString(verseArabic.getSurahNo() + ":" + verseArabic.getVerseId(), "");

            if (id.equalsIgnoreCase(verseArabic.getVerseId())) {
                Toast.makeText(context, "Yellow :" + id, Toast.LENGTH_SHORT).show();
                header.setBackground(null);
                header.setBackground(context.getResources().getDrawable(R.drawable.ayat_number_yellow));
                header.setTextColor(context.getResources().getColor(R.color.white));
            }else{
             //   Toast.makeText(context, "Gray :" + id, Toast.LENGTH_SHORT).show();
                header.setBackground(null);
                header.setBackground(context.getResources().getDrawable(R.drawable.ayat_number_gray));
                header.setTextColor(Color.DKGRAY);
            }
        }
    }
}
