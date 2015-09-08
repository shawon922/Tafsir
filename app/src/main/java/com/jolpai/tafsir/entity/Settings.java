package com.jolpai.tafsir.entity;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Settings {
    private int id;
    private String name;
    private String lang;


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
