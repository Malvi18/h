package com.incipient.secondtest.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmployeeHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "mydb";
    public static final String TABLENAME = "employee";
    public static final int VERSION = 1;
    public static final String ID = "id";
    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final String EMAIL = "email";
    public static final String DOB = "dob";
    public static final String GENDER = "gender";
    public static final String IMG = "img";


    public EmployeeHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create = "CREATE TABLE " + TABLENAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRST + " TEXT, "
                + LAST + " TEXT, "
                + EMAIL + " TEXT, "
                + DOB + " TEXT, "
                + GENDER + " TEXT, "
                + IMG + " BLOB)";
        db.execSQL(Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }



}
