package com.jolpai.tafsir.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanim reja on 7/28/2015.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final SQLiteDatabase.CursorFactory FACTORY = null;
    private SQLiteDatabase db;

    public DatabaseManager(Context context,SQLiteDatabase db){
        super(context, DP.DB_Name,FACTORY,VERSION);
        this.db=db;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<String> getVersesArabic(){
        ArrayList<String> spinnerList = new ArrayList<String>();
        String sql = "select * from "+ DP.Tbl_VerseArabic;

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String name = "";
                String id = "";
                name = cursor.getString(cursor.getColumnIndex(DP.VerseArabic_SurahNo));
                id = cursor.getString(cursor.getColumnIndex(DP.VerseArabic_Verse));

                spinnerList.add(id);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return spinnerList;
    }
}
