package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MessageDB";
    public static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "Messages";
    public static final String ID_COLUMN = "_id";
    public static final String SENT_COLUMN = "isSent";
    public static final String MESSAGE_COLUMN = "Message";


    public DbOpenHelper(Activity a) {
        super(a, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + SENT_COLUMN + " INTEGER," + MESSAGE_COLUMN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
