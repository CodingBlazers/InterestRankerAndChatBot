package com.codingblocks.ChatBot_And_InterestRanker.DBMS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codingblocks.ChatBot_And_InterestRanker.Models.ExpenditureModel;

import java.util.ArrayList;

/**
 * Created by megha on 29/01/17.
 */

public class ExpenditureTable {
    
    public static final String TABLE_NAME = "EXPENDITURE";
    public static final String ID = "EXPENDITURE_ID";
    public static final String BATCH_NAME = "BATCH_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String TYPE = "TYPE";
    public static final String AMOUNT = "AMOUNT";
    public static final String AMOUNT_UNIT = "AMOUNT_UNIT";
    public static final String DATE = "DATE";
    public static final String SPLIT = "SPLIT";
    public static final String NOTES = "NOTES";
    public static final String NAMES = "NAMES";
    public static final String SPLIT_AMOUNTS = "SPLIT_AMOUNTS";

    private static final String[] COLUMNS = {ID, BATCH_NAME, DESCRIPTION, TYPE, AMOUNT, AMOUNT_UNIT, DATE, SPLIT, NOTES, NAMES, SPLIT_AMOUNTS};

    public static final String CMD_CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    + BATCH_NAME + " TEXT,"
                    + DESCRIPTION + " TEXT,"
                    + TYPE + " TEXT,"
                    + AMOUNT + " DOUBLE,"
                    + AMOUNT_UNIT + " TEXT,"
                    + DATE + " TEXT,"
                    + SPLIT + " INTEGER,"
                    + NOTES + " TEXT,"
                    + NAMES + " TEXT,"
                    + SPLIT_AMOUNTS + " TEXT"
                    + " );";

    public static ArrayList<ExpenditureModel> getExpendituresFromDB(Context context) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getReadableDatabase();
        ArrayList<ExpenditureModel> expenditures = new ArrayList<>();
        Cursor c = database.query(
                true,
                TABLE_NAME,
                COLUMNS,
                null,
                null,
                null,
                null, null, null
        );
        while (c.moveToNext()) {
            expenditures.add(new ExpenditureModel(
                    c.getInt(c.getColumnIndexOrThrow(ID)),
                    c.getString(c.getColumnIndexOrThrow(BATCH_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(TYPE)),
                    c.getDouble(c.getColumnIndexOrThrow(AMOUNT)),
                    c.getString(c.getColumnIndexOrThrow(AMOUNT_UNIT)),
                    c.getString(c.getColumnIndexOrThrow(DATE)),
                    c.getInt(c.getColumnIndexOrThrow(SPLIT)),
                    c.getString(c.getColumnIndexOrThrow(NOTES)),
                    c.getString(c.getColumnIndexOrThrow(NAMES)),
                    c.getString(c.getColumnIndexOrThrow(SPLIT_AMOUNTS))
            ));
        }
        c.close();
        database.close();
        return expenditures;
    }

