package com.jolpai.tafsir.model;

/**
 * Created by Tanim Reja on 8/28/2015.
 */
public class VerseArabic extends Verse {

    private String  verse,
            surahNo,
            verseId;
    private boolean isBookmarked;

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }


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
