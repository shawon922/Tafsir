package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.R2L;

import org.w3c.dom.Text;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
   // private final TextView mItemTextView;
    Context context;
    private final R2L  mCustomLayout;
    private static Typeface tf ;

    public RecyclerItemViewHolder(final View parent, R2L r2l) {
        super(parent);
        mCustomLayout = r2l;
        context=parent.getContext();
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        tf=Typeface.createFromAsset(parent.getContext().getAssets(),"fonts/TRADO.TTF");
        R2L customLayout = (R2L) parent.findViewById(R.id.cl_R2L);

        return new RecyclerItemViewHolder(parent, customLayout);
    }

    public void setItemText(CharSequence text) {
        View normalView =View.inflate(context, R.layout.normal_view, null);
        TextView tv = (TextView)normalView.findViewById(R.id.textView);
        tv.setText(text);
        tv.setTypeface(tf);
        tv.setTextSize(35);
        tv.setTextColor(Color.DKGRAY);
        mCustomLayout.removeAllViews();
        mCustomLayout.addView(normalView);
    }
}
