package com.example.aviaryquest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database extends SQLiteOpenHelper {


    public Database( Context context) {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Users(email Text primary key,password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists Users");
    }


    //Check if the user already exists in the database
    public boolean userExists(String Email){
        SQLiteDatabase myDb=this.getWritableDatabase();
        Cursor cursor=myDb.rawQuery("select * from Users where email=?",new String[]{Email});

        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }


    //Insert the new user into the database
    public boolean RegisterUser(String Email, String Password){
        SQLiteDatabase myDb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",Email);
        contentValues.put("password",Password);

        long result=myDb.insert("Users",null,contentValues);

        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    //Login the user if all requirements are met
    public boolean LoginUser(String Email,String Password){

        SQLiteDatabase myDb=this.getWritableDatabase();
        Cursor cursor=myDb.rawQuery("select * from Users where email=? and password=?",new String[]{Email,Password});

        if (cursor.getCount()>0){
            return true;
        }

        return false;
    }








}
