package com.codingblocks.ChatBot_And_InterestRanker.DBMS;

/**
 * Created by Sachin on 9/3/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codingblocks.ChatBot_And_InterestRanker.BatchModel;

import java.util.ArrayList;


public class BatchTable {
    public static final String TABLE_NAME = "BATCH";

    public static final String ID = "batch_id";

    public static final String NAME = "batch_name";

    public static final String[] PROJECTION = {ID, NAME};

    public static final String CMD_CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    + NAME + " TEXT"
                    + " );";

    public static ArrayList<BatchModel> getByArg(SQLiteDatabase db) {
        ArrayList<BatchModel> batches = new ArrayList<>();
        Cursor c = db.query(
                true,
                TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null, null, null
        );
        while (c.moveToNext()) {
            batches.add(new BatchModel(
                    c.getInt(c.getColumnIndexOrThrow(ID)),

                    c.getString(c.getColumnIndexOrThrow(NAME))
            ));
        }
        c.close();
        return batches;
    }

    public static int deleteById(SQLiteDatabase db, int id) {
        try {

            int result = db.delete(TABLE_NAME, ID + "=" + id, null);

            db.execSQL("UPDATE " + TABLE_NAME + " set " + ID + " = (batch_id-1) WHERE " + BatchTable.ID + " > " + id);

            db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_NAME});

            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long save(SQLiteDatabase db, BatchModel batch) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, batch.getBatch_name());
        return db.insert(TABLE_NAME, null, cv);
    }
}