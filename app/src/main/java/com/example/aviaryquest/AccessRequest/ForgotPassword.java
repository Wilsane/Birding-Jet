package com.example.aviaryquest.AccessRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.aviaryquest.MainActivity;
import com.example.aviaryquest.R;
import com.example.aviaryquest.SplashScreen;

public class ForgotPassword extends AppCompatActivity {

    ImageView btn_sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_sendEmail=findViewById(R.id.btn_sendEmail);

        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.scale_animation);
                btn_sendEmail.startAnimation(animation);
            }
        });
    }

    //Take the user back to the login page
    public void BackToLogin(View v){

        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(intent);
    }
}