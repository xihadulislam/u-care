//package com.project.ucare.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.project.ucare.models.Alarm;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//
//public class DataBaseHandler extends SQLiteOpenHelper {
//
//    public static final String DATABASE_NAME = "alarmdb";
//    public static final int DATABASE_VERSION = 2;
//    public static final String TABLE_NAME = "alarms_table";
//    public static final String ALARM_NAME = "alarm_name";
//    public static final String ALARM_HOUR = "alarm_hour";
//    public static final String ALARM_MIN = "alarm_min";
//    public static final String ALARM_DATE = "alarm_date";
//    public static final String KEY_ID = "_id";
//
//
//    private final ArrayList<Alarm> AlarmList = new ArrayList<>();
//
//    public DataBaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //Creating tables
//        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + KEY_ID + " INTEGER PRIMARY KEY, "
//                + ALARM_NAME + " TEXT, "
//                + ALARM_HOUR + " INT, "
//                + ALARM_MIN + " INT, "
//                + ALARM_DATE + " LONG);";
//
//        db.execSQL(CREATE_ALARMS_TABLE);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // drop old one
//        db.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
//
//        // create new
//        onCreate(db);
//
//    }
//
//    // delete an alarm
//    public void deleteAlarm(int id) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, KEY_ID + " = ? ",
//                new String[]{String.valueOf(id)});
//        db.close();
//
//    }
//
//    //adding content to table
//    public void addAlarm(Alarm alarm) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ALARM_NAME, alarm.getName());
//        values.put(ALARM_HOUR, alarm.getHour());
//        values.put(ALARM_MIN, alarm.getMinunte());
//        values.put(ALARM_DATE, System.currentTimeMillis());
//
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//
//    }
//
//    //get all alarms
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
//
//}