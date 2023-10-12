package com.example.aviaryquest.LoggedIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.aviaryquest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedInActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //Default fragment
        SwitchFragments(new Home());

        bottomNavigationView.setOnNavigationItemSelectedListener(nav);


    }

    private  BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.navItem_home) {
                SwitchFragments(new Home());
                return true;
            } else if (itemId == R.id.navItem_trips) {
                SwitchFragments(new Trips());
                return true;
            } else if (itemId == R.id.navItem_settings) {
                SwitchFragments(new Settings());
                return true;
            } else if (itemId == R.id.navItem_account) {
                SwitchFragments(new Account());
                return true;
            } else {
                SwitchFragments(new Home());
                return true;
            }
        }
    };
    private void SwitchFragments(Fragment frag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame, frag);
        transaction.commit();
    }
}