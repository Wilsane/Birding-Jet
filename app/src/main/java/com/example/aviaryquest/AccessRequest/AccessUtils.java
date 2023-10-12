package com.example.aviaryquest.AccessRequest;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;

import com.example.aviaryquest.LoggedIn.LoggedInActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Class containing methods all access pages require
public class AccessUtils {
    // Regular expression pattern for a valid email address
    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@(.+)$";

    //Pattern object to compile the regular expression
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    // Method to validate an email address
    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void authorisedUser(Activity activity){
        Intent Login=new Intent(activity, LoggedInActivity.class);
        activity.startActivity(Login);
        activity.finish();
    }
}
