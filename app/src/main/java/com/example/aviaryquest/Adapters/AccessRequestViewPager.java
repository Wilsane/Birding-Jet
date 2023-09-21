package com.example.aviaryquest.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aviaryquest.AccessRequest.Login;
import com.example.aviaryquest.AccessRequest.Register;

public class AccessRequestViewPager extends FragmentStateAdapter {
    public AccessRequestViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Login();
            case 1:
                return new Register();
            default:
                return new Login();
        }
    }

    //Amount of pages in the view pager
    @Override
    public int getItemCount() {
        return 2;
    }
}
