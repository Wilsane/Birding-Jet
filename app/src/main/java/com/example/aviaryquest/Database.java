package com.example.aviaryquest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    Activity _activity;
    boolean isRegistered;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=auth.getCurrentUser();

    public Database( Activity activity) {
        _activity=activity;

    }

    //Check if the user already exists in the database
    /*public boolean userExists(String Email){
        SQLiteDatabase myDb=this.getWritableDatabase();
        Cursor cursor=myDb.rawQuery("select * from Users where email=?",new String[]{Email});

        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }*/


    //Insert the new user into the database
    public boolean RegisterUser(String Email, String Password){

        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    isRegistered=true;
                }
                else
                    isRegistered=false;
            }
        });
        return isRegistered;

    }

    //Login the user if all requirements are met
    /*public boolean LoginUser(String Email,String Password){

        SQLiteDatabase myDb=this.getWritableDatabase();
        Cursor cursor=myDb.rawQuery("select * from Users where email=? and password=?",new String[]{Email,Password});

        if (cursor.getCount()>0){
            return true;
        }

        return false;
    }*/








}
