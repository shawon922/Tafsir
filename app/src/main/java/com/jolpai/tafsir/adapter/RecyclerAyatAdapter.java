package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.entity.Global;
import com.jolpai.tafsir.entity.Verse;
import com.jolpai.tafsir.entity.VerseArabic;
import com.jolpai.tafsir.entity.VerseTrans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerAyatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Verse> verseArabicList;
    private ArrayList<Verse> verseTransList;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    public RecyclerAyatAdapter(List<Verse> itemList) {
        verseArabicList = itemList;
        verseTransList = Global.getVerseVerseTransList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_ayat, parent, false);
            return RecyclerAyatItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header_ayat, parent, false);
            return new RecyclerAyatHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerAyatItemViewHolder holder = (RecyclerAyatItemViewHolder) viewHolder;

            VerseArabic verseArabic = (VerseArabic) verseArabicList.get(position - 1);
            VerseTrans verseTrans;
            if(verseArabic.getVerseId().equalsIgnoreCase("0")){
                verseTrans = new VerseTrans();
            }else if(verseArabic.getSurahNo().equalsIgnoreCase("1") ||verseArabic.getSurahNo().equalsIgnoreCase("9") ){
                verseTrans = (VerseTrans) verseTransList.get(position-1);
            }else{
                verseTrans = (VerseTrans) verseTransList.get(position-2);
            }

            holder.setItemText(verseArabic, verseTrans);
        }
    }
    //our old getItemCount()
    public int getBasicItemCount() {
        return verseArabicList == null ? 0 : verseArabicList.size();
    }

    //our new getItemCount() that includes header View
    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header
    }

    //added a method that returns viewType for a given position
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    //added a method to check if given position is a header
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}