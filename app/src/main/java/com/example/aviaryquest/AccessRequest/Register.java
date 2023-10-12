package com.example.aviaryquest.AccessRequest;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aviaryquest.Database;
import com.example.aviaryquest.LoggedIn.LoggedInActivity;
import com.example.aviaryquest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;


public class Register extends Fragment {
    TextInputLayout EmailInputLayout,PasswordInputLayout,ConfirmPasswordInputLayout;
    ImageView btn_register;
    Database db;
    AccessUtils accessUtils;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


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
        progressBar=view.findViewById(R.id.reg_progressBar);

        db=new Database(getActivity());
        accessUtils=new AccessUtils();

        //Firebase variables
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animate button click
                Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale_animation);
                btn_register.startAnimation(animation);
                String email= EmailInputLayout.getEditText().getText().toString().trim();
                String password= PasswordInputLayout.getEditText().getText().toString().trim();
                String confirmPassword= ConfirmPasswordInputLayout.getEditText().getText().toString().trim();

                if (!areFieldsFilled(email, password, confirmPassword))
                    return;
                if (!accessUtils.isValidEmail(email)) {
                    EmailInputLayout.setError("Invalid Email Address");
                    return;
                }
                if(!isValidPassword(password))
                    return;
                if (!arePasswordsEqual(password, confirmPassword))
                    return;

                /*All requirements are met
                    1)No empty fields
                    2)Email Address is valid
                    3)Password is valid
                    4)Password and Confirm password are equal * */
                if(areFieldsFilled(email, password, confirmPassword) &&
                    arePasswordsEqual(password, confirmPassword) &&
                    accessUtils.isValidEmail(email) && isValidPassword(password))
                {

                    progressBar.setVisibility(View.VISIBLE);
                    btn_register.setEnabled(false);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Firebase user successfully registered", Toast.LENGTH_LONG).show();
                                accessUtils.authorisedUser(getActivity());
                            }
                            else {
                                Toast.makeText(getActivity(), "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

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
        else
            ConfirmPasswordInputLayout.setError("Passwords do not match");

        return false;

    }

    //Ensure the passwords meet passwords requirements
    private boolean isValidPassword(String password){

       final short minLength=6;//Minimum password length
       boolean hasUppercase=false;//At least one uppercase letter
       boolean hasLowercase=false;//At least one lower letter
       boolean hasDigit=false;//At least one digits
       boolean hasSpecialChar=false;//At least one special character

        //Check the password length
        if(password.length()<minLength){
            PasswordInputLayout.setError("6 characters is the minimum length");
            return false;
        }

        //Check for complexity requirements
        for(char c:password.toCharArray()){
            if(Character.isUpperCase(c))
                hasUppercase = true;
            else if (Character.isLowerCase(c))
                hasLowercase=true;
            else if (Character.isDigit(c)) 
                hasDigit=true;
            else if (!Character.isLetterOrDigit(c))
                hasSpecialChar=true;

        }

        // Clear errors if all requirements are met
        if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
            PasswordInputLayout.setError(null); // Clear the error
        } else {
            // Display specific errors for missing requirements
            if (!hasUppercase) {
                PasswordInputLayout.setError("Password must contain at least one uppercase letter");
            }
            if (!hasLowercase) {
                PasswordInputLayout.setError("Password must contain at least one lowercase letter");
            }
            if (!hasDigit) {
                PasswordInputLayout.setError("Password must contain at least one digit");
            }
            if (!hasSpecialChar) {
                PasswordInputLayout.setError("Password must contain at least one special character");
            }
        }

        // Ensure all complexity requirements are met
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

}