package com.jolpai.tafsir.entity;

/**
 * Created by Tanim Reja on 8/19/2015.
 */
public class VerseTrans extends Verse{

    private String  verse,
            surahNo,
            verseId;

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getSurahNo() {
        return surahNo;
    }

    public void setSurahNo(String surahNo) {
        this.surahNo = surahNo;
    }

    public String getVerseId() {
        return verseId;
    }

    public void setVerseId(String verseId) {
        this.verseId = verseId;
    }
}
