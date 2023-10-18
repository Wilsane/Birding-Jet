package com.example.aviaryquest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviaryquest.Adapters.Nearby_RV_Adapter;
import com.example.aviaryquest.Adapters.Nearby_RV_Adapter2;
import com.example.aviaryquest.Adapters.Trips_RV_Adapter;
import com.example.aviaryquest.Data.Models.CombinedVariables;
import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class testing extends Fragment {

    RecyclerView recyc;
    Nearby_RV_Adapter2 tripsRVAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_testing, container, false);

        recyc = view.findViewById(R.id.testRecycler);
        recyc.setHasFixedSize(true);
        recyc.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        //tripsRVAdapter=new Nearby_RV_Adapter2(getContext(),);

        recyc.setAdapter(tripsRVAdapter);


        return view;
    }

}