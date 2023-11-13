package com.example.aviaryquest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Entry;
import com.example.aviaryquest.LoggedIn.entrydetail;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class Entry_Adapter extends FirestoreRecyclerAdapter<Entry, Entry_Adapter.EntryViewHolder> {

    Context context;

    public Entry_Adapter(@NonNull FirestoreRecyclerOptions<Entry> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull EntryViewHolder holder, int position, @NonNull Entry entry) {
        holder.titleTT.setText(entry.getTitle());
        holder.specieTT.setText(entry.getSpecieName());
        holder.locationTT.setText(entry.getLocation());
        holder.dateTT.setText(entry.getDate());

        holder.dltBtn.setOnClickListener((v)->{
            String docId = this.getSnapshots().getSnapshot(position).getId();

            DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForEntry().document(docId);
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
        });

    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_entry_item,parent,false);
        return new EntryViewHolder(view);
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView titleTT, specieTT, locationTT, dateTT;
        ImageButton dltBtn;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTT = itemView.findViewById(R.id.entry_title);
            specieTT = itemView.findViewById(R.id.entry_specie);
            locationTT = itemView.findViewById(R.id.entry_location);
            dateTT = itemView.findViewById(R.id.entry_date);
            dltBtn = itemView.findViewById(R.id.delete_btn);

        }
    }
}
