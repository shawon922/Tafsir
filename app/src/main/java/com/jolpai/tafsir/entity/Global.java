package com.jolpai.tafsir.entity;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 8/7/2015.
 */
public class Global {
    private static ArrayList<String> VERSE_LIST ;
    private static ArrayList<VerseTrans> verseVerseTransList;
    private static ArrayList<SurahName> SURAH_NAME_LIST;

    public static ArrayList<SurahName> getSurahNameList() {
        return SURAH_NAME_LIST;
    }

    public static void setSurahNameList(ArrayList<SurahName> surahNameList) {
        SURAH_NAME_LIST = surahNameList;
    }

    public static ArrayList<VerseTrans> getVerseVerseTransList() {
        return verseVerseTransList;
    }

    public static void setVerseVerseTransList(ArrayList<VerseTrans> verseVerseTransList) {
        Global.verseVerseTransList = verseVerseTransList;
    }

    public static ArrayList<String> getVerseList() {
        return VERSE_LIST;
    }

    public static void setVerseList(ArrayList<String> verseList) {
        VERSE_LIST = verseList;
    }
}
