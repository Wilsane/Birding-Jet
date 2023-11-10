package com.example.aviaryquest.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.aviaryquest.R;

public class Window {
    private Dialog dialog;

    Activity act;
    AlertDialog.Builder builder=new AlertDialog.Builder(act);
    LayoutInflater inflater;
    View dialogView;
    AlertDialog alertDialog;

    public Window(Activity context) {
        this.act = act;
    }

    public  void updateUsernameWindow(String clicked){
        inflater=act.getLayoutInflater();//Get the layout inflater
        dialogView = inflater.inflate(R.layout.msg_layout, null);// Inflate and set the layout for the dialog.

        //Build the dialog
        builder.setView(dialogView);//Set the view of the dialog
        alertDialog=builder.create();
        alertDialog.show();
    }
}
