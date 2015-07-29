package com.jolpai.tafsir.db;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Tanim reja on 7/28/2015.
 */
public class App extends Application {
    private static App instence;
    private DatabaseManager dbManager;
    private Context context;



    @Override
    public void onCreate() {
        super.onCreate();

      //  loadApplicationData(instence);
    }

    public DatabaseManager getDBManager() {
        return dbManager;
    }

    public void setDBManager(DatabaseManager dbManager) {
       this.dbManager = dbManager;


    }

    public static void destroyApp() {
        instence = null;
    }

    public synchronized static App getContext() {
        if (instence == null) {
            instence = new App();
            loadApplicationData();
        }

        return instence;
    }

    public boolean isExtSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)
                && !Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    public String getAppDataDir() {
        if (isExtSDCardPresent()) {
            String filePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/TafsirDB";
            File file = new File(filePath);
            if (!file.exists())
                file.mkdirs();

            return filePath;
        } else
            return null;
    }

    public static boolean loadApplicationData() {
       Context context=instence;


        DatabaseManager dbManager = new DatabaseManager(context);
        if(dbManager.initializeDatabase())
        {
            App.getContext().setDBManager(dbManager);
            return true;
        }
        return false;

    }



}
