package com.jolpai.tafsir.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.jolpai.tafsir.entity.Audio;
import com.jolpai.tafsir.entity.Font;
import com.jolpai.tafsir.entity.Lang;
import com.jolpai.tafsir.entity.Tafsir;
import com.jolpai.tafsir.entity.Translation;

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
    private SQLiteDatabase db;
    private static String DB_NAME = DP.DB_Name;
    private String DB_PATH = "";
    private Context context;
    private DatabaseManager dbm;


    public DatabaseManager(Context context){
        super(context, DP.DB_Name,FACTORY,VERSION);
        this.context=context;

        DB_PATH = App.getContext().getAppDirectory();

        if(!checkDataBase()){
            try{
                copyDataBase();
            }catch (IOException e){
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

    private  void copyDataBase() throws IOException {
        // Open your local database as the input stream

     //   AssetManager mngr = context.getAssets();
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
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+DP.DB_Folder;
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



    public ArrayList<String> getVersesArabic(){
        ArrayList<String> spinnerList = new ArrayList<String>();
        String sql = "select * from "+ DP.Tbl_VerseArabic + "  LIMIT 100";

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

    public ArrayList<Lang> getLangNameList(){
        ArrayList<Lang> list = new ArrayList<>();

        Lang lang = new Lang();
        lang.setName("Arabic");
        list.add(lang);

        Lang lang1 = new Lang();
        lang1.setName("English");
        list.add(lang1);

        Lang lang2 = new Lang();
        lang2.setName("Bangla");
        list.add(lang2);

        return list;

    }

    public ArrayList<Translation> getTranslatorNameList(){
        ArrayList<Translation> list = new ArrayList<>();

        Translation lang = new Translation();
        lang.setName("Md.Mozebur Rahman");
        list.add(lang);

        Translation lang1 = new Translation();
        lang1.setName("Munir");
        list.add(lang1);

        Translation lang2 = new Translation();
        lang2.setName("Muhiuddin Khan");
        list.add(lang2);

        Translation lang3 = new Translation();
        lang3.setName("Muhiuddin Khan");
        list.add(lang3);

        return list;

    }

    public ArrayList<Audio> getReciterNameList(){
        ArrayList<Audio> list = new ArrayList<>();

        Audio lang = new Audio();
        lang.setName("Al Afasi");
        list.add(lang);

        Audio lang1 = new Audio();
        lang1.setName("Bilal");
        list.add(lang1);

        Audio lang2 = new Audio();
        lang2.setName("Ibn Baz");
        list.add(lang2);

        return list;

    }

    public ArrayList<Font> getFontNameList(){
        ArrayList<Font> list = new ArrayList<>();

        Font lang = new Font();
        lang.setName("Itali");
        list.add(lang);

        Font lang1 = new Font();
        lang1.setName("Arial");
        list.add(lang1);

        Font lang2 = new Font();
        lang2.setName("Solaimani Lipi");
        list.add(lang2);

        return list;

    }

}
