package com.jolpai.tafsir.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.jolpai.tafsir.model.AppSettings;
import com.jolpai.tafsir.model.SurahName;
import com.jolpai.tafsir.model.Tafsir;
import com.jolpai.tafsir.model.Translation;
import com.jolpai.tafsir.model.Verse;
import com.jolpai.tafsir.model.VerseArabic;
import com.jolpai.tafsir.model.VerseTrans;

import java.util.ArrayList;

/**
 * Created by Tanim reja on 12/26/2015.
 */
public class MyDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "jolpai.sqlite";
    private static final int DATABASE_VERSION = 3;
    private static final String STORAGE_DIR = null;//"TaFsIr_DB";
    SQLiteDatabase db = getReadableDatabase();
    private static MyDB myDB;

    public MyDB(Context context) {
        super(context, DATABASE_NAME,STORAGE_DIR, null, DATABASE_VERSION);
    }

    public static MyDB getInstance(Context context){
        if(myDB ==null){
            return myDB = new MyDB(context);
        }else{
            return myDB;
        }
    }

    public int getUpgradeVersion() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"MAX (version)"};
        String sqlTables = "upgrades";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        int v = 0;
        c.moveToFirst();
        if (!c.isAfterLast()) {
            v = c.getInt(0);
        }
        c.close();
        return v;
    }

    public  ArrayList<SurahName> getSurahName(String lang){

        ArrayList<SurahName> verseList = new ArrayList<>();
        String sql = "select * from SurahName";
        Cursor cursor = db.rawQuery(sql, null);
        SurahName surahName;
        if(cursor.moveToFirst()){
            do{
                surahName =new SurahName();
                surahName.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                surahName.setSurahNo(cursor.getString(cursor.getColumnIndex("surahNo")));
                surahName.setSurahName(cursor.getString(cursor.getColumnIndex("name")));
                surahName.setVerseNo(cursor.getString(cursor.getColumnIndex("verseNo")));

                verseList.add(surahName);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return verseList;

    }

    public ArrayList<Verse> getVersesArabic(String surahNo){
        ArrayList<Verse> verseList = new ArrayList<>();
        //  String sql = "select " + DbProperty.Tbl_VerseArabic_SurahNo+ "," + DbProperty.Tbl_VerseArabic_Verse+ " from "+ DbProperty.Tbl_VerseArabic + "  where surahNo="+surahNo+"";
        String sql="select * from Verse where surahNo="+surahNo+"";
        Cursor cursor = db.rawQuery(sql, null);
        VerseArabic verseArabic;
        if(cursor.moveToFirst()){
            do{
                verseArabic=new VerseArabic();
                verseArabic.setSurahNo(cursor.getString(cursor.getColumnIndex(DbProperty.Tbl_VerseArabic_SurahNo)));
                verseArabic.setVerse(cursor.getString(cursor.getColumnIndex(DbProperty.Tbl_VerseArabic_Verse)));
                verseArabic.setVerseId(cursor.getString(cursor.getColumnIndex("verseId")));


                verseList.add(verseArabic);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return verseList;
    }

    public ArrayList<Verse> getPlainTrans(String surahNo){
        ArrayList<Verse> verseTransList = new ArrayList<>();
        // String sql = "select " + DbProperty.Tbl_VerseTrans_SurahNo+ "," + DbProperty.Tbl_VerseTrans_Verse+ " from "+ DbProperty.Tbl_VerseTrans + "  where surahNo="+surahNo+" and lang='bn'";

        String sql="select * from Trans  where surahNo="+surahNo+" and lang='bn'";
        VerseTrans verseTrans;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                verseTrans =new VerseTrans();
                verseTrans.setSurahNo(cursor.getString(cursor.getColumnIndex(DbProperty.Tbl_VerseArabic_SurahNo)));
                verseTrans.setVerse(cursor.getString(cursor.getColumnIndex(DbProperty.Tbl_VerseArabic_Verse)));
                verseTrans.setVerseId(cursor.getString(cursor.getColumnIndex("verseId")));

                verseTransList.add(verseTrans);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return verseTransList;
    }

    public ArrayList<Tafsir> getTafsirNameList(){
        ArrayList<Tafsir> list = new ArrayList<>();

        Tafsir tafsir = new Tafsir();
        tafsir.setName("Ibn Kathir");
        list.add(tafsir);

        Tafsir tafsir1 = new Tafsir();
        tafsir1.setName("Fee Zilalil Quran");
        list.add(tafsir1);

        Tafsir tafsir2 = new Tafsir();
        tafsir2.setName("Ma'reful Quran");
        list.add(tafsir2);

        return list;

    }

    public ArrayList<String> getArabicFontList(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select please");
        list.add("me_quran_volt_newmet");
        list.add("trado");
        list.add("_PDMS_Saleem_QuranFont");
        list.add("noorehira");
        return list;
    }

    public ArrayList<String> getSecondaryFontList(){
        ArrayList<String> list = new ArrayList();
        list.add("Select please");
        list.add("SolaimanLipi");
        list.add("SolaimanLipi");
        list.add("SolaimanLipi");
        return list;
    }

    public ArrayList<String> getTranslatorList(){
        ArrayList<String> list = new ArrayList();
        list.add("Select please");
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        return list;
    }

    public ArrayList<String> getSecondaryLangList(){
        ArrayList<String> list = new ArrayList();
        list.add("Select please");
        list.add("Bangla");
        list.add("English");
        list.add("Hindi");
        list.add("Mandarin");

        return list;
    }




}
