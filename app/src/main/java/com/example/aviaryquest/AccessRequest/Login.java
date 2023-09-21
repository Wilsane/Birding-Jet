package com.example.aviaryquest.AccessRequest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviaryquest.Database;
import com.example.aviaryquest.LoggedIn.LoggedInActivity;
import com.example.aviaryquest.R;


public class Login extends Fragment {

    EditText email,password;
    TextView btn_forgotPassword,err_email,err_password;
    ImageView btn_login;

    Database db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        email=view.findViewById(R.id.log_inpt_email);
        password=view.findViewById(R.id.log_inpt_password);
        err_email=view.findViewById(R.id.err_email_txt);
        err_password=view.findViewById(R.id.err_password_txt);

        btn_forgotPassword=view.findViewById(R.id.txt_forgotPassword);
        btn_login=view.findViewById(R.id.btn_log);

        db=new Database(getActivity().getApplicationContext());

        //Log the user in
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale_animation);
                btn_login.startAnimation(animation);

                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();

                if (!areFieldsFilled(Email, Password)) {
                    err_password.setText("*required");
                    err_email.setText("*required");
                    return;
                }

                err_password.setText(null);
                err_email.setText(null);

                if (!db.userExists(Email)) {
                    Toast.makeText(getActivity(), "User doesn't exist\n\n\nPlease Register first", Toast.LENGTH_LONG).show();
                    return; // Exit if user doesn't exist
                }


                if (db.LoginUser(Email, Password)) {
                    Toast.makeText(getActivity(), "Successfully logged in", Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(getActivity(), LoggedInActivity.class);
                    startActivity(loginIntent);
                } else {
                    err_password.setText("Incorrect password");
                    err_password.setVisibility(View.VISIBLE);
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