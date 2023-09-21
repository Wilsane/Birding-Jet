package com.example.aviaryquest.LoggedIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aviaryquest.R;

import java.util.Locale;

public class Settings extends Fragment {

    TextView test;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        test=view.findViewById(R.id.txt_test);

        Locale currentLocate=Locale.getDefault();
        String regionCode=currentLocate.getCountry();

        test.setText(regionCode);
        return view;
    }
}