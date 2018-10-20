package com.br.gamifit.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.br.gamifit.model.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class ProfileFirebaseDAO extends Observable {

    private final int UPDATE_OFFENSIVE_DAYS_SUCESSFULL = 1;

    public boolean createProfile(Profile profile){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        profile.setCode(firebaseReference.child("profile").push().getKey());
        return firebaseReference.child("profile").child(profile.getCode()).setValue(profile).isSuccessful();
    }

    public void getAllMyProfiles(String userCode){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseReference.child("profile").orderByChild("user/code").equalTo(userCode).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Profile profile = data.getValue(Profile.class);
                        Log.i("Profile",profile.getGym().getName());
                        setChanged();
                        notifyObservers(profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean updateOffensiveDays(Profile profile){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        return firebaseReference.child("profile/"+profile.getCode()+"/progress")
                .setValue(profile.getProgress()).isSuccessful();
    }

    public boolean checkInOut(Profile profile){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        return firebaseReference.child("profile/"+profile.getCode()).setValue(profile.getCheckInOut()).isSuccessful();
    }
}
