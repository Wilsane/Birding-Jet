package com.example.aviaryquest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Windows {
    private Dialog dialog;

    LayoutInflater inflater;
    View dialogView;
    AlertDialog alertDialog;
    AlertDialog.Builder builder ;


    public  void updateUsernameWindow(String clicked,Activity act){
        builder = new AlertDialog.Builder(act);
        inflater=act.getLayoutInflater();//Get the layout inflater
        dialogView = inflater.inflate(R.layout.account_update_options, null);// Inflate and set the layout for the dialog.

        TextView title = dialogView.findViewById(R.id.dialogTitle);
        title.setText("Edit "+clicked);

        //Build the dialog
        builder.setView(dialogView);//Set the view of the dialog
        alertDialog=builder.create();
        alertDialog.show();
    }

}
