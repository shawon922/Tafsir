package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.activity.NavigationDrawer;
import com.jolpai.tafsir.entity.Global;
import com.jolpai.tafsir.entity.SurahName;
import com.jolpai.tafsir.entity.Trans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerSurahNameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SurahName> mItemList;
    private ArrayList<Trans> mTransList;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    public RecyclerSurahNameAdapter() {
        mItemList = Global.getSurahNameList();
        mTransList= Global.getVerseTransList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_surah_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SurahName surahName =(SurahName) v.getTag();

                    Intent intent = new Intent(parent.getContext(), NavigationDrawer.class);
                    intent.putExtra("surahNo",""+surahName.getSurahNo());
                    intent.putExtra("surahName",""+surahName.getSurahName());
                    parent.getContext().startActivity(intent);


                }
            });

            int[] androidColors = parent.getContext().getResources().getIntArray(R.array.androidcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            view.setBackgroundColor(randomAndroidColor);



            return RecyclerSurahNameItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_surah_header, parent, false);
            return new RecyclerSurahNameHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerSurahNameItemViewHolder holder = (RecyclerSurahNameItemViewHolder) viewHolder;
            SurahName surahName=mItemList.get(position-1); // we are taking header in to account so all of our items are correctly positioned

            holder.setItemText(surahName);
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