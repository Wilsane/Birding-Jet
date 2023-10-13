package com.example.aviaryquest.AccessRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aviaryquest.MainActivity;
import com.example.aviaryquest.R;
import com.example.aviaryquest.SplashScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgotPassword extends AppCompatActivity {

    ImageView btn_sendEmail;
    EditText txt_email;
    ProgressBar progressBar;
    FirebaseAuth auth;
    AccessUtils accessUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txt_email=findViewById(R.id.fpw_inpt_email);
        btn_sendEmail=findViewById(R.id.btn_sendEmail);
        progressBar=findViewById(R.id.fwd_progressBar);

        accessUtils=new AccessUtils();

        auth=FirebaseAuth.getInstance();


        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.scale_animation);
                btn_sendEmail.startAnimation(animation);
                progressBar.setVisibility(View.VISIBLE);
                btn_sendEmail.setEnabled(false);

                String email=txt_email.getText().toString().trim();
                if(email.isEmpty() || email==null){
                    txt_email.setError("Required*");
                    return;
                }
                else{
                    auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setVisibility(View.GONE);
                            txt_email.setError(null);
                            Toast.makeText(ForgotPassword.this, "Please check both you inbox and spam emails", Toast.LENGTH_LONG).show();
                            accessUtils.openEmail(ForgotPassword.this);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            txt_email.setError(e.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                    btn_sendEmail.setEnabled(true);
                }

            }
        });
    }

    //Take the user back to the login page
    public void BackToLogin(View v){

        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(intent);
    }
}