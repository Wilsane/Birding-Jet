package com.example.aviaryquest.LoggedIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.aviaryquest.LoggedIn.Settings;

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
        btn_nearby=view.findViewById(R.id.btn_nearby);
        btn_hotspots=view.findViewById(R.id.btn_hotspots);

        //When the nearby button is pressed
        btn_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new Nearby();
                SwitchFragments(fragment);

                btn_nearby.setTextColor(getResources().getColor(R.color.white));
                btn_nearby.setBackground(getResources().getDrawable(R.drawable.selected_home_top_navbar));
                btn_hotspots.setBackground(getResources().getDrawable(R.drawable.not_selected_home_top_navbar));
                btn_hotspots.setTextColor(getResources().getColor(R.color.limeGreen));
            }
        });

        //When hotspots button is pressed
        btn_hotspots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new Settings();
                SwitchFragments(fragment);

                btn_hotspots.setTextColor(getResources().getColor(R.color.white));
                btn_hotspots.setBackground(getResources().getDrawable(R.drawable.selected_home_top_navbar));
                btn_nearby.setBackground(getResources().getDrawable(R.drawable.not_selected_home_top_navbar));
                btn_nearby.setTextColor(getResources().getColor(R.color.limeGreen));
            }
        });

        //Default fragment
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