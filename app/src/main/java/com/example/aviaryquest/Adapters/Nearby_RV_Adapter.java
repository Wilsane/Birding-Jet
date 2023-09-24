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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.R;

import java.util.List;

public class Nearby_RV_Adapter extends RecyclerView.Adapter<Nearby_RV_Adapter.viewHolder> {

    Context context;
    List<NearbyVariables> nearbyList;

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
        Button btn_nearbyLike,btn_nearbyShare;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ComName=itemView.findViewById(R.id.comName_nearby);
            SciName=itemView.findViewById(R.id.sciName_nearby);
            LocName=itemView.findViewById(R.id.loc_nearby);

            btn_nearbyLike=itemView.findViewById(R.id.btn_like_nearby);
            btn_nearbyShare=itemView.findViewById(R.id.btn_share_nearby);
        }
    }
    public void setData(List<NearbyVariables> data) {
        this.nearbyList = data;
        notifyDataSetChanged();
    }
}
