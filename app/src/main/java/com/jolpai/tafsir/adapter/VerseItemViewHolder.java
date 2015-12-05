package com.jolpai.tafsir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.L2R;
import com.jolpai.tafsir.custom.view.R2L;
import com.jolpai.tafsir.utility.Typefaces;
import com.jolpai.tafsir.model.Global;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.model.VerseTrans;

/**
 * Created by Tanim reja on 8/9/2015.
 */
public class VerseItemViewHolder extends RecyclerView.ViewHolder {
   // private final TextView mItemTextView;
    Context context;
    private  final R2L mCustomR2L;
    private  final L2R mCustomL2R;
    private final LinearLayout mLinearLayout;
    private  final TextView txtAyatNo;
    private  static Typeface tf ;

    public VerseItemViewHolder(final View parent) {
        super(parent);
        context=parent.getContext();
        mCustomR2L = (R2L) parent.findViewById(R.id.cl_R2L);
        mCustomL2R=(L2R) parent.findViewById(R.id.cl_L2R);
        mLinearLayout =(LinearLayout) parent.findViewById(R.id.cl_Normal);
        txtAyatNo=(TextView) parent.findViewById(R.id.txtAyatNo);

    }

    public static VerseItemViewHolder newInstance(View parent) {
        Global.setTypefaceArabic(Typefaces.get(parent.getContext(), Global.selectedArabicFontName));
        Global.setTypefaceTrans(Typefaces.get(parent.getContext(), Global.selectedTransFontName));


       // R2L lCustomR2L = (R2L) parent.findViewById(R.id.cl_R2L);
       // L2R lCustomL2R=(L2R) parent.findViewById(R.id.cl_L2R);

        return new VerseItemViewHolder(parent);
    }

    public void setItemText(VerseArabic verseArabic) {
        View normalView = View.inflate(context, R.layout.normal_view, null);
        TextView txtVerse = (TextView)normalView.findViewById(R.id.textView);
        txtVerse.setText(verseArabic.getVerse());
        txtVerse.setTypeface(Global.getTypefaceArabic());
        txtVerse.setTextSize(Global.arabicFontSize);
        txtVerse.setTextColor(Color.DKGRAY);

        View normalViewTrans =View.inflate(context, R.layout.normal_view, null);
        TextView txtTrans = (TextView)normalViewTrans.findViewById(R.id.textView);

        txtTrans.setTypeface(Global.getTypefaceTrans());
        txtTrans.setTextSize(22);
        txtTrans.setTextColor(Color.DKGRAY);

        mCustomR2L.removeAllViews();
        mLinearLayout.removeAllViews();

        if(verseArabic.getVerseId().equalsIgnoreCase("0")){
            txtAyatNo.setVisibility(View.GONE);
            txtVerse.setGravity(View.TEXT_ALIGNMENT_CENTER);
            txtVerse.setTextColor(context.getResources().getColor(R.color.green_500));
            mLinearLayout.addView(normalView);
            mLinearLayout.setVisibility(View.VISIBLE);
            mCustomR2L.setVisibility(View.GONE);

        }else{
            txtAyatNo.setVisibility(View.VISIBLE);
            txtAyatNo.setText(verseArabic.getVerseId());
            txtAyatNo.setTextColor(context.getResources().getColor(R.color.gray_800));
            mCustomR2L.addView(normalView);
            mLinearLayout.setVisibility(View.GONE);
            mCustomR2L.setVisibility(View.VISIBLE);
        }

    }

    public void setItemText(VerseArabic verseArabic,VerseTrans verseTrans) {

        View normalView = View.inflate(context, R.layout.normal_view, null);
        TextView txtVerse = (TextView)normalView.findViewById(R.id.textView);
        txtVerse.setText(verseArabic.getVerse());
        txtVerse.setTypeface(Global.getTypefaceArabic());
        txtVerse.setTextSize(Global.arabicFontSize);
        txtVerse.setTextColor(Color.DKGRAY);

        View normalViewTrans =View.inflate(context, R.layout.normal_view, null);
        TextView txtTrans = (TextView)normalViewTrans.findViewById(R.id.textView);
        txtTrans.setText(verseTrans.getVerse());
        txtTrans.setTypeface(Global.getTypefaceTrans());
        txtTrans.setTextSize(22);
        txtTrans.setTextColor(Color.DKGRAY);

        mCustomR2L.removeAllViews();
        mLinearLayout.removeAllViews();

        if(verseArabic.getVerseId().equalsIgnoreCase("0")){
            txtAyatNo.setVisibility(View.GONE);
            txtVerse.setGravity(View.TEXT_ALIGNMENT_CENTER);
            txtVerse.setTextColor(context.getResources().getColor(R.color.green_500));
            mLinearLayout.addView(normalView);

        }else{
            txtAyatNo.setVisibility(View.VISIBLE);
            txtAyatNo.setText(verseTrans.getVerseId());
            txtAyatNo.setTextColor(context.getResources().getColor(R.color.gray_800));
            mCustomR2L.addView(normalView);
            mLinearLayout.addView(normalViewTrans);

        }

        // setArabicTextInSingleRow(verseArabic);
    }

    public void setArabicTextInSingleRow(VerseArabic verseArabic){
        String [] arrayWhitespace = verseArabic.getVerse().split("\\s+");
        TextView tv;
        mCustomR2L.removeAllViews();
        for (int i=0; i<arrayWhitespace.length; i++) {
            View normalView =View.inflate(context, R.layout.normal_view, null);
            View normalEmptyView =View.inflate(context, R.layout.normal_view, null);
            TextView tvEmpty=(TextView)normalEmptyView.findViewById(R.id.textView);
            tv = (TextView)normalView.findViewById(R.id.textView);
            tv.setText(arrayWhitespace[i].toString());
            tv.setTypeface(Global.getTypefaceArabic());
            tv.setTextSize(Global.arabicFontSize);
            tv.setTextColor(Color.DKGRAY);
            tvEmpty.setText("   ");

            // mCustomR2L.removeAllViews();
            mCustomR2L.addView(normalView);
            mCustomR2L.addView(normalEmptyView);
        }
        if(verseArabic.getVerseId().equalsIgnoreCase("0")) {
            txtAyatNo.setVisibility(View.GONE);
        }else{
            txtAyatNo.setTextColor(context.getResources().getColor(R.color.gray_800));
            txtAyatNo.setText(verseArabic.getVerseId());
        }

    }
}
