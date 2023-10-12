package com.example.aviaryquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.aviaryquest.LoggedIn.LoggedInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser firebaseUser=auth.getCurrentUser();

                //Take the user to the home page if they are already logged in
                if(firebaseUser!=null){
                    Intent intent = new Intent(SplashScreen.this, LoggedInActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 6000);
    }
}