package com.jolpai.tafsir.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tanim reja on 12/26/2015.
 */
public class MyDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "northwind.db";
    private static final int DATABASE_VERSION = 13;

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
