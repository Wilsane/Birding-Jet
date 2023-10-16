package com.example.aviaryquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class BirdAdapter extends FirestoreRecyclerAdapter<NearbyVariables, BirdAdapter.BirdViewHolder> {

    Context context;


    public BirdAdapter(@NonNull FirestoreRecyclerOptions<NearbyVariables> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BirdViewHolder holder, int position, @NonNull NearbyVariables nearbyVariables) {
        holder.sci_name.setText(nearbyVariables.getSciName());
        holder.com_name.setText(nearbyVariables.getComName());
        holder.loc_name.setText(nearbyVariables.getLocName());

    }

    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_list,parent,false);
        return new BirdViewHolder(view);
    }

    class BirdViewHolder extends RecyclerView.ViewHolder{
        TextView sci_name, com_name, loc_name;

        public BirdViewHolder(@NonNull View itemView) {
            super(itemView);
            sci_name = itemView.findViewById(R.id.fav_specie);
            com_name = itemView.findViewById(R.id.fav_birdname);
            loc_name = itemView.findViewById(R.id.fav_birdLocation);


        }

    }
}
