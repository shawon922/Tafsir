package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.L2R;
import com.jolpai.tafsir.custom.view.R2L;
import com.jolpai.tafsir.entity.VerseArabic;
import com.jolpai.tafsir.entity.VerseTrans;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class RecyclerAyatItemViewHolder extends RecyclerView.ViewHolder {
   // private final TextView mItemTextView;
    Context context;
    private  final R2L mCustomR2L;
    private  final L2R mCustomL2R;
    private  static Typeface tf ;

    public RecyclerAyatItemViewHolder(final View parent, R2L r2l, L2R l2r) {
        super(parent);
        mCustomR2L = r2l;
        mCustomL2R=l2r;
        context=parent.getContext();
    }

    public static RecyclerAyatItemViewHolder newInstance(View parent) {
        tf=Typeface.createFromAsset(parent.getContext().getAssets(),"fonts/TRADO.TTF");
        R2L lCustomR2L = (R2L) parent.findViewById(R.id.cl_R2L);
        L2R lCustomL2R=(L2R) parent.findViewById(R.id.cl_L2R);

        return new RecyclerAyatItemViewHolder(parent,lCustomR2L,lCustomL2R);
    }

    public void setItemText(VerseArabic verseArabic,VerseTrans verseTrans) {

        View normalView =View.inflate(context, R.layout.normal_view, null);
        View normalEmptyView =View.inflate(context, R.layout.normal_view, null);
        TextView tvEmpty=(TextView)normalEmptyView.findViewById(R.id.textView);
        TextView txtAyat = (TextView)normalView.findViewById(R.id.textView);

        txtAyat.setText(verseArabic.getVerse());
        txtAyat.setTypeface(tf);
        txtAyat.setTextSize(35);
        txtAyat.setTextColor(Color.DKGRAY);
        tvEmpty.setText("   ");

        mCustomR2L.removeAllViews();
        mCustomR2L.addView(normalView);

        View normalViewTrans =View.inflate(context, R.layout.normal_view, null);
        View normarImgView =View.inflate(context, R.layout.normal_image_view_ayat_no, null);

        TextView tvEng = (TextView)normalViewTrans.findViewById(R.id.textView);
        ImageView imgAyat = (ImageView)normarImgView.findViewById(R.id.imgAyatNo);

        tvEng.setText(verseTrans.getVerse());
        tvEng.setTypeface(tf);
        tvEng.setTextSize(22);
       // tv.setText(text);

        mCustomL2R.removeAllViews();
        mCustomL2R.addView(normalViewTrans);



        /*String [] arrayWhitespace = text.split("\\s+");
        for (int i=0; i<arrayWhitespace.length; i++) {
            View normalView =View.inflate(context, R.layout.normal_view, null);
            View normalEmptyView =View.inflate(context, R.layout.normal_view, null);
            TextView tvEmpty=(TextView)normalEmptyView.findViewById(R.id.textView);
            TextView tv = (TextView)normalView.findViewById(R.id.textView);
            tv.setText(arrayWhitespace[i].toString());
            tv.setTypeface(tf);
            tv.setTextSize(35);
            tv.setTextColor(Color.DKGRAY);
            tvEmpty.setText("   ");

            mCustomR2L.addView(normalView);
            mCustomR2L.addView(normalEmptyView);
        }*/
        // mCustomR2L.addView(normarImgView);
    }
}
