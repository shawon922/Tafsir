package com.jolpai.tafsir.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jolpai.tafsir.entity.Verse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanim reja on 7/28/2015.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    private String DB_PATH;
    private static String DB_NAME = "jolpai";
    private SQLiteDatabase db;
    private Context context;
    SharedPreferences pref;

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, VERSION);

        this.context = context;
        pref = this.context.getSharedPreferences("iapp_pref",
                this.context.MODE_PRIVATE);

        DB_PATH = App.getContext().getAppDataDir();

        if (pref.getBoolean("isfirst", true) || !checkDataBase()) {
            try {
                copyDataBase();
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isfirst", false);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean checkDataBase() {
        String myPath = DB_PATH + "/" + DB_NAME;
        if (new File(myPath).exists())
            return true;
        else
            return false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + "/" + DB_NAME;

        // Open the empty db as the output stream
        new File(outFileName).createNewFile();
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean initializeDatabase() {
        if (DB_PATH == null)
            return false;

        try {
            db = SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_NAME, null,
                    SQLiteDatabase.OPEN_READWRITE
                            | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteDataBase() {
        String myPath = DB_PATH + "/" + DB_NAME;
        if (new File(myPath).delete())
            return true;
        else
            return false;
    }

    public List<Verse> getVerseArabic(){
        List<Verse> verseList = new ArrayList<>();
        String sql = "select *  from VerseArabic";
        Cursor cursor = db.rawQuery(sql, null);


        if(cursor.moveToFirst()){
            do{
                Verse verse = new Verse();
                verse.setSurahNo(cursor.getString(cursor.getColumnIndex(DBO.VerseArabic_SurahNo)));
                verse.setVerseNo(cursor.getString(cursor.getColumnIndex(DBO.VerseArabic_VerseNo)));
                verse.setVerse(cursor.getString(cursor.getColumnIndex(DBO.VerseArabic_Verse)));
                verseList.add(verse);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return verseList;
    }
}
