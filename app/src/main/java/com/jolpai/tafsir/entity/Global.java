package com.jolpai.tafsir.entity;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 8/7/2015.
 */
public class Global {
    private static ArrayList<String> VERSE_LIST ;


    public static ArrayList<String> getVerseList() {
        return VERSE_LIST;
    }

    public static void setVerseList(ArrayList<String> verseList) {
        VERSE_LIST = verseList;
    }
}
