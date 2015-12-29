package com.jolpai.tafsir.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tanim reja on 8/7/2015.
 */
public class Global {
    private static ArrayList<Verse> VERSE_LIST ;
    private static ArrayList<Verse> verseVerseTransList;
    private static ArrayList<SurahName> SURAH_NAME_LIST;

    private static Typeface typefaceArabic ;
    private static Typeface typefaceTrans ;
    public static String selectedArabicFontName="trado";
    public static String selectedTransFontName="SolaimanLipi";
    public static String selectedEngFontName="trado";
    public static int arabicFontSize =30;
    public static Map<String,View> itemHeaderViewMap=new Hashtable<>();
    public static boolean isPopupOpen=false;

    public static SharedPreferences bookmarkedStore;
    public static SharedPreferences bookMarkedStoreSurah;

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
