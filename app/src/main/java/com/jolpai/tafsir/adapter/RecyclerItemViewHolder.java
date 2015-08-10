package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jolpai.tafsir.R;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView mItemTextView;
    Context context;
private static Typeface tf ;


    public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
        super(parent);
        mItemTextView = itemTextView;

    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        tf=Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/TRADO.TTF");
        return new RecyclerItemViewHolder(parent, itemTextView);
    }

    public void setItemText(CharSequence text) {
        mItemTextView.setText(text);
        mItemTextView.setTypeface(tf);
        mItemTextView.setTextSize(35);
    }

}
