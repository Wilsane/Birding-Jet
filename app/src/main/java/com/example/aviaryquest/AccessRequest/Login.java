package com.example.aviaryquest.AccessRequest;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aviaryquest.Utilities.Msg;
import com.example.aviaryquest.Database;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utilities.AccessUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class Login extends Fragment {

    EditText email,password;
    ToggleButton tgb_showHidePassword;
    TextView btn_forgotPassword,err_email,err_password;
    ImageView btn_login;
    ProgressBar progressBar;
    FirebaseAuth auth;
    AccessUtils accessUtils;

    Database db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        email=view.findViewById(R.id.log_inpt_email);
        password=view.findViewById(R.id.log_inpt_password);
        tgb_showHidePassword=view.findViewById(R.id.toggleButtonShowHideButton);

        err_email=view.findViewById(R.id.err_email_txt);
        err_password=view.findViewById(R.id.err_password_txt);

        btn_forgotPassword=view.findViewById(R.id.txt_forgotPassword);
        btn_login=view.findViewById(R.id.btn_log);
        progressBar=view.findViewById(R.id.log_progressBar);

        accessUtils=new AccessUtils();
        auth=FirebaseAuth.getInstance();

        db=new Database(getActivity());

        //Show and hide the password in password Edit text
        tgb_showHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show password
                    password.setTransformationMethod(null);
                    int sky= Color.parseColor("#26a7de");
                    tgb_showHidePassword.setTextColor(sky);
                } else {
                    // Hide password
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    int gray= Color.parseColor("#848484");
                    tgb_showHidePassword.setTextColor(gray);
                }
            }
        });
        //Log the user in
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale_animation);
                btn_login.startAnimation(animation);

                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();

                if(!areFieldsFilled(Email,Password))
                    return;
                if(!accessUtils.isValidEmail(Email)){
                    err_email.setVisibility(View.VISIBLE);
                    err_email.setText("Invalid Email address");
                    return;
                }

                if(areFieldsFilled(Email,Password) && accessUtils.isValidEmail(Email)){
                    err_password.setText(null);
                    err_email.setText(null);
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Successfully logged in", Toast.LENGTH_LONG).show();
                                accessUtils.authorisedUser(getActivity());
                            }
                            else{
                                try {
                                    throw task.getException();
                                }catch (FirebaseAuthInvalidUserException noUser){
                                    err_email.setText(noUser.getMessage());
                                    err_email.setVisibility(View.VISIBLE);
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    err_password.setVisibility(View.VISIBLE);
                                    err_password.setText("Incorrect password");
                                } catch (Exception e) {
                                    Msg msg=new Msg();
                                    Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                                    msg.display(getActivity(),e.getMessage(),"Error Found",errImg,"err");
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                else {
                    err_password.setText("*required");
                    err_email.setText("*required");
                    return;
                }



            }
        });

        //If the user forgot the password
        btn_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgotPassActivity=new Intent(getActivity(),ForgotPassword.class);
                startActivity(toForgotPassActivity);
            }
        });


        return view;
    }

    //Checking if all fields are filled,no empty fields
    private boolean areFieldsFilled(String Email,String Password){
        if(Email.isEmpty()){
            err_email.setVisibility(View.VISIBLE);
            err_email.setText("Email address is required");
            return false;
        }
        else {
            err_email.setText(null);
            err_email.setVisibility(View.GONE);
        }

        if(Password.isEmpty()){
            err_password.setText("Password is required");
            err_password.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            err_password.setText(null);
            err_password.setVisibility(View.GONE);
        }
        return true;
    }
}