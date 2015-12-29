package com.jolpai.tafsir.adapter.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tanim reja on 12/15/2015.
 */
public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {

    /**
     *
     */
    long getHeaderId(int position);
    /**
     *
     */
    T onCreateHeaderViewHolder(ViewGroup parent);

    /**
     *
     */
    void onBindHeaderViewHolder(T viewHolder, int position,View view);


}
