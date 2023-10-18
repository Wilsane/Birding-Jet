package com.example.aviaryquest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.CombinedVariables;
import com.example.aviaryquest.Data.Models.ImageVariables;
import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Nearby_RV_Adapter2 extends RecyclerView.Adapter<Nearby_RV_Adapter2.ViewHolder> {


    Context context;
    List<CombinedVariables>variablesList;

    public Nearby_RV_Adapter2(Context context, List<CombinedVariables> variablesList) {
        this.context = context;
        this.variablesList = variablesList;
    }

    @NonNull
    @Override
    public Nearby_RV_Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.nearby_bird,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Nearby_RV_Adapter2.ViewHolder holder, int position) {

        // Retrieve the CombinedData object for the current position
        CombinedVariables combinedData=variablesList.get(position);
        NearbyVariables nearbyData=combinedData.getNearbyVariables();
        ImageVariables imageData=combinedData.getImageVariables();

        // Access and set data from NearbyVariables
        holder.ComName.setText(nearbyData.getComName());
        holder.SciName.setText(nearbyData.getSciName());
        holder.LocName.setText(nearbyData.getLocName());

        // Load the bird picture from ImageVariables into the ImageView
        Picasso.get().load(imageData.getImageUrl()).into(holder.birdPic);

        //When the share button is clicked
        holder.btn_nearbyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Common Name: " + nearbyData.getComName() + "\n" +
                        "Scientific Name: " + nearbyData.getSciName() + "\n" +
                        "Location Name: " + nearbyData.getLocName();

                // Append a Google Maps link to the location name
                String googleMapsLink = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(nearbyData.getLocName());
                shareText += "\n\nLocation on Google Maps: " + googleMapsLink;

                // Intent to share the text
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                // Start the sharing activity
                context.startActivity(Intent.createChooser(shareIntent, "Share Bird Info"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return variablesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ComName,SciName,LocName;
        Button btn_nearbyShare;
        ImageView btn_nearbyLike,birdPic;
        private Boolean isLiked = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            birdPic=itemView.findViewById(R.id.img_nearby);
            ComName=itemView.findViewById(R.id.comName_nearby);
            SciName=itemView.findViewById(R.id.sciName_nearby);
            LocName=itemView.findViewById(R.id.loc_nearby);

            btn_nearbyLike=itemView.findViewById(R.id.btn_like_nearby);
            btn_nearbyShare=itemView.findViewById(R.id.btn_share_nearby);

            btn_nearbyLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
    public void setData(List<CombinedVariables> data) {
        this.variablesList = data;
        notifyDataSetChanged();
    }
}