    public static int deleteExpenditureById(Context context, int id) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getWritableDatabase();
        try {
            int result = database.delete(TABLE_NAME, ID + "=" + id, null);
            // maintaining the sequence of all ID's
            database.execSQL("UPDATE " + TABLE_NAME + " set " + ID + " = (" + ID + "-1) WHERE " + ID + " > " + id);
            // updating auto-increment in ID
            database.delete("SQLITE_SEQUENCE", "NAME = ?", new String[]{TABLE_NAME});
            database.close();
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long addExpenditureInDB(Context context, ExpenditureModel expenditureModel) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BATCH_NAME, expenditureModel.getBatchName());
        cv.put(DESCRIPTION, expenditureModel.getDescription());
        cv.put(TYPE, expenditureModel.getType());
        cv.put(AMOUNT, expenditureModel.getAmountRecord());
        cv.put(AMOUNT_UNIT, expenditureModel.getAmountUnit());
        cv.put(DATE, expenditureModel.getDate());
        cv.put(SPLIT, expenditureModel.getSplit());
        cv.put(NOTES, expenditureModel.getNotesString());
        cv.put(NAMES, expenditureModel.getNamesString());
        cv.put(SPLIT_AMOUNTS, expenditureModel.getSplitAmountsString());
        long result = database.insert(TABLE_NAME, null, cv);
        database.close();
        return result;
    }

    public static ArrayList<ExpenditureModel> getExpendituresByBatch(Context context, String batchName) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getReadableDatabase();
        Cursor c = database.query(
                true,
                ExpenditureTable.TABLE_NAME,
                ExpenditureTable.COLUMNS,
                BATCH_NAME + " = ?",
                new String[]{batchName},
                null, null, null, null
        );

        ArrayList<ExpenditureModel> expenditures = new ArrayList<>();
        while (c.moveToNext()) {
            expenditures.add(new ExpenditureModel(
                    c.getInt(c.getColumnIndexOrThrow(ID)),
                    c.getString(c.getColumnIndexOrThrow(BATCH_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(TYPE)),
                    c.getDouble(c.getColumnIndexOrThrow(AMOUNT)),
                    c.getString(c.getColumnIndexOrThrow(AMOUNT_UNIT)),
                    c.getString(c.getColumnIndexOrThrow(DATE)),
                    c.getInt(c.getColumnIndexOrThrow(SPLIT)),
                    c.getString(c.getColumnIndexOrThrow(NOTES)),
                    c.getString(c.getColumnIndexOrThrow(NAMES)),
                    c.getString(c.getColumnIndexOrThrow(SPLIT_AMOUNTS))
            ));
        }
        c.close();
        database.close();
        return expenditures;
    }

    public static ExpenditureModel getExpendituresByDescription(Context context, String description) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getReadableDatabase();

        Cursor c = database.query(
                true,
                ExpenditureTable.TABLE_NAME,
                ExpenditureTable.COLUMNS,
                DESCRIPTION + " = ?",
                new String[]{description},
                null, null, null, null
        );

        ArrayList<ExpenditureModel> expenditures = new ArrayList<>();
        while (c.moveToNext()) {
            expenditures.add(new ExpenditureModel(
                    c.getInt(c.getColumnIndexOrThrow(ID)),
                    c.getString(c.getColumnIndexOrThrow(BATCH_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(TYPE)),
                    c.getDouble(c.getColumnIndexOrThrow(AMOUNT)),
                    c.getString(c.getColumnIndexOrThrow(AMOUNT_UNIT)),
                    c.getString(c.getColumnIndexOrThrow(DATE)),
                    c.getInt(c.getColumnIndexOrThrow(SPLIT)),
                    c.getString(c.getColumnIndexOrThrow(NOTES)),
                    c.getString(c.getColumnIndexOrThrow(NAMES)),
                    c.getString(c.getColumnIndexOrThrow(SPLIT_AMOUNTS))
            ));
        }
        c.close();
        database.close();
        return expenditures.get(expenditures.size()-1);
    }

    public static long updateExpenditureInDB(Context context, ExpenditureModel expenditureModel, int id) {
        SQLiteDatabase database = MyDatabase.getInstance(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BATCH_NAME, expenditureModel.getBatchName());
        cv.put(DESCRIPTION, expenditureModel.getDescription());
        cv.put(TYPE, expenditureModel.getType());
        cv.put(AMOUNT, expenditureModel.getAmountRecord());
        cv.put(AMOUNT_UNIT, expenditureModel.getAmountUnit());
        cv.put(DATE, expenditureModel.getDate());
        cv.put(SPLIT, expenditureModel.getSplit());
        cv.put(NOTES, expenditureModel.getNotesString());
        cv.put(NAMES, expenditureModel.getNamesString());
        cv.put(SPLIT_AMOUNTS, expenditureModel.getSplitAmountsString());
        long result = database.update(TABLE_NAME, cv, ID + "=" + id, null);;
        database.close();
        return result;
    }
}
