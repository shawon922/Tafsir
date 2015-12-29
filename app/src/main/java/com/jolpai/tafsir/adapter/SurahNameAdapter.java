package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.activity.VerseDetail;
import com.jolpai.tafsir.adapter.holder.SurahNameScreenHeaderViewHolder;
import com.jolpai.tafsir.adapter.holder.SurahNameItemViewHolder;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.SurahName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class SurahNameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SurahName> mItemList;
    private ArrayList<com.jolpai.tafsir.model.Verse> mVerseTransList;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private Map<String, String> bookMarkStoreSurah;

    public SurahNameAdapter(Context context,Map<String,String> mapBookMarkStoreSurah) {
        this.context=context;
        this.bookMarkStoreSurah=mapBookMarkStoreSurah;
        mItemList = Global.getSurahNameList();
        mVerseTransList = Global.getVerseVerseTransList();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.row_surah, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SurahName surahName =(SurahName) v.getTag();

                    Intent intent = new Intent(parent.getContext(), VerseDetail.class);
                    intent.putExtra("surahName", surahName);
                    parent.getContext().startActivity(intent);


                }
            });

            int[] androidColors = parent.getContext().getResources().getIntArray(R.array.androidcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            view.setBackgroundColor(randomAndroidColor);



            return SurahNameItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.header_surah, parent, false);
            return new SurahNameScreenHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            SurahNameItemViewHolder holder = (SurahNameItemViewHolder) viewHolder;
            SurahName surahName=mItemList.get(position-1); // we are taking header in to account so all of our items are correctly positioned



            holder.setItemText(surahName,bookMarkStoreSurah);
        }
    }
    //our old getItemCount()
    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
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