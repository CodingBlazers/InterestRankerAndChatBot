package com.codingblocks.ChatBot_And_InterestRanker.DBMS;

/**
 * Created by Sachin on 9/3/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "Teacher.db";
    public static final int DB_VER = 1;

    //SQLite database implements foreign keys, but you have to enable them each time you open the database ,
    //to modify it.
    public static final String CMD_ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";

    public static MyDatabase getInstance (Context c) {
        return new MyDatabase(c);
    }

    public MyDatabase (Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL(CMD_ENABLE_FOREIGN_KEYS);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BatchTable.CMD_CREATE_TABLE);
        db.execSQL(StudentTable.CMD_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}