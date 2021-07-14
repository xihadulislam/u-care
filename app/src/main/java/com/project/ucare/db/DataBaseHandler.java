package com.project.ucare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;
import com.project.ucare.models.Alarm;
import com.project.ucare.models.Profile;

import java.util.ArrayList;
import java.util.Date;


public class DataBaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ucare";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_CREATE_PROFILE = "create_profile";
    public static final String USER_NAME = "user_name";
    public static final String BIRTH_DATE = "birth_date";
    public static final String USER_GENDER = "user_gender";
    public static final String KEY_ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String  UPDATED_TIME= "updated_time";
    Context context;


    private final ArrayList<Profile> profileList = new ArrayList<>();

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating tables
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_NAME_CREATE_PROFILE + " ("
                + KEY_ID + " TEXT PRIMARY KEY, "
                + PARENT_ID + " TEXT, "
                + USER_NAME + " TEXT, "
                + BIRTH_DATE + " TEXT, "
                + USER_GENDER + " TEXT, "
                + UPDATED_TIME + " TEXT)";



        db.execSQL(CREATE_PROFILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CREATE_PROFILE);

        // create new
        onCreate(db);

    }

    // delete an alarm
    public void deleteAlarm(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CREATE_PROFILE, KEY_ID + " = ? ",
                new String[]{String.valueOf(id)});
        db.close();

    }

    //adding content to table
    public void addProfile(Profile profile) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, profile.getId());
        values.put(PARENT_ID, profile.getParent_id());
        values.put(USER_NAME, profile.getName());
        values.put(USER_GENDER, profile.getGender());
        values.put(BIRTH_DATE, profile.getBirth_date());
        values.put(UPDATED_TIME, profile.getUpdatedTime().toString());


        long result= db.insert(TABLE_NAME_CREATE_PROFILE, null, values);

        Toast.makeText(context, "res "+result, Toast.LENGTH_SHORT).show();
        db.close();

    }


    //get all alarms
    public ArrayList<Profile> getAlarms() {

        profileList.clear();

        //String selectQuery = "SELECT * FROM " +  TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_CREATE_PROFILE, new String[]{KEY_ID,
                        PARENT_ID,USER_NAME, BIRTH_DATE, USER_GENDER, UPDATED_TIME},
                null, null, null, null, KEY_ID + " ASC ");

        // loop through cursor
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));

                Toast.makeText(context, "cursor "+cursor.getString(cursor.getColumnIndex(USER_NAME)), Toast.LENGTH_SHORT).show();

                profileList.add(profile);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return profileList;
    }

    //get all alarms
//    public ArrayList<Alarm> getAlarms() {
//
//        AlarmList.clear();
//
//        //String selectQuery = "SELECT * FROM " +  TABLE_NAME;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
//                        ALARM_NAME, ALARM_HOUR, ALARM_MIN, ALARM_DATE},
//                null, null, null, null, ALARM_DATE + " ASC ");
//
//        // loop through cursor
//        if (cursor.moveToFirst()) {
//            do {
//                Alarm alarm = new Alarm();
//                alarm.setName(cursor.getString(cursor.getColumnIndex(ALARM_NAME)));
//                alarm.setHour(cursor.getInt(cursor.getColumnIndex(ALARM_HOUR)));
//                alarm.setMinunte(cursor.getInt(cursor.getColumnIndex(ALARM_MIN)));
//                alarm.setItemID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
//
//                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
//                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(ALARM_DATE))).getTime());
//
//                alarm.setDate(dateData);
//
//                AlarmList.add(alarm);
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        return AlarmList;
//    }

}