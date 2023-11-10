package com.example.aviaryquest.Utilities;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

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

    public void openEmail(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Open Email App");
        builder.setMessage("Please open your email app to verify your email.");

        // Open the email app if the user clicks "Open Email App"
        builder.setPositiveButton("Open Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent chooser = Intent.createChooser(intent, "Choose an email app:");
                if (chooser.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(activity, "Can't open your email app", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Create the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
