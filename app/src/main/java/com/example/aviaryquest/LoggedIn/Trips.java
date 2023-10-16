package com.example.aviaryquest.LoggedIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviaryquest.BirdAdapter;
import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Trips extends Fragment {

    RecyclerView recyclerView2;
    BirdAdapter birdAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        setUpRecyclerView();

        return view;
    }
    void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForData().orderBy("sciName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NearbyVariables> options = new FirestoreRecyclerOptions.Builder<NearbyVariables>()
                .setQuery(query, NearbyVariables.class).build();
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        birdAdapter = new BirdAdapter(options, getContext());
        recyclerView2.setAdapter(birdAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        birdAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        birdAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        birdAdapter.notifyDataSetChanged();
    }
}