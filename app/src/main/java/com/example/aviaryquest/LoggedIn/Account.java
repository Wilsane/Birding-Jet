package com.example.aviaryquest.LoggedIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aviaryquest.MainActivity;
import com.example.aviaryquest.R;
import com.google.firebase.auth.FirebaseAuth;

public class Account extends Fragment {


    ImageView btn_imgLogout;
    TextView btn_txtLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        btn_txtLogout=view.findViewById(R.id.txt_logout);
        btn_imgLogout=view.findViewById(R.id.img_logout);

        btn_txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btn_imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return  view;
    }

    public void logout( ){
        FirebaseAuth.getInstance().signOut();
        Intent Login=new Intent(getActivity(), MainActivity.class);
        startActivity(Login);
        getActivity().finish();
    }
}