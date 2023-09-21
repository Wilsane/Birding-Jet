package com.example.aviaryquest.AccessRequest;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aviaryquest.Database;
import com.example.aviaryquest.LoggedIn.LoggedInActivity;
import com.example.aviaryquest.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.logging.Logger;


public class Register extends Fragment {
    TextInputLayout EmailInputLayout,PasswordInputLayout,ConfirmPasswordInputLayout;
    ImageView btn_register;
    Database db;
    AccessUtils accessUtils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);


        //Initializing the variables
        EmailInputLayout=view.findViewById(R.id.inpt_email);
        PasswordInputLayout=view.findViewById(R.id.inpt_password);
        ConfirmPasswordInputLayout=view.findViewById(R.id.inpt_ConPassword);
        btn_register=view.findViewById(R.id.btn_reg);

        db=new Database(getActivity().getApplicationContext());
        accessUtils=new AccessUtils();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale_animation);
                btn_register.startAnimation(animation);
                String email= EmailInputLayout.getEditText().getText().toString().trim();
                String password= PasswordInputLayout.getEditText().getText().toString().trim();
                String confirmPassword= ConfirmPasswordInputLayout.getEditText().getText().toString().trim();

                if (!areFieldsFilled(email, password, confirmPassword)) {
                    return;
                }

                if (!accessUtils.isValidEmail(email)) {
                    EmailInputLayout.setError("Invalid Email Address");
                    return;
                }

                if (!arePasswordsEqual(password, confirmPassword)) {
                    return;
                }

                if (db.userExists(email)) {
                    Toast.makeText(getActivity(), "User already exists\n\n\t\tPlease login", Toast.LENGTH_LONG).show();
                    return;
                }

                if (db.RegisterUser(email, password)) {
                    Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent Login=new Intent(getActivity(), LoggedInActivity.class);
                    startActivity(Login);
                } else {
                    Toast.makeText(getActivity(), "Error:\tUser registration failed", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    //Check if all inputs fields are filled
    private boolean areFieldsFilled(String checkEmail,String checkPass,String checkConPass){
         if(checkEmail.isEmpty()){
             EmailInputLayout.setError("Email input missing");
             return false;
         }
         else{
             EmailInputLayout.setError(null);
         }
         if(checkPass.isEmpty()){
             PasswordInputLayout.setError("Password not filled");
             return  false;
         }
         else {
             PasswordInputLayout.setError(null);
         }
         if(checkConPass.isEmpty()){
             ConfirmPasswordInputLayout.setError("Password not confirmed");
             return false;
         }
         else {
             ConfirmPasswordInputLayout.setError(null);
         }

         return  true;
    }

    //Ensure that "Password" and "Confirm Password" are equal
    private boolean arePasswordsEqual(String password,String conPass){
        if(password.equals(conPass)){
            ConfirmPasswordInputLayout.setError(null);
            return true;
        }
        else {
            ConfirmPasswordInputLayout.setError("Passwords do not match");
        }
        return false;

    }


}