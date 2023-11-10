package com.example.aviaryquest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Nearby_RV_Adapter extends RecyclerView.Adapter<Nearby_RV_Adapter.viewHolder> {

    Context context;
    List<NearbyVariables> nearbyList;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

    public Nearby_RV_Adapter(Context context, List<NearbyVariables> nearbyList) {
        this.context = context;
        this.nearbyList = nearbyList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.nearby_bird,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //Binding all variables obtained from the list with widgets from the fragment
        NearbyVariables nearbyVariables=nearbyList.get(position);
        holder.ComName.setText(nearbyVariables.getComName());
        holder.SciName.setText(nearbyVariables.getSciName());
        holder.LocName.setText(nearbyVariables.getLocName());

        Query query=databaseReference.orderByChild("name").equalTo(nearbyVariables.getComName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap:snapshot.getChildren()){
                        String imageUrl=snap.child("imageUrl").getValue(String.class);
                        Picasso.get().load(imageUrl).into(holder.bird_img);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //When the share button is clicked
        holder.btn_nearbyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Common Name: " + nearbyVariables.getComName() + "\n" +
                        "Scientific Name: " + nearbyVariables.getSciName() + "\n" +
                        "Location Name: " + nearbyVariables.getLocName();

                // Append a Google Maps link to the location name
                String googleMapsLink = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(nearbyVariables.getLocName());
                shareText += "\n\nLocation on Google Maps: " + googleMapsLink;

                // Intent to share the text
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                // Start the sharing activity
                context.startActivity(Intent.createChooser(shareIntent, "Share Bird Info"));
            }
        });
        //Redirect to the Map
        holder.LocName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //length of the recycler view
    @Override
    public int getItemCount() {
        return nearbyList.size();
    }


    //Declaring widgets from the fragment
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView ComName,SciName,LocName;
        Button btn_nearbyShare;
        ImageView btn_nearbyLike,bird_img;
        private Boolean isLiked = false;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ComName=itemView.findViewById(R.id.comName_nearby);
            SciName=itemView.findViewById(R.id.sciName_nearby);
            LocName=itemView.findViewById(R.id.loc_nearby);
            bird_img=itemView.findViewById(R.id.img_nearby);

            btn_nearbyLike=itemView.findViewById(R.id.btn_like_nearby);
            btn_nearbyShare=itemView.findViewById(R.id.btn_share_nearby);

            //When like button is clicked
            btn_nearbyLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_nearbyLike.setImageResource(R.drawable.baseline_favorite_active);
                    saveData();
                }
            });
        }
        void saveData(){
            String cName = ComName.getText().toString();
            String s_name = SciName.getText().toString();
            String loc_name = LocName.getText().toString();

            NearbyVariables nearbyVariables = new NearbyVariables();
            nearbyVariables.setComName(cName);
            nearbyVariables.setSciName(s_name);
            nearbyVariables.setLocName(loc_name);

            saveDataToFirebase(nearbyVariables);
        }
        //this code saves data to database
        void saveDataToFirebase(NearbyVariables nearbyVariables) {

            DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForData().document();

            documentReference.set(nearbyVariables).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "!Unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void setData(List<NearbyVariables> data) {
        this.nearbyList = data;
        notifyDataSetChanged();
    }
}
