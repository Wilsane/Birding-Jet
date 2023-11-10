package com.example.aviaryquest.LoggedIn;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.aviaryquest.LoggedIn.HomeTasks.Hotspots;
import com.example.aviaryquest.LoggedIn.HomeTasks.Nearby;
import com.example.aviaryquest.R;


public class Home extends Fragment {

    FrameLayout frameLayout;
    Button btn_nearby,btn_hotspots;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        frameLayout=view.findViewById(R.id.home_frameLayout);

        SwitchFragments(new Nearby());
        return  view;
    }

    private void SwitchFragments(Fragment frag){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.home_frameLayout, frag);
        transaction.commit();
    }
}