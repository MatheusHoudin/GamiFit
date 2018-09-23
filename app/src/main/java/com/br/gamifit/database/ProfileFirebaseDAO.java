package com.br.gamifit.database;

import com.br.gamifit.model.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFirebaseDAO {

    public boolean createProfile(Profile profile){
        DatabaseReference firebaseReference = Connection.getDatabaseReference();
        return firebaseReference.child("profile").push().setValue(profile).isSuccessful();
    }
}
