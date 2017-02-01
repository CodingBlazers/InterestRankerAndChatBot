package com.codingblocks.ChatBot_And_InterestRanker.DBMS;

/**
 * Created by Sachin on 9/3/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codingblocks.ChatBot_And_InterestRanker.StudentModel;

import java.util.ArrayList;


/**
 * Created by championswimmer on 30/12/15.
 */
public class StudentTable {
    public static final String TABLE_NAME = "Student";

    public static final String ID = "student_id";

    public static final String STUDENT_NAME = "student_name";

    public static final String USER_NAME = "user_name";

    public static final String BATCH_NAME = "batch_name";

    public static final String[] PROJECTION =
            {ID, STUDENT_NAME, USER_NAME, BATCH_NAME};

    public static final String CMD_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    + STUDENT_NAME + " TEXT , "
                    + USER_NAME + " TEXT , "
                    + BATCH_NAME + " TEXT "
                    + " );";
    public static int id;

    public static ArrayList<StudentModel> getByArg(SQLiteDatabase db, String batch_name) {
        Cursor c = db.query(
                true,
                StudentTable.TABLE_NAME,
                StudentTable.PROJECTION,
                BATCH_NAME + " = ?",
                new String[]{batch_name},
                //null,
                //null,
                null,
                null,
                null, null
        );
        //BATCH_ID+" = ?"
        // new String[]{String.valueOf(id)}

        Log.i("DatabaseContentCount", c.getCount() + "");

        ArrayList<StudentModel> students = new ArrayList<>();
        while (c.moveToNext()) {
            students.add(
                    new StudentModel(
                            c.getInt(c.getColumnIndexOrThrow(ID)),
                            c.getString(c.getColumnIndexOrThrow(STUDENT_NAME)),
                            c.getString(c.getColumnIndexOrThrow(USER_NAME)),
                            c.getString(c.getColumnIndexOrThrow(BATCH_NAME))
                    )

            );
        }
        c.close();
        return students;

    }

    public static int deleteByBatchName(SQLiteDatabase db, String name) {
        /*
        We can just delete the parent expense row.
        The ON DELETE CASCADE clause, will make sure the
        refuel row is also deleted.
         */
        try {
            return db.delete(TABLE_NAME, BATCH_NAME + "= '" + name + "'", null);
//            db.execSQL("UPDATE " + TABLE_NAME + " set " + ID + " = (student_id-1) WHERE " + BatchTable.ID + " > " + id);
//
//            db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_NAME});

        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int deleteByStudentName(SQLiteDatabase db, String name) {
        /*
        We can just delete the parent expense row.
        The ON DELETE CASCADE clause, will make sure the
        refuel row is also deleted.
         */
        try {

            String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + STUDENT_NAME + " = ?";

            Cursor cursor = db.rawQuery(query, new String[]{name});
            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            }

            int result = db.delete(TABLE_NAME, STUDENT_NAME + "= ? ", new String[]{name});

            Log.i("FetchID", id + "");
            db.execSQL("UPDATE " + TABLE_NAME + " set " + ID + " = (student_id-1) WHERE " + ID + " > " + id);
            db.delete("SQLITE_SEQUENCE", "NAME = ?", new String[]{TABLE_NAME});

            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static long save(SQLiteDatabase db, StudentModel student) {

        ContentValues cv = new ContentValues();
        Log.i("StudentDetails", student.getStudent_name() + " " + student.getUser_name() + " " + student.getBatch_name());

        cv.put(STUDENT_NAME, student.getStudent_name());
        cv.put(USER_NAME, student.getUser_name());
        cv.put(BATCH_NAME, student.getBatch_name());

        //Log.i("himanshu", cv.size() + "");

        long result = db.insert(TABLE_NAME, null, cv);

        Log.e("Result After Inserting", "" + result);
        return result;
    }

}