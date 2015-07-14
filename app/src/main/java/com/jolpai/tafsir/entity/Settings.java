package com.jolpai.tafsir.entity;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Settings {

    private ArrayList <String> parentList;
    private ArrayList <Tafsir> tafsir;
    private ArrayList <Lang> lang;
    private ArrayList <Translation> translation;
    private ArrayList <Font> font;
    private ArrayList <Audio> audio;
    private ArrayList <Update> update;
    private ArrayList <Audio> about;
    private ArrayList <Help> help;


    public ArrayList<Tafsir> getTafsir() {
        return tafsir;
    }

    public void setTafsir(ArrayList<Tafsir> tafsir) {
        this.tafsir = tafsir;
    }

    public ArrayList<Lang> getLang() {
        return lang;
    }

    public void setLang(ArrayList<Lang> lang) {
        this.lang = lang;
    }

    public ArrayList<Translation> getTranslation() {
        return translation;
    }

    public void setTranslation(ArrayList<Translation> translation) {
        this.translation = translation;
    }

    public ArrayList<Font> getFont() {
        return font;
    }

    public void setFont(ArrayList<Font> font) {
        this.font = font;
    }

    public ArrayList<Audio> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<Audio> audio) {
        this.audio = audio;
    }

    public ArrayList<Update> getUpdate() {
        return update;
    }

    public void setUpdate(ArrayList<Update> update) {
        this.update = update;
    }

    public ArrayList<Audio> getAbout() {
        return about;
    }

    public void setAbout(ArrayList<Audio> about) {
        this.about = about;
    }

    public ArrayList<Help> getHelp() {
        return help;
    }

    public void setHelp(ArrayList<Help> help) {
        this.help = help;
    }
}
