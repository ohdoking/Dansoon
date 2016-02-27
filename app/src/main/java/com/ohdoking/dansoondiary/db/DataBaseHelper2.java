package com.ohdoking.dansoondiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-02-26.
 */
public class DataBaseHelper2 extends SQLiteOpenHelper {

    public static final String DIARYS_TABLE = "diarys";
    public static final String DIARYS_ID = "id";
    public static final String DIARYS_DATE = "date";
    public static final String DIARYS_DETAILDIARYS_ID = "detaildiarys_id";

    public static final String DETAILDIARYS_TABLE = "detaildiarys";
    public static final String DETAILDIARYS_ID = "id";
    public static final String DETAILDIARYS_MEMO = "memo";
    public static final String DETAILDIARYS_IMAGES_ID ="images_id";


    public static final String IMAGES_TABLE = "images";
    public static final String IMAGES_ID = "id";
    public static final String IMAGES_IMAGES = "image";

    private static final String DATABASE_NAME = "Diary.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String CREATE_DIARYS_TABLE= "create table " + DIARYS_TABLE
            + "(" + DIARYS_ID + " integer primary key autoincrement, "
            + DIARYS_DETAILDIARYS_ID + " integer not null"
            + DIARYS_DATE + " integer not null"
            + ");";

    private static final String CREATE_DETAILDIARYS_TABLE = "create table " + DETAILDIARYS_TABLE
            + "(" + DETAILDIARYS_ID + " integer primary key autoincrement, "
            + DETAILDIARYS_MEMO + " text not null"
            + DETAILDIARYS_IMAGES_ID + " integer not null"
            + ");";

    private static final String CREATE_IMAGES_TABLE = "create table " + IMAGES_TABLE
            + "(" + IMAGES_ID + " integer primary key autoincrement, "
            + IMAGES_IMAGES + " integer not null"
            + ");";

    private static DataBaseHelper2 instance;

    private DataBaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper2 getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper2(context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARYS_TABLE);
        db.execSQL(CREATE_DETAILDIARYS_TABLE);
        db.execSQL(CREATE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
