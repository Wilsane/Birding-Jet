package com.example.aviaryquest.LoggedIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviaryquest.Adapters.Entry_Adapter;
import com.example.aviaryquest.Entry;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;


public class add extends Fragment {

    FloatingActionButton addEntryBtn;
    RecyclerView recyclerView;
    Entry_Adapter entryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);


        addEntryBtn = view.findViewById(R.id.add_entry);
        recyclerView = view.findViewById(R.id.erecyvler_view);
        addEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEntryDetailFragment();
            }
        });
        setUpRecyclerView();
        return view;
    }
    void setUpRecyclerView(){
        Query query = Utility.getCollectionReferenceForEntry().orderBy("date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Entry> options = new FirestoreRecyclerOptions.Builder<Entry>()
                .setQuery(query,Entry.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entryAdapter = new Entry_Adapter(options, getContext());
        recyclerView.setAdapter(entryAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        entryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        entryAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        entryAdapter.notifyDataSetChanged();
    }

    public void openEntryDetailFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        entrydetail eDetail = new entrydetail();
        transaction.replace(R.id.fragment_container, eDetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}