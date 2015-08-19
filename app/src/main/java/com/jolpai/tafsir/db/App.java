package com.jolpai.tafsir.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tanim reja on 7/28/2015.
 */
public class App extends Application {
    private static App instence;
    private DatabaseManager dbm;
  //  public static final SQLiteDatabase.CursorFactory FACTORY = null;
    private static String DB_NAME = DbProperty.DB_Name;
    private String DB_PATH = "";
    public static SQLiteDatabase db;
    public static Context context;


    public synchronized static App getContext() {
        if (instence == null)
            instence = new App();


        return instence;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public DatabaseManager getDatabaseManager(){
        setDatabaseManager();
        return dbm;
    }

    public  void setDatabaseManager(){

       DatabaseManager manager = new DatabaseManager(instence);
        /*DB_PATH = App.getContext().getAppDirectory();

        if(!checkDataBase()){
            try{
                copyDataBase();
            }catch (IOException e){
                e.printStackTrace();
            }
        }*/

        if(manager.initializeDatabase()){
            dbm=manager;
        }
    }


    private  void copyDataBase() throws IOException {
        // Open your local database as the input stream
        InputStream myInput = instence.getAssets().open(DB_NAME);

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

    public String getAppDirectory(){
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

}
