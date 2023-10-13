package com.example.aviaryquest.Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aviaryquest.R;

public class Msg {
    int color= Color.parseColor("#47F607");
    public void display(Activity activity, String msg,String msgTitle,Drawable drawable,String msgType){

        // Create a LayoutInflater to inflate the custom layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.msg_layout, null);

        TextView title = dialogView.findViewById(R.id.dialog_title);
        ImageView imageView = dialogView.findViewById(R.id.dialog_img);
        TextView message=dialogView.findViewById(R.id.dialog_Msg);
        Button btn_dismiss=dialogView.findViewById(R.id.dialog_button);

        if(msgType.equals("success")||msgType=="success"){
            title.setTextColor(color);
            message.setTextColor(color);
            btn_dismiss.setBackgroundColor(color);
            btn_dismiss.setText("OK");
        }

        title.setText(msgTitle);
        imageView.setImageDrawable(drawable);
        message.setText(msg);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
