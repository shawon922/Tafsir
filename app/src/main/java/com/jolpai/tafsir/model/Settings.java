package com.jolpai.tafsir.model;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Settings {
    private int id;
    private String name;
    private String lang;
    private int arabicFontSize;


    public int getArabicFontSize() {
        return arabicFontSize;
    }

    public void setArabicFontSize(int arabicFontSize) {
        this.arabicFontSize = arabicFontSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
