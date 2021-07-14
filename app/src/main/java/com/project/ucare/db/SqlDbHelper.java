package com.project.ucare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDbHelper extends SQLiteOpenHelper {

    // profile create
    public static final String TABLE_PROFILE = "table_profile";
    public static final String USER_NAME = "user_name";
    public static final String BIRTH_DATE = "birth_date";
    public static final String USER_GENDER = "user_gender";
    public static final String KEY_ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String UPDATED_TIME = "updated_time";

    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + " ("
            + KEY_ID + " TEXT PRIMARY KEY, "
            + PARENT_ID + " TEXT, "
            + USER_NAME + " TEXT, "
            + BIRTH_DATE + " TEXT, "
            + USER_GENDER + " TEXT, "
            + UPDATED_TIME + " TEXT)";



    public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PROFILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

}
