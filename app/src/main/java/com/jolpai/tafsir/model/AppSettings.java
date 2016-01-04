package com.jolpai.tafsir.model;

import android.graphics.Typeface;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class AppSettings {
    private Typeface arabicFont;
    private int arabicFontSize;
    private String secondaryLang;
    private Typeface secondaryFont;
    private int secondaryFontSize;
    private String translator;
    private Boolean translation;


    public Typeface getArabicFont() {
        return arabicFont;
    }

    public void setArabicFont(Typeface arabicFont) {
        this.arabicFont = arabicFont;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public int getSecondaryFontSize() {
        return secondaryFontSize;
    }

    public void setSecondaryFontSize(int secondaryFontSize) {
        this.secondaryFontSize = secondaryFontSize;
    }

    public Typeface getSecondaryFont() {
        return secondaryFont;
    }

    public void setSecondaryFont(Typeface secondaryFont) {
        this.secondaryFont = secondaryFont;
    }

    public String getSecondaryLang() {
        return secondaryLang;
    }

    public void setSecondaryLang(String secondaryLang) {
        this.secondaryLang = secondaryLang;
    }

    public int getArabicFontSize() {

        return arabicFontSize;
    }

    public void setArabicFontSize(int arabicFontSize) {
        this.arabicFontSize = arabicFontSize;
    }

    public Boolean getTranslation() {
        return translation;
    }

    public void setTranslation(Boolean translation) {
        this.translation = translation;
    }
}
