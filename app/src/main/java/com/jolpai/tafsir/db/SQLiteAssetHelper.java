package com.jolpai.tafsir.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by Tanim reja on 12/26/2015.
 */
public class SQLiteAssetHelper extends SQLiteOpenHelper {

    private final Context context;
    private final String databaseName;
    private  final SQLiteDatabase.CursorFactory factory;
    private static final String ASSET_DB_PATH = "databases";

    private SQLiteDatabase mDatabase = null;
    private boolean mIsInitializing = false;

    private String mDatabasePath;

    private String mAssetPath;

    private String mUpgradePathFormat;

    private int mForcedUpgradeVersion = 0;

    int mNewVersion;


    public SQLiteAssetHelper(Context context,
                             String databaseName,
                             String storageDirectory,
                             SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, databaseName, factory, version);

        this.context = context;
        this.databaseName = databaseName;
        this.factory = factory;
        this.mNewVersion = version;

        if (version < 1) throw new IllegalArgumentException("Version must be >= 1, was " + version);
        if (databaseName == null) throw new IllegalArgumentException("Database name cannot be null");

        mAssetPath = ASSET_DB_PATH + "/" + databaseName;
        if (storageDirectory != null) {
            mDatabasePath = storageDirectory;
        } else {
            mDatabasePath = context.getApplicationInfo().dataDir + "/databases";
        }


    }


    public SQLiteAssetHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);

        this.context = context;
        this.databaseName = databaseName;
        this.factory = factory;
        this.mNewVersion = version;

    }


    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mDatabase != null && mDatabase.isOpen()) {
            return mDatabase;  // The database is already open for business
        }

        if (mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
        try{
            return getWritableDatabase();

        }catch (SQLiteException e){
            if(databaseName ==null) throw e;
        }

        SQLiteDatabase db= null;
        try{
            mIsInitializing=true;
            String path=context.getDatabasePath(databaseName).getPath();
            db = SQLiteDatabase.openDatabase(path,factory, SQLiteDatabase.OPEN_READONLY);
            if(db.getVersion() != mNewVersion){
                throw new SQLiteException("Can't upgrade read-only database from version " +
                        db.getVersion() + " to " + mNewVersion + ": " + path);
            }
            onOpen(db);
            mDatabase = db;
            return mDatabase;
        } finally {
            mIsInitializing = false;
            if (db != null && db != mDatabase) db.close();
        }


    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {


        if(mDatabase != null && mDatabase.isOpen() && ! mDatabase.isReadOnly()){
            return mDatabase;
        }

        if(mIsInitializing){
            throw new IllegalStateException("getWritableDatabase called recursively");
        }

         /* If we have a read-only database open, someone could be using it
         (though they shouldn't), which would cause a lock to be held on
         the file, and our attempts to open the database read-write would
         fail waiting for the file lock.  To prevent that, we acquire the
         lock on the read-only database, which shuts out other users.
         */
        boolean success=false;
        SQLiteDatabase db = null;

        try{
            mIsInitializing=true;
            db=createOrOpenDatabase(false);
            db.setVersion(mNewVersion);
            int version=db.getVersion();
            //do force upgrade

            if(version !=0 && version < mForcedUpgradeVersion){
                db.beginTransaction();
                try{
                    if(version==0){
                        onCreate(db);
                    }else{
                        if(version<mNewVersion){
                            // can not downgrade database
                        }
                        onUpgrade(db,version,mNewVersion);
                    }
                    db.setVersion(mNewVersion);
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
            }
            onOpen(db);
            success=true;
            return db;


        }finally {
            mIsInitializing=false;
            if(success){
                if(mDatabase != null){
                    try{
                        mDatabase.close();
                    }catch (Exception e){

                    }
                }
                mDatabase=db;
            }else{
                if(db != null){
                    db.close();
                }
            }
        }


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        ArrayList<String> paths = new ArrayList<String>();
        getUpgradeFilePaths(oldVersion, newVersion-1, newVersion, paths);

        if (paths.isEmpty()) {
           // Log.e(TAG, "no upgrade script path from " + oldVersion + " to " + newVersion);
           // throw new SQLiteAssetException("no upgrade script path from " + oldVersion + " to " + newVersion);
        }


        Collections.sort(paths, new VersionComparator());
        for (String path : paths) {
            try {

                InputStream is = context.getAssets().open(path);
                String sql = ScriptProvider.convertStreamToString(is);
                if (sql != null) {
                    List<String> cmds = ScriptProvider.splitSqlScript(sql, ';');
                    for (String cmd : cmds) {
                        //Log.d(TAG, "cmd=" + cmd);
                        if (cmd.trim().length() > 0) {
                            db.execSQL(cmd);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Bypass the upgrade process (for each increment up to a given version) and simply
     * overwrite the existing database with the supplied asset file.
     *
     * @param version bypass upgrade up to this version number - should never be greater than the
     *                latest database version.
     *
     * @deprecated use {@link #setForcedUpgrade} instead.
     */
    @Deprecated
    public void setForcedUpgradeVersion(int version) {
        setForcedUpgrade(version);
    }

    /**
     * Bypass the upgrade process (for each increment up to a given version) and simply
     * overwrite the existing database with the supplied asset file.
     *
     * @param version bypass upgrade up to this version number - should never be greater than the
     *                latest database version.
     */
    public void setForcedUpgrade(int version) {
        mForcedUpgradeVersion = version;
    }

    /**
     * Bypass the upgrade process for every version increment and simply overwrite the existing
     * database with the supplied asset file.
     */
    public void setForcedUpgrade() {
        setForcedUpgrade(mNewVersion);
    }

    private SQLiteDatabase createOrOpenDatabase(boolean force){
        SQLiteDatabase db = null;
        File file = new File (mDatabasePath + "/" + databaseName);
        if (file.exists()) {
            db = returnDatabase();
        }
        //SQLiteDatabase db = returnDatabase();

        if (db != null) {
            // database already exists
            if (force) {
                //Log.w(TAG, "forcing database upgrade!");
                copyDatabaseFromAssets();
                db = returnDatabase();
            }
            return db;
        } else {
            // database does not exist, copy it from assets and return it
            copyDatabaseFromAssets();
            db = returnDatabase();
            return db;
        }
    }

    private SQLiteDatabase returnDatabase(){
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(mDatabasePath + "/" + databaseName, factory, SQLiteDatabase.OPEN_READWRITE);
            //Log.i(TAG, "successfully opened database " + mName);
            return db;
        } catch (SQLiteException e) {
            //Log.w(TAG, "could not open database " + mName + " - " + e.getMessage());
            return null;
        }
    }

    private void copyDatabaseFromAssets() throws SQLiteAssetException {
      //  Log.w(TAG, "copying database from assets...");

        String path = mAssetPath;
        String dest = mDatabasePath + "/" + databaseName;
        InputStream is;
        boolean isZip = false;

        try {
            // try uncompressed
            is = context.getAssets().open(path);
        } catch (IOException e) {
            // try zip
            try {
                is = context.getAssets().open(path + ".zip");
                isZip = true;
            } catch (IOException e2) {
                // try gzip
                try {
                    is = context.getAssets().open(path + ".gz");
                } catch (IOException e3) {
                    SQLiteAssetException se = new SQLiteAssetException("Missing " + mAssetPath + " file (or .zip, .gz archive) in assets, or target folder not writable");
                    se.setStackTrace(e3.getStackTrace());
                    throw se;
                }
            }
        }

        try {
            File f = new File(mDatabasePath + "/");
            if (!f.exists()) { f.mkdir(); }
            if (isZip) {
                ZipInputStream zis = ScriptProvider.getFileFromZip(is);
                if (zis == null) {
                    throw new SQLiteAssetException("Archive is missing a SQLite database file");
                }
                ScriptProvider.writeExtractedFileToDisk(zis, new FileOutputStream(dest));
            } else {
                ScriptProvider.writeExtractedFileToDisk(is, new FileOutputStream(dest));
            }

           // Log.w(TAG, "database copy complete");

        } catch (IOException e) {
            SQLiteAssetException se = new SQLiteAssetException("Unable to write " + dest + " to data directory");
            se.setStackTrace(e.getStackTrace());
            throw se;
        }
    }

    /**
     * Close any open database object.
     */
    @Override
    public synchronized void close() {
        if (mIsInitializing) throw new IllegalStateException("Closed during initialization");

        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    @Override
    public final void onConfigure(SQLiteDatabase db) {
        // not supported!
    }

    private InputStream getUpgradeSQLStream(int oldVersion, int newVersion) {
        String path = String.format(mUpgradePathFormat, oldVersion, newVersion);
        try {
            return context.getAssets().open(path);
        } catch (IOException e) {
           // Log.w(TAG, "missing database upgrade script: " + path);
            return null;
        }
    }

    private void getUpgradeFilePaths(int baseVersion, int start, int end, ArrayList<String> paths) {

        int a;
        int b;

        InputStream is = getUpgradeSQLStream(start, end);
        if (is != null) {
            String path = String.format(mUpgradePathFormat, start, end);
            paths.add(path);
            //Log.d(TAG, "found script: " + path);
            a = start - 1;
            b = start;
            is = null;
        } else {
            a = start - 1;
            b = end;
        }

        if (a < baseVersion) {
            return;
        } else {
            getUpgradeFilePaths(baseVersion, a, b, paths); // recursive call
        }

    }

    /**
     * An exception that indicates there was an error with SQLite asset retrieval or parsing.
     */
    @SuppressWarnings("serial")
    public static class SQLiteAssetException extends SQLiteException {

        public SQLiteAssetException() {}

        public SQLiteAssetException(String error) {
            super(error);
        }
    }
}
