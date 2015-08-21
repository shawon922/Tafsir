package com.jolpai.tafsir.entity;

/**
 * Created by Tanim reja on 7/28/2015.
 */
public class SurahName {

    int id;
    String surahName;
    String surahNo;
    String verseNo;
    String lang;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public String getSurahNo() {
        return surahNo;
    }

    public void setSurahNo(String surahNo) {
        this.surahNo = surahNo;
    }

    public String getVerseNo() {
        return verseNo;
    }

    public void setVerseNo(String verseNo) {
        this.verseNo = verseNo;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
