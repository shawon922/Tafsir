package com.jolpai.tafsir.entity;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 8/7/2015.
 */
public class Global {
    private static ArrayList<String> VERSE_LIST ;
    private static ArrayList<Trans> VERSE_TRANS_LIST;


    public static ArrayList<Trans> getVerseTransList() {
        return VERSE_TRANS_LIST;
    }

    public static void setVerseTransList(ArrayList<Trans> verseTransList) {
        VERSE_TRANS_LIST = verseTransList;
    }

    public static ArrayList<String> getVerseList() {
        return VERSE_LIST;
    }

    public static void setVerseList(ArrayList<String> verseList) {
        VERSE_LIST = verseList;
    }
}
