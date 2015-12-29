package com.jolpai.tafsir.adapter.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.utility.Typefaces;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.SurahName;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class SurahNameItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
   // private final TextView mItemTextView;
    Context context;
    private final TextView txtSurahName,txtSurahNo,txtAyahNo;
    private static Typeface tf ;
    private final  View card;
    private final ImageView imgBookMark;

    public SurahNameItemViewHolder(final View parent, TextView txtSurahName) {
        super(parent);
        this.card=parent;
        this.txtSurahName=txtSurahName;
        this.txtSurahNo = (TextView)parent.findViewById(R.id.txtSurahNo);
        this.txtAyahNo=(TextView)parent.findViewById(R.id.txtAyahNo);
        this.imgBookMark=(ImageView)parent.findViewById(R.id.imgBookmark);
        context=parent.getContext();
        Global.bookmarkedStore=context.getSharedPreferences("bookmarkedStore", Context.MODE_PRIVATE);
    }

    public static SurahNameItemViewHolder newInstance(View parent) {

        Global.setTypefaceTrans(Typefaces.get(parent.getContext(), Global.selectedEngFontName));
        TextView txtSurahName = (TextView) parent.findViewById(R.id.txtSurahName);

        return new SurahNameItemViewHolder(parent,txtSurahName);
    }

    public void setItemText(SurahName surahName,Map<String,String> bookMarkStoreSurah) {
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        int brown_500=context.getResources().getColor(R.color.brown_500);
        card.setBackgroundColor(randomAndroidColor);
        card.setTag(surahName);

        txtSurahNo.setText(surahName.getSurahNo());
        txtSurahNo.setTypeface(Global.getTypefaceTrans());
        txtSurahNo.setTextSize(20);
        txtSurahNo.setTextColor(Color.DKGRAY);

        txtSurahName.setText(surahName.getSurahName());
        txtSurahName.setTypeface(Global.getTypefaceTrans());
        txtSurahName.setTextSize(23);
        txtSurahName.setTextColor(randomAndroidColor);

        txtAyahNo.setText(surahName.getVerseNo());
        txtAyahNo.setTypeface(Global.getTypefaceTrans());
        txtAyahNo.setTextSize(15);
        txtAyahNo.setTextColor(brown_500);

        checkBookmark(surahName);

    }

    public void checkBookmark(SurahName surahName){
        if (Global.bookmarkedStore != null) {
            Set<String> bookmarkSet = null;

            bookmarkSet = Global.bookmarkedStore.getStringSet("bookmarkSet", null);
            if(bookmarkSet != null){
                if (bookmarkSet.contains(surahName.getSurahNo())) {
                    imgBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.bookmark_50));
                } else {
                    imgBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.bookmark_empty_50));
                }
            }else{

                SharedPreferences.Editor editor = Global.bookmarkedStore.edit();
                bookmarkSet=new HashSet<>();
                editor.putStringSet("bookmarkSet", bookmarkSet);
                editor.commit();
                checkBookmark(surahName);
            }

        }else{
            Global.bookmarkedStore=context.getSharedPreferences("bookmarkedStore", Context.MODE_PRIVATE);
            checkBookmark(surahName);
        }
    }

    @Override
    public void onClick(View v) {
     //   Toast.makeText(context,""+getPosition(),Toast.LENGTH_LONG).show();
        SurahName surahName =(SurahName) v.getTag();
        Toast.makeText(context,""+surahName.getSurahName(),Toast.LENGTH_LONG).show();
    }
}
