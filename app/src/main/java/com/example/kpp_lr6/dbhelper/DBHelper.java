package com.example.kpp_lr6.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "appDB";
    public static final String TABLE_USER = "users";
    public static final String TABLE_WHEAT = "wheat";

    public static final String USER_ID = "_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";

    public static final String WHEAT_ID = "_id";
    public static final String WHEAT_NAME = "Назва_сорту";
    public static final String WHEAT_SEASON = "Сезон";
    public static final String WHEAT_GROWING = "Вегетаційний_період";
    public static final String WHEAT_PRODUCTIVITY = "Середня_врожайність";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + "(" + USER_ID + " integer primary key," + USER_LOGIN + " text," + USER_PASSWORD + " text" + ")");
        db.execSQL("create table " + TABLE_WHEAT + "(" + WHEAT_ID + " integer primary key," + WHEAT_NAME + " text," + WHEAT_SEASON + " text," + WHEAT_GROWING + " integer," + WHEAT_PRODUCTIVITY + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USER);
        db.execSQL("drop table if exists " + TABLE_WHEAT);

        onCreate(db);
    }
}
