package ankushkumarkamboj.weatherworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WEATHER_WORLD";
    private static final String Table1 = "create table fragment_1 (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATA TEXT,LAT DOUBLE,LON DOUBLE)";
    private static final String Table5 = "create table fragment_11 (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATA TEXT)";
    private static final String Table6 = "create table fragment_22 (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATA TEXT)";
    private static final String Table7 = "create table fragment_33 (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATA TEXT)";
    private static final String Table8 = "create table fragment_44 (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATA TEXT)";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table1);
        db.execSQL(Table5);
        db.execSQL(Table6);
        db.execSQL(Table7);
        db.execSQL(Table8);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fragment_1");
        db.execSQL("DROP TABLE IF EXISTS fragment_11");
        db.execSQL("DROP TABLE IF EXISTS fragment_22");
        db.execSQL("DROP TABLE IF EXISTS fragment_33");
        db.execSQL("DROP TABLE IF EXISTS fragment_44");
        onCreate(db);
    }

    public long addData(String key, String item, double lat, double lon) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result = 0;
        cv.put("NAME", key);
        cv.put("DATA", item);
        cv.put("LAT", lat);
        cv.put("LON", lon);
        result = database.insert("fragment_1", null, cv);
        Log.i("showdata", String.valueOf(result));
        return result;
    }

    public long addData(int table, String key, String item) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", key);
        cv.put("DATA", item);
        long result = 0;
        switch (table) {
            case 4:
                result = database.insert("fragment_11", null, cv);
                break;
            case 5:
                result = database.insert("fragment_22", null, cv);
                break;
            case 6:
                result = database.insert("fragment_33", null, cv);
                break;
            case 7:
                result = database.insert("fragment_44", null, cv);
                break;
        }
        Log.i("showdata", String.valueOf(result));
        return result;
    }

    public void updateData(int table, String row, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DATA", name);
        String[] selection = new String[]{row};
        switch (table) {
            case 0:
                database.update("fragment_1", cv, "ID = ?", selection);
                break;
            case 4:
                database.update("fragment_11", cv, "ID = ?", selection);
                break;
            case 5:
                database.update("fragment_22", cv, "ID = ?", selection);
                break;
            case 6:
                database.update("fragment_33", cv, "ID = ?", selection);
                break;
            case 7:
                database.update("fragment_44", cv, "ID = ?", selection);
                break;
        }
    }

    public void deleteItem(int table, int name) {
        SQLiteDatabase database = this.getWritableDatabase();
        int in = 0;
        String[] selection = new String[]{String.valueOf(name)};
        switch (table) {
            case 0:
                in = database.delete("fragment_1", "ID = ?", selection);
                break;
            case 4:
                in = database.delete("fragment_11", "ID = ?", selection);
                break;
            case 5:
                in = database.delete("fragment_22", "ID = ?", selection);
                break;
            case 6:
                in = database.delete("fragment_33", "ID = ?", selection);
                break;
            case 7:
                in = database.delete("fragment_44", "ID = ?", selection);
                break;
        }
        Log.i("effect", String.valueOf(in));

    }

    public Map<Object, Object> getLocData(int table, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = null;
        query = "select * from fragment_1 where NAME = ?";
        Map<Object, Object> map = new HashMap<>();
        String[] selection = new String[]{name};
        Cursor cursor = database.rawQuery(query, selection);
        int count = cursor.getCount();
        if (count > 1) {
            cursor.moveToFirst();
            int i = cursor.getInt(0);
            deleteItem(table, i);
            cursor.moveToLast();
            map.put("location", cursor.getString(2));
            map.put("lat", cursor.getString(3));
            map.put("lon", cursor.getString(4));
        } else {
            if (count!=0){
                cursor.moveToFirst();
                map.put("location", cursor.getString(2));
                map.put("lat", cursor.getString(3));
                map.put("lon", cursor.getString(4));
            }
        }
        cursor.close();
        return map;
    }

    public String getData(int table, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = null, list = null;
        switch (table) {
            case 4:
                query = "select * from fragment_11 where NAME = ?";
                break;
            case 5:
                query = "select * from fragment_22 where NAME = ?";
                break;
            case 6:
                query = "select * from fragment_33 where NAME = ?";
                break;
            case 7:
                query = "select * from fragment_44 where NAME = ?";
                break;
        }
        String[] selection = new String[]{name};
        Cursor cursor = database.rawQuery(query, selection);
        int count = cursor.getCount();
        if (count > 1) {
            cursor.moveToFirst();
            int i = cursor.getInt(0);
            deleteItem(table, i);
            cursor.moveToLast();
            list = cursor.getString(2);
        } else {
            if (count!=0){
                cursor.moveToFirst();
                list = cursor.getString(2);
            }
        }
        cursor.close();
        return list;
    }
}
