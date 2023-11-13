package com.example.aviaryquest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.LoggedIn.CaptureImage;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Trips_RV_Adapter extends FirestoreRecyclerAdapter<NearbyVariables, Trips_RV_Adapter.BirdViewHolder> {
    Context context;
    NearbyVariables nearbyVariables;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

    public Trips_RV_Adapter(@NonNull FirestoreRecyclerOptions<NearbyVariables> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BirdViewHolder holder, int position, @NonNull NearbyVariables nearbyVariables) {
        holder.sci_name.setText(nearbyVariables.getSciName());
        holder.com_name.setText(nearbyVariables.getComName());
        holder.loc_name.setText(nearbyVariables.getLocName());

        String docId = this.getSnapshots().getSnapshot(position).getId();

        holder.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.unlike.setImageResource(R.drawable.baseline_favorite_inactive);
                DocumentReference documentReference;
                documentReference = Utility.getCollectionReferenceForData().document(docId);
                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "unsuccessful event", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        holder.cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaptureImage.class);
                intent.putExtra("birdName",nearbyVariables.getComName());
                context.startActivity(intent);
            }
        });

        Query query=databaseReference.orderByChild("name").equalTo(nearbyVariables.getComName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.cam.setVisibility(View.GONE);
                    for (DataSnapshot snap:snapshot.getChildren()){
                        String imageUrl=snap.child("imageUrl").getValue(String.class);
                        Picasso.get().load(imageUrl).into(holder.img);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_list,parent,false);
        return new BirdViewHolder(view);
    }

    class BirdViewHolder extends RecyclerView.ViewHolder{
        TextView sci_name, com_name, loc_name;
        ImageView img, unlike;
        ImageView like,cam,share;
        public BirdViewHolder(@NonNull View itemView) {
            super(itemView);
            sci_name = itemView.findViewById(R.id.fav_specie);
            com_name = itemView.findViewById(R.id.fav_birdname);
            loc_name = itemView.findViewById(R.id.fav_birdLocation);
            img=itemView.findViewById(R.id.img_fav);
            unlike = itemView.findViewById(R.id.unlike_btn);


            cam=itemView.findViewById(R.id.btn_camera_nearby);
        }



    }
}
