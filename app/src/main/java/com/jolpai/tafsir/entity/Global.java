package com.jolpai.tafsir.entity;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.jolpai.tafsir.custom.view.Typefaces;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 8/7/2015.
 */
public class Global {
    private static ArrayList<Verse> VERSE_LIST ;
    private static ArrayList<Verse> verseVerseTransList;
    private static ArrayList<SurahName> SURAH_NAME_LIST;

    private static Typeface typefaceArabic ;
    private static Typeface typefaceTrans ;
    public static String selectedArabicFontName="me_quran_volt_newmet";
    public static String selectedTransFontName="SolaimanLipi";
    public static String selectedEngFontName="trado";


    public static Typeface getTypefaceArabic() {
        return typefaceArabic;
    }

    public static void setTypefaceArabic(Typeface typefaceArabic) {


        Global.typefaceArabic = typefaceArabic;
    }

    public static Typeface getTypefaceTrans() {
        return typefaceTrans;
    }

    public static void setTypefaceTrans(Typeface typefaceTrans) {
        Global.typefaceTrans = typefaceTrans;
    }

    public static ArrayList<SurahName> getSurahNameList() {
        return SURAH_NAME_LIST;
    }

    public static void setSurahNameList(ArrayList<SurahName> surahNameList) {
        SURAH_NAME_LIST = surahNameList;
    }

    public static ArrayList<Verse> getVerseVerseTransList() {
        return verseVerseTransList;
    }

    public static void setVerseVerseTransList(ArrayList<Verse> verseVerseTransList) {
        Global.verseVerseTransList = verseVerseTransList;
    }

    public static ArrayList<Verse> getVerseList() {
        return VERSE_LIST;
    }

    public static void setVerseList(ArrayList<Verse> verseList) {
        VERSE_LIST = verseList;
    }
}
