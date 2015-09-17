package com.jolpai.tafsir.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.jolpai.tafsir.entity.Settings;
import com.jolpai.tafsir.entity.SurahName;
import com.jolpai.tafsir.entity.Tafsir;
import com.jolpai.tafsir.entity.Translation;
import com.jolpai.tafsir.entity.Verse;
import com.jolpai.tafsir.entity.VerseArabic;
import com.jolpai.tafsir.entity.VerseTrans;

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
    private static String DB_NAME = DbProperty.DB_Name;
    private String DB_PATH = "";
    private Context context;
    private DatabaseManager dbm;
    private static int currentDbVersion;


    public DatabaseManager(Context context){
        super(context, DbProperty.DB_Name,FACTORY,VERSION);
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

    public  static void getDV(){

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
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ DbProperty.DB_Folder;
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



    public ArrayList<SurahName> getSurahName(String lang){

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

    public ArrayList<Settings> getFont(String langType){
        ArrayList<Settings> list = new ArrayList<>();

        String sql="select * from Font where lang='" + langType + "'" ;
        Cursor cursor = db.rawQuery(sql, null);
        Settings settings=null;

        if(cursor.moveToFirst()){
            do{
                settings=new Settings();
                settings.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                settings.setName(cursor.getString(cursor.getColumnIndex("name")));
                settings.setLang(cursor.getString(cursor.getColumnIndex("lang")));


                list.add(settings);
            }while(cursor.moveToNext());

        }
        cursor.close();
        if(settings.equals(null)) {
            settings=new Settings();
            settings.setId(0);
            settings.setName("Choose Arabic Font");
        }else{
            settings.setId(0);
            settings.setName("Choose Arabic Font");
        }

        return list;
    }

}
