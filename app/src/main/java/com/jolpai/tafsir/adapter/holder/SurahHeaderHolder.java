package com.jolpai.tafsir.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tanim reja on 12/20/2015.
 */
public class SurahHeaderHolder extends RecyclerView.ViewHolder {

    public  SurahHeaderHolder(View surahHeaderView){
        super(surahHeaderView);
    }

    public static SurahHeaderHolder newInstance(View surahHeaderView) {
        return new SurahHeaderHolder(surahHeaderView);
    }
}
