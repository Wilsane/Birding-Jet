package com.example.aviaryquest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    public static CollectionReference getCollectionReferenceForData(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Observations").document(currentUser.getUid()).collection("my_observations");
    }

    public static CollectionReference getCollectionReferenceForEntry(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Entries").document(currentUser.getUid()).collection("my_diary");
    }
}
