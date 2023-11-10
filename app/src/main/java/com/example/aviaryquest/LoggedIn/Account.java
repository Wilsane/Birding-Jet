package com.example.aviaryquest.LoggedIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aviaryquest.Adapters.AccountEditOpts_Adapter;
import com.example.aviaryquest.Data.Models.AccountEditOptions;
import com.example.aviaryquest.MainActivity;
import com.example.aviaryquest.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Account extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<AccountEditOptions>editOptsArrayList;
    private AccountEditOpts_Adapter editOptsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        AccountEditOptions();

        //Initialize and set properties for recycler view
        recyclerView=view.findViewById(R.id.acc_recycler);
        recyclerView.setHasFixedSize(true);

        //Initialize and set properties for GridLayoutManager
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        //Set the recycler view layout
        recyclerView.setLayoutManager(gridLayoutManager);

        editOptsAdapter=new AccountEditOpts_Adapter(getActivity(),editOptsArrayList);
        recyclerView.setAdapter(editOptsAdapter);


        return  view;
    }

    private void AccountEditOptions(){
        editOptsArrayList=new ArrayList<AccountEditOptions>();

        editOptsArrayList.add(new AccountEditOptions("Icon",R.drawable.white_baseline_account_circle_24));
        editOptsArrayList.add(new AccountEditOptions("Username",R.drawable.baseline_label_24));
        editOptsArrayList.add(new AccountEditOptions("Email",R.drawable.white_baseline_email_24));
        editOptsArrayList.add(new AccountEditOptions("Password",R.drawable.baseline_password_24));
    }
}