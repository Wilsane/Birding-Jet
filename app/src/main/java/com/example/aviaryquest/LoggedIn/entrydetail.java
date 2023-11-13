package com.example.aviaryquest.LoggedIn;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aviaryquest.Entry;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class entrydetail extends Fragment {

    EditText birdNameEdt, specieNameEdt, datEdt, locEdt;
    ImageButton saveEntryBtn;
    int year, month, day;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_entrydetail, container, false);

     datEdt = view.findViewById(R.id.entry_date);
     birdNameEdt = view.findViewById(R.id.entry_title);
     specieNameEdt = view.findViewById(R.id.entry_specie);
     locEdt = view.findViewById(R.id.entry_location);
     saveEntryBtn = view.findViewById(R.id.save_btn);

     saveEntryBtn.setOnClickListener((v)-> saveEntry());



     Calendar calendar = Calendar.getInstance();
     datEdt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             year = calendar.get(Calendar.YEAR);
             month = calendar.get(Calendar.MONTH);
             day = calendar.get(Calendar.DAY_OF_MONTH);
             DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                 @Override
                 public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     datEdt.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                 }
             }, year,month, day);
             datePickerDialog.show();
         }
     });
     return view;
    }
    void saveEntry(){

        String entryTitle = birdNameEdt.getText().toString();
        String specieName = specieNameEdt.getText().toString();
        String entryDate = datEdt.getText().toString();
        String entryLoc = locEdt.getText().toString();

        if (entryTitle==null || entryTitle.isEmpty()){
            birdNameEdt.setError("Name is required");
            return;
        }
        Entry entry = new Entry();
        entry.setTitle(entryTitle);
        entry.setSpecieName(specieName);
        entry.setDate(entryDate);
        entry.setLocation(entryLoc);

        saveEntryToFirebase(entry);

    }
    void saveEntryToFirebase(Entry entry){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForEntry().document();
        documentReference.set(entry).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Entry Successfully Created", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.remove(entrydetail.this);
                    transaction.commit();
                }else {
                    Toast.makeText(getContext(), "Failed while adding entry", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}