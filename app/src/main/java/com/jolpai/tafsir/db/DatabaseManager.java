package com.jolpai.tafsir.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

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
    public static final SQLiteDatabase.CursorFactory FACTORY = null;
    private static String DB_NAME = "jolpai.sqlite";
    private String DB_PATH = "";
    private SQLiteDatabase db;
    private static Context context;

    public DatabaseManager(Context context,SQLiteDatabase db){
        super(context,DB_NAME,FACTORY,VERSION);
        this.db=db;
        /*this.context = context;
        DB_PATH = getAppDirectory();

        if(!checkDataBase()){
            try{
                copyDataBase();
            }catch (IOException e){
                e.printStackTrace();
            }
        }*/
    }


    private  void copyDataBase() throws IOException{
        // Open your local database as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty database
        String dataBaseFileName =DB_PATH +"/"+DB_NAME;

        //Open the empty db as the output stream.
        new File(dataBaseFileName).createNewFile();
        OutputStream myOutput = new FileOutputStream(dataBaseFileName);

        //transfer bytes from the input file to output file.
        byte [] buffer = new byte[1024];
        int length;
        while((length =myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private  boolean checkDataBase(){
        String myPath = DB_PATH+"/"+DB_NAME;
        if( new File(myPath).exists()){
            return true;
        }else{
            return false;
        }
    }

    private String getAppDirectory(){
        if(isExtSDCardPresent()){
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TafsirDB";
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdir();
            }
            return filePath;
        }else{
            return null;
        }
    }

    public static boolean isExtSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);

    }

    public boolean initializeDatabase(){
        if(DB_PATH == null){
            return false;
        }else{
            try{
                db=SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_NAME, null,
                        SQLiteDatabase.OPEN_READWRITE
                                | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
            return true;
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<String> getFiscalYearList(){
        List<String> spinnerList = new ArrayList<String>();
        String sql = "select * from VerseArabic";

        Cursor cursor = db.rawQuery(sql, null);



        if(cursor.moveToFirst()){
            do{
                String name = "";
                String id = "";
                name = cursor.getString(cursor.getColumnIndex(DBO.VerseArabic_SurahNo));
                id = cursor.getString(cursor.getColumnIndex(DBO.VerseArabic_Verse));

                spinnerList.add(id);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return spinnerList;
    }




}
