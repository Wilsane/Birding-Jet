package com.example.aviaryquest.LoggedIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.aviaryquest.MainActivity;
import com.example.aviaryquest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoggedInActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView menuIcon;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        toolbar=findViewById(R.id.toolbar);
        menuIcon=findViewById(R.id.circle_imageView);

        
        //Bottom navigation
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //Default fragment
        SwitchFragments(new Home());

        bottomNavigationView.setOnNavigationItemSelectedListener(nav);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.top_menu_options, popupMenu.getMenu());
        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item click
                int id = item.getItemId();
                if (id == R.id.itm_acc) {
                    SwitchFragments(new Account());
                    return true;
                } else if (id == R.id.itm_settings) {
                    // Handle item 2 click
                    return true;
                }else if (id == R.id.itm_help) {
                    // Handle item 2 click
                    return true;
                }else if (id == R.id.itm_logout) {
                    logout();
                    return true;
                }

                return false;
            }
        });
        popupMenu.show();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.navItem_home) {
                SwitchFragments(new Home());
                return true;
            } else if (itemId == R.id.navItem_trips) {
                SwitchFragments(new Trips());//Redirect the user to the "trips" page
                return true;
            } else if (itemId == R.id.navItem_hotspots) {
                SwitchFragments(new Settings());//Redirect the user to the "Settings" page
                return true;
            }
            return  true;
        }
    };

    //Log the user out of the application
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent Login=new Intent(this, MainActivity.class);
        startActivity(Login);
        this.finish();
    }
    private void SwitchFragments(Fragment frag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame, frag);
        transaction.commit();
    }
}