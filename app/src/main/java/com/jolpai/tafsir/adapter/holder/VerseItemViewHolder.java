package com.jolpai.tafsir.adapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jolpai.tafsir.R;
import com.jolpai.tafsir.model.AppSettings;
import com.jolpai.tafsir.model.GLOBAL;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.model.VerseTrans;
import com.jolpai.tafsir.utility.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tanim reja on 12/20/2015.
 */
public class VerseItemViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textArabic;
    private TextView textTrans;
    private final View card;
    private Typeface arabicFont,
            secondaryFont;
    private AppSettings appSettings;
    public VerseItemViewHolder(View item, Context context, AppSettings appSettings) {
        super(item);
        LinearLayout ll=(LinearLayout)item;

        this.context=context;
        textArabic =(TextView)ll.findViewById(R.id.textArabic);
        textTrans=(TextView)ll.findViewById(R.id.textTrans);
        card=item;
        this.appSettings=appSettings;
        //this.appSettings= Utility.getAppSettings(context);
    }
    public static VerseItemViewHolder newInstance(View item,Context context,AppSettings appSettings) {

        return new VerseItemViewHolder(item,context,appSettings);
    }
    public void setItemText(VerseArabic verseArabic,VerseTrans verseTrans){

        String whitespace_chars =  ""       /* dummy empty string for homogeneity */
                + "\\u0009" // CHARACTER TABULATION
                + "\\u000A" // LINE FEED (LF)
                + "\\u000B" // LINE TABULATION
                + "\\u000C" // FORM FEED (FF)
                + "\\u000D" // CARRIAGE RETURN (CR)
                + "\\u0020" // SPACE
                + "\\u0085" // NEXT LINE (NEL)
                + "\\u00A0" // NO-BREAK SPACE
                + "\\u1680" // OGHAM SPACE MARK
                + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
                + "\\u2000" // EN QUAD
                + "\\u2001" // EM QUAD
                + "\\u2002" // EN SPACE
                + "\\u2003" // EM SPACE
                + "\\u2004" // THREE-PER-EM SPACE
                + "\\u2005" // FOUR-PER-EM SPACE
                + "\\u2006" // SIX-PER-EM SPACE
                + "\\u2007" // FIGURE SPACE
                + "\\u2008" // PUNCTUATION SPACE
                + "\\u2009" // THIN SPACE
                + "\\u200A" // HAIR SPACE
                + "\\u2028" // LINE SEPARATOR
                + "\\u2029" // PARAGRAPH SEPARATOR
                + "\\u202F" // NARROW NO-BREAK SPACE
                + "\\u205F" // MEDIUM MATHEMATICAL SPACE
                + "\\u3000" // IDEOGRAPHIC SPACE
                ;

        String regix=null;

        String arabic="خَتَمَ اللَّـهُ عَلَىٰ قُلُوبِهِمْ وَعَلَىٰ سَمْعِهِمْ ۖ وَعَلَىٰ أَبْصَارِهِمْ غِشَاوَةٌ ۖ وَلَهُمْ عَذَابٌ عَظِيمٌ ";
        String english="My Name 23 4 #s Tanim #2 Reja. 423432 .Thank #3 you";//"[+خ ۖ#|]"
        try {
            byte[] utf8Bytes = "[ۖ ۗ ۚ ۙ ۘ]".getBytes("UTF-8");
            regix= new String(utf8Bytes,"UTF-8");
        }catch(Exception e){

        }

        SpannableString spannableString =new SpannableString(verseArabic.getVerse());
        //  Matcher matcher = Pattern.compile("([0-9_-]+)").matcher(spannableString);
        // Matcher matcher = Pattern.compile("[+خ ۖ#|]").matcher(spannableString);
        Matcher matcher = Pattern.compile("["+ regix +"]").matcher(spannableString);


        while (matcher.find()){


            final String tag = matcher.group(0);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Log.e("click", "click " + tag);
                   // Toast.makeText(context, "clicked : " + tag, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.RED);
                    // ds.setTextSize(30);
                    ds.setUnderlineText(false);

                }
            };
            spannableString.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_MARK_POINT);

        }




       // textArabic.setText(verseArabic.getVerse());
        textArabic.setText(spannableString);
        textArabic.setTypeface(appSettings.getArabicFont());
        textArabic.setTextSize(appSettings.getArabicFontSize());
        textArabic.setTextColor(Color.DKGRAY);

        textTrans.setText(verseTrans.getVerse());
        textTrans.setTypeface(appSettings.getSecondaryFont());
        textTrans.setTextSize(appSettings.getSecondaryFontSize());
        textTrans.setTextColor(Color.DKGRAY);
        card.setTag(verseArabic);
        /*if(appSettings.getTranslation()){
            textTrans.setVisibility(View.GONE);
        }else{
            textTrans.setVisibility(View.VISIBLE);
        }*/

    }
}
